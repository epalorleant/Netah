/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 * 
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 * 
 */
package com.imag.netah.gui.plugin;

import com.imag.netah.Simulation;
import com.imag.netah.gui.transformers.CustomVertexIconTransformer;
import com.imag.netah.gui.transformers.CustomVertexShapeTransformer;
import com.imag.netah.network.devices.AMIDevice;
import com.imag.netah.network.devices.ComLink;
import com.imag.netah.network.devices.ComLinkFactory;
import com.imag.netah.network.devices.DCDevice;
import com.imag.netah.network.devices.Device;
import com.imag.netah.network.devices.DeviceFactory;
import com.imag.netah.network.devices.DeviceType;
import com.imag.netah.network.devices.HTACoordDevice;
import com.imag.netah.network.devices.Junction;
import com.imag.netah.network.devices.PADevice;
import com.imag.netah.network.devices.SacomutDevice;
import com.imag.netah.network.devices.UtilityDevice;
import com.imag.netah.network.routing.CustomUndirectedGraph;
import com.imag.netah.network.routing.EPGraph;
import com.imag.netah.network.routing.Topology;
import com.imag.netah.runtime.algoritms.GraphUtil;
import com.imag.netah.runtime.algoritms.OperatorMapping;
import com.imag.netah.runtime.algoritms.Solution;
import com.imag.netah.runtime.client.EventConsumer;
import com.imag.netah.runtime.client.EventProducer;
import com.imag.netah.core.EPUnit;
import com.imag.netah.core.EventChannel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.functors.MapTransformer;
import org.apache.commons.collections15.map.LazyMap;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.GraphMLWriter;
import edu.uci.ics.jung.io.graphml.EdgeMetadata;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;
import edu.uci.ics.jung.io.graphml.GraphMetadata;
import edu.uci.ics.jung.io.graphml.HyperEdgeMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.annotations.AnnotationControls;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.util.Animator;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.commons.collections15.Transformer;

/**
 * Shows how to create a graph editor with JUNG. Mouse modes and actions are
 * explained in the help text. The application version of GraphEditorDemo
 * provides a File menu with an option to save the visible graph as a jpeg file.
 *
 * @author Tom Nelson
 *
 */
public class GraphEditor extends JApplet implements Printable, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2023243689258876709L;

    /**
     * the graph
     */
    //Graph<Number,Number> graph;
    Graph<Device, ComLink> graph;

    static JFrame frame = new JFrame("Simulator");
    AbstractLayout<Device, ComLink> layout;
    private static GraphEditor demo = null;
    static Simulation simu = null;
    /**
     * the visual component and renderer for the graph
     */
    static VisualizationViewer<Device, ComLink> vv;

    String instructions
            = "<html>"
            + "<h3>All Modes:</h3>"
            + "<ul>"
            + "<li>Right-click an empty area for <b>Create Vertex</b> popup"
            + "<li>Right-click on a Vertex for <b>Delete Vertex</b> popup"
            + "<li>Right-click on a Vertex for <b>Add Edge</b> menus <br>(if there are selected Vertices)"
            + "<li>Right-click on an Edge for <b>Delete Edge</b> popup"
            + "<li>Mousewheel scales with a crossover value of 1.0.<p>"
            + "     - scales the graph layout when the combined scale is greater than 1<p>"
            + "     - scales the graph view when the combined scale is less than 1"
            + "</ul>"
            + "<h3>Editing Mode:</h3>"
            + "<ul>"
            + "<li>Left-click an empty area to create a new Vertex"
            + "<li>Left-click on a Vertex and drag to another Vertex to create an Undirected Edge"
            + "<li>Shift+Left-click on a Vertex and drag to another Vertex to create a Directed Edge"
            + "</ul>"
            + "<h3>Picking Mode:</h3>"
            + "<ul>"
            + "<li>Mouse1 on a Vertex selects the vertex"
            + "<li>Mouse1 elsewhere unselects all Vertices"
            + "<li>Mouse1+Shift on a Vertex adds/removes Vertex selection"
            + "<li>Mouse1+drag on a Vertex moves all selected Vertices"
            + "<li>Mouse1+drag elsewhere selects Vertices in a region"
            + "<li>Mouse1+Shift+drag adds selection of Vertices in a new region"
            + "<li>Mouse1+CTRL on a Vertex selects the vertex and centers the display on it"
            + "<li>Mouse1 double-click on a vertex or edge allows you to edit the label"
            + "</ul>"
            + "<h3>Transforming Mode:</h3>"
            + "<ul>"
            + "<li>Mouse1+drag pans the graph"
            + "<li>Mouse1+Shift+drag rotates the graph"
            + "<li>Mouse1+CTRL(or Command)+drag shears the graph"
            + "<li>Mouse1 double-click on a vertex or edge allows you to edit the label"
            + "</ul>"
            + "<h3>Annotation Mode:</h3>"
            + "<ul>"
            + "<li>Mouse1 begins drawing of a Rectangle"
            + "<li>Mouse1+drag defines the Rectangle shape"
            + "<li>Mouse1 release adds the Rectangle as an annotation"
            + "<li>Mouse1+Shift begins drawing of an Ellipse"
            + "<li>Mouse1+Shift+drag defines the Ellipse shape"
            + "<li>Mouse1+Shift release adds the Ellipse as an annotation"
            + "<li>Mouse3 shows a popup to input text, which will become"
            + "<li>a text annotation on the graph at the mouse location"
            + "</ul>"
            + "</html>";

    /**
     * create an instance of a simple graph with popup controls to create a
     * graph.
     *
     */
    private GraphEditor(Simulation s) {
        simu = s;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.getLogger(GraphEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        // create a simple graph for the demo
        graph = Topology.getInstance().getGraph();
        this.layout = new StaticLayout<Device, ComLink>(graph,
                new Transformer<Device, Point2D>() {
                    @Override
                    public Point2D transform(Device v) {
                        Point2D p = new Point2D.Double(v.getX(), v.getY());
                        return p;
                    }
                },
                new Dimension(600, 600)
        );

        vv = new VisualizationViewer<Device, ComLink>(layout);
        vv.setBackground(Color.white);

        final Transformer<Device, String> vertexLabelTransformer = new Transformer<Device, String>() {
            @Override
            public String transform(Device d) {
                if(d instanceof Junction){
                    return "";
                }
                return d.getDeviceName();
            }
        };

        //vv.getRenderContext().setVertexLabelTransformer(MapTransformer.<Device, String>getInstance(
        //      LazyMap.<Device, String>decorate(new HashMap<Device, String>(), new ToStringLabeller<Device>())));
        vv.getRenderContext().setVertexLabelTransformer(vertexLabelTransformer);
        vv.getRenderContext().setEdgeLabelTransformer(new Transformer<ComLink, String>() {
            @Override
            public String transform(ComLink link) {
                return (link.getID() + ", " + link.getLatency());
            }
        });
        //float dash[] = {0.1f};
        //final Stroke edgeStroke = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 1.0f);
        final Stroke edgeStroke = new BasicStroke(3.0f);
        final Transformer<ComLink, Stroke> edgeStrokeTransformer = new Transformer<ComLink, Stroke>() {
            @Override
            public Stroke transform(ComLink l) {
                return edgeStroke;
            }
        };
        Transformer<ComLink, Paint> edgePaint = new Transformer<ComLink, Paint>() {
            public Paint transform(ComLink l) {
                if (l.isDown()) {
                    return Color.RED;
                } else {
                    return Color.BLACK;
                }
            }
        };
        vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.setVertexToolTipTransformer(vv.getRenderContext().getVertexLabelTransformer());
        vv.getRenderContext().setVertexIconTransformer(new CustomVertexIconTransformer());
        vv.getRenderContext().setVertexShapeTransformer(new CustomVertexShapeTransformer());

        vv.addPreRenderPaintable(new VisualizationViewer.Paintable() {

            @Override
            public void paint(Graphics grphcs) {

                for (Device d : Topology.getInstance().getGraph().getVertices()) {
                    int size = d.getOperators().size();
                    MyLayeredIcon icon = d.getIcon();
                    //if(icon == null) continue;
                    icon.removeAll();
                    if (size > 0) {
                        // the vertex icon                        
                        // Let's create the annotation image to be added to icon..
                        BufferedImage image
                                = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g = image.createGraphics();
                        g.setColor(Color.ORANGE);
                        g.fillOval(0, 0, 20, 20);
                        g.setColor(Color.BLACK);
                        g.drawString(size + "", 5, 13);
                        g.dispose();
                        ImageIcon img = new ImageIcon(image);
                        //Dimension id = new Dimension(icon.getIconWidth(), icon.getIconHeight());
                        //double x = vv.getModel().getGraphLayout().transform(d).getX();
                        //x -= (icon.getIconWidth() / 2);
                        //double y = vv.getModel().getGraphLayout().transform(d).getY();
                        //y -= (icon.getIconHeight() / 2);
                        //grphcs.drawImage(image, (int) Math.round(x), (int) Math.round(y), null);
                        icon.add(img);
                    }
                }
            }

            @Override
            public boolean useTransform() {
                return false;
            }
        });

        Container content = getContentPane();
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        content.add(panel);
        Factory<Device> vertexFactory = DeviceFactory.getInstance();
        Factory<ComLink> edgeFactory = ComLinkFactory.getInstance();

        final EditingModalGraphMouse<Device, ComLink> graphMouse
                = new EditingModalGraphMouse<>(vv.getRenderContext(), vertexFactory, edgeFactory);

        // Trying out our new popup menu mouse plugin...
        PopupVertexEdgeMenuMousePlugin myPlugin = new PopupVertexEdgeMenuMousePlugin();
        // Add some popup menus for the edges and vertices to our mouse plugin.
        JPopupMenu edgeMenu = new MyMouseMenus.EdgeMenu(frame);
        JPopupMenu vertexMenu = new MyMouseMenus.VertexMenu(frame);
        myPlugin.setEdgePopup(edgeMenu);
        myPlugin.setVertexPopup(vertexMenu);
        graphMouse.remove(graphMouse.getPopupEditingPlugin());  // Removes the existing popup editing plugin

        graphMouse.add(myPlugin);   // Add our new plugin to the mouse  
        // AnnotatingGraphMousePlugin<Device,ComLink> annotatingPlugin =
        //	new AnnotatingGraphMousePlugin<>(vv.getRenderContext());
        //graphMouse.add(annotatingPlugin);
        // the EditingGraphMouse will pass mouse event coordinates to the
        // vertexLocations function to set the locations of the vertices as
        // they are created
//        graphMouse.setVertexLocations(vertexLocations);
        vv.setGraphMouse(graphMouse);
        vv.addKeyListener(graphMouse.getModeKeyListener());

        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);

        //final ImageAtEdgePainter<String, String> imageAtEdgePainter = 
        //  new ImageAtEdgePainter<String, String>(vv, edge, image);
        final ScalingControl scaler = new CrossoverScalingControl();
        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1 / 1.1f, vv.getCenter());
            }
        });

        JButton help = new JButton("Help");
        help.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(vv, instructions);
            }
        });
        JButton deploy = new JButton("Deploy");
        deploy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // OPMapping algo here
                if (simu == null) {
                    return;
                }
                GraphUtil<Device, ComLink> util = new GraphUtil<>();
                for (EventProducer p : simu.getProducers()) {
                    if (!p.isMapped()) {
                        JOptionPane.showMessageDialog(frame, "Cannot map operators. Please deploy the producer: " + p.getName());
                        return;
                    }
                }
                for (EventConsumer c : simu.getConsumers()) {
                    if (!c.isMapped()) {
                        JOptionPane.showMessageDialog(frame, "Cannot map operators. Please deploy the consumer: " + c.getName());
                        return;
                    }
                    System.out.println("-- Operator placement algorithm Greedy: " + c.getName() + " --");
                    Solution init = util.initialMapping(c.getGraph());
                    System.out.println(c.getGraph() + "\nInitial Mapping: " + init);
                    OperatorMapping mapper = new OperatorMapping();
                    long T1, T2;
                    System.out.println("--- OpMapping Algo Greedy --- ");
                    T1 = System.currentTimeMillis();
                    Solution solution = mapper.opMapping(c.getGraph(),
                            Topology.getInstance().getGraph(), init);
                    T2 = System.currentTimeMillis();
                    System.out.println(solution);
                    System.out.println("Solution founded in: " + (T2 - T1) + " ms");
                }
//                Solution init = util.initialMapping(EPGraph.getInstance().getGraph());
//                System.out.println("Initial Mapping: " + init);
//                OperatorMapping mapper = new OperatorMapping();
//                long T1, T2;
//                System.out.println("--- OpMapping Algo Greedy --- ");
//                T1 = System.currentTimeMillis();
//                Solution solution = mapper.opMapping(EPGraph.getInstance().getGraph(),
//                        Topology.getInstance().getGraph(), init);
//                T2 = System.currentTimeMillis();
//                System.out.println(solution);
//                System.out.println("Solution founded in: " + (T2 - T1) + " ms");
                vv.repaint();
            }
        });
        JButton run = new JButton("Run");
        run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // run the simulation here
                System.out.println("Setting the simulation...");
                for (EventConsumer c : simu.getConsumers()) {

                    for (EPUnit op : c.getGraph().getVertices()) {
                        if (op.isMapped()) {
                            op.openIOchannels();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Cannot run, undeployed operators founded.");
                            return;
                        }
                    }
                }
                //ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                System.out.println("Running the simulation...");
                //scheduledExecutorService.execute(runner);
                for (Device device : Topology.getInstance().getGraph().getVertices()) {
                    if (!device.isAlive()) {
                        device.start();
                    }
                }
                for (ComLink link : Topology.getInstance().getGraph().getEdges()) {
                    if (!link.isAlive()) {
                        link.start();
                    }
                }
                for (EventConsumer c : simu.getConsumers()) {
                    for (EPUnit op : c.getGraph().getVertices()) {
                        if (op.isMapped() && op.getDevice() != null && !op.isAlive()) {
                            op.start();
                        }
                    }
                }

            }
        });
        AnnotationControls<Device, ComLink> annotationControls
                = new AnnotationControls<Device, ComLink>(graphMouse.getAnnotatingPlugin());
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 30, 0);
        slider.setMinorTickSpacing(5);
        slider.setMajorTickSpacing(30);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(15));
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                if (!slider.getValueIsAdjusting()) {
                    speedSimulation(slider.getValue());
                }
            }
        });
        JPanel controls = new JPanel();
        controls.add(plus);
        controls.add(minus);
        JComboBox modeBox = graphMouse.getModeComboBox();
        controls.add(modeBox);
        controls.add(annotationControls.getAnnotationsToolBar());
        controls.add(slider);
        controls.add(deploy);
        controls.add(run);
        controls.add(help);
        content.add(controls, BorderLayout.SOUTH);
        /* Custom JPanels can be added here  */
        //
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //final GraphEditor demo = new GraphEditor();

    }

    private void speedSimulation(int speed) {
        // speed up communication links
        System.out.println(speed);
        if (speed == 0) {
            speed = 1;
        }
        for (ComLink l : Topology.getInstance().getGraph().getEdges()) {
            int lat = l.getDefinedlatency() / speed;
            l.setLatency(Math.max(1, lat));
        }
        // speed up producers...
        for (EPUnit p : EPGraph.getInstance().getGraph().getVertices()) {
            if (p instanceof EventProducer) {
                long delay = ((EventProducer) p).getDefinedDelay() / speed;
                ((EventProducer) p).setDelay(Math.max(1, delay));
            }
        }
        vv.repaint();
    }

    public Simulation getSimu() {
        return simu;
    }

    public void setSimu(Simulation simu) {
        this.simu = simu;
    }

    private static void initMenu() {
        JMenu menu = new JMenu("File");
        menu.add(new AbstractAction("Make Image") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int option = chooser.showSaveDialog(demo);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    demo.writeJPEGImage(file);
                }
            }
        });
        menu.add(new AbstractAction("Print") {
            public void actionPerformed(ActionEvent e) {
                PrinterJob printJob = PrinterJob.getPrinterJob();
                printJob.setPrintable(demo);
                if (printJob.printDialog()) {
                    try {
                        printJob.print();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        menu.add(new AbstractAction("Save topology") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int option = chooser.showSaveDialog(demo);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    try {
                        demo.save(file);
                        frame.setTitle(file.getName());
                    } catch (IOException ex) {
                        Logger.getLogger(GraphEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        menu.add(new AbstractAction("Load topology") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int option = chooser.showOpenDialog(demo);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    try {
                        //EPGraph.getInstance().resetMapping();
                        simu.resetMapping();
                        demo.load(file);
                        frame.setTitle("Simulator - " + file.getName());
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(GraphEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        JMenu menu2 = new JMenu("View");
        menu2.add(new AbstractAction("Layout") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Layout l = new CircleLayout<Device, ComLink>(Topology.getInstance().getGraph());
                l.setInitializer(vv.getGraphLayout());
                l.setSize(vv.getSize());
                LayoutTransition<Device, ComLink> lt
                        = new LayoutTransition<>(vv, vv.getGraphLayout(), l);
                Animator animator = new Animator(lt);
                animator.start();
                vv.getRenderContext().getMultiLayerTransformer().setToIdentity();
                vv.repaint();
            }
        });
        menu2.add(new AbstractAction("Event Composition Networks") {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEPGraph(EPGraph.getInstance().getGraph());
            }
        });
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        menuBar.add(menu2);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(demo);
        frame.pack();
        frame.setVisible(true);
        buildEPGraphs();
        showEPGraph(EPGraph.getInstance().getGraph());
    }

    private static void buildEPGraphs() {
        ArrayList<EPUnit> operators = new ArrayList<>();
        operators.addAll(simu.getProducers());
        operators.addAll(simu.getConsumers());
        for (EventConsumer c : simu.getConsumers()) {
            operators.addAll(c.getEPUList());
        }
        EPGraph.getInstance().AddEPGraphFromList(operators);
    }

    public static void showEPGraph(DirectedSparseGraph<EPUnit, EventChannel> graph) {
        //System.out.println(EPGraph.getInstance().getGraph());
        Layout<EPUnit, EventChannel> layout = new DAGLayout<>(graph);
        //Layout<EPUnit, EventChannel> layout = new SpringLayout2<>(EPGraph.getInstance().getGraph());
        layout.setSize(new Dimension(800, 600)); // sets the initial size of the space
        // The BasicVisualizationServer<V,E> is parameterized by the edge types
        VisualizationViewer<EPUnit, EventChannel> vv
                = new VisualizationViewer<>(layout);
        vv.setPreferredSize(new Dimension(850, 650)); //Sets the viewing area size
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller() {
            @Override
            public String transform(Object v) {

                return ((EPUnit) v).getInfo();
            }
        });
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller() {
            @Override
            public String transform(Object v) {

                return ((EventChannel) v).getTopic();
            }
        });
        vv.getRenderContext().setVertexFillPaintTransformer(new Transformer<EPUnit, Paint>() {
            @Override
            public Paint transform(EPUnit i) {
                if (i.isMapped()) {
                    return Color.GREEN;
                } else {
                    return Color.RED;
                }
            }
        });

        DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        vv.setGraphMouse(graphMouse);
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
        JFrame frame = new JFrame("Event Composition Graph");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * copy the visible part of the graph to a file as a jpeg image
     *
     * @param file
     */
    public void writeJPEGImage(File file) {
        int width = vv.getWidth();
        int height = vv.getHeight();

        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bi.createGraphics();
        vv.paint(graphics);
        graphics.dispose();

        try {
            ImageIO.write(bi, "jpeg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int print(java.awt.Graphics graphics,
            java.awt.print.PageFormat pageFormat, int pageIndex)
            throws java.awt.print.PrinterException {
        if (pageIndex > 0) {
            return (Printable.NO_SUCH_PAGE);
        } else {
            java.awt.Graphics2D g2d = (java.awt.Graphics2D) graphics;
            vv.setDoubleBuffered(false);
            g2d.translate(pageFormat.getImageableX(), pageFormat
                    .getImageableY());

            vv.paint(g2d);
            vv.setDoubleBuffered(true);

            return (Printable.PAGE_EXISTS);
        }
    }

    public static GraphEditor getInstance(Simulation s) {
        if (demo == null) {
            demo = new GraphEditor(s);
        }
        initMenu();
        //demo.setSimu(s);
        return demo;
    }

    public static GraphEditor getInstance() {
        //demo.setSimu(s);
        return demo;
    }

    public VisualizationViewer<Device, ComLink> getVv() {
        return vv;
    }

    private void save(File file) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        GraphMLWriter<Device, ComLink> graphWriter = new GraphMLWriter<Device, ComLink>();
        graphWriter.addVertexData("x", null, "0",
                new Transformer<Device, String>() {
                    public String transform(Device v) {
                        //return Double.toString(layout.getX(v));
                        return Double.toString(vv.getModel().getGraphLayout().transform(v).getX());
                    }
                }
        );
        graphWriter.addVertexData("y", null, "0",
                new Transformer<Device, String>() {
                    public String transform(Device v) {
                        //return Double.toString(layout.getY(v));
                        return Double.toString(vv.getModel().getGraphLayout().transform(v).getY());
                    }
                }
        );

        graphWriter.addVertexData("deviceName", null, "device",
                new Transformer<Device, String>() {
                    public String transform(Device v) {
                        return v.getDeviceName();
                    }
                }
        );
        graphWriter.addVertexData("cpuSpeed", null, "1",
                new Transformer<Device, String>() {
                    public String transform(Device v) {
                        return Double.toString(v.getCpuSpeed());
                    }
                }
        );
        graphWriter.addVertexData("totalMemory", null, "1",
                new Transformer<Device, String>() {
                    public String transform(Device v) {
                        return Integer.toString(v.getTotalMemory());
                    }
                }
        );
        graphWriter.addVertexData("deviceType", null, DeviceType.AMI.toString(),
                new Transformer<Device, String>() {
                    public String transform(Device v) {
                        return v.getDeviceType().toString();
                    }
                }
        );
        graphWriter.addEdgeData("latency", null, "1000",
                new Transformer<ComLink, String>() {
                    public String transform(ComLink e) {
                        return Integer.toString(e.getLatency());
                    }
                }
        );
        graphWriter.addEdgeData("ID", null, "link",
                new Transformer<ComLink, String>() {
                    public String transform(ComLink e) {
                        return e.getID();
                    }
                }
        );
        graphWriter.addEdgeData("down", null, "false",
                new Transformer<ComLink, String>() {
                    public String transform(ComLink e) {
                        return e.isDown() + "";
                    }
                }
        );
        graphWriter.save(graph, out);

    }

    private void load(File file) throws FileNotFoundException {
        DeviceFactory.resetCounters();
        ComLinkFactory.resetCounter();
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        Transformer<GraphMetadata, Graph<Device, ComLink>> graphTransformer = new Transformer<GraphMetadata, Graph<Device, ComLink>>() {
            @Override
            public Graph<Device, ComLink>
                    transform(GraphMetadata metadata) {
                return new CustomUndirectedGraph<Device, ComLink>();
            }
        };
        Transformer<NodeMetadata, Device> vertexTransformer
                = new Transformer<NodeMetadata, Device>() {
                    public Device transform(NodeMetadata metadata) {
                        Device v;
                        String deviceName = metadata.getProperty("deviceName");
                        double cpuSpeeed = Double.parseDouble(metadata.getProperty("cpuSpeed"));
                        int totalMemory = Integer.parseInt(metadata.getProperty("totalMemory"));
                        if (metadata.getProperty("deviceType").equals("Smart Meter")) {
                            v = new AMIDevice(deviceName, cpuSpeeed, totalMemory);
                            DeviceFactory.setAmiCount(DeviceFactory.getAmiCount() + 1);
                        } else if (metadata.getProperty("deviceType").equals("SACOMUT")) {
                            v = new SacomutDevice(deviceName, cpuSpeeed, totalMemory);
                            DeviceFactory.setSacomutCount(DeviceFactory.getSacomutCount() + 1);
                        } else if (metadata.getProperty("deviceType").equals("HTA Coordinator")) {
                            v = new HTACoordDevice(deviceName, cpuSpeeed, totalMemory);
                            DeviceFactory.setHtaCount(DeviceFactory.getHtaCount() + 1);
                        } else if (metadata.getProperty("deviceType").equals("Utility")) {
                            v = new UtilityDevice(deviceName, cpuSpeeed, totalMemory);
                            DeviceFactory.setUtilityCount(DeviceFactory.getUtilityCount() + 1);
                        } else if (metadata.getProperty("deviceType").equals("POSTE ASSERVI")) {
                            v = new PADevice(deviceName, cpuSpeeed, totalMemory);
                            DeviceFactory.setPaCount(DeviceFactory.getPaCount() + 1);
                        }
                        else if (metadata.getProperty("deviceType").equals("Junction")) {
                            v = new Junction(deviceName, 1, 0);
                            DeviceFactory.setJcCount(DeviceFactory.getJcCount() + 1);
                        } else {
                            v = new DCDevice(deviceName, cpuSpeeed, totalMemory);
                            DeviceFactory.setDcCount(DeviceFactory.getDcCount() + 1);
                        }

                        v.setX(Double.parseDouble(
                                        metadata.getProperty("x")));
                        v.setY(Double.parseDouble(
                                        metadata.getProperty("y")));
                        Random r = new Random();
                        int red = r.nextInt(255);
                        int green = r.nextInt(255);
                        int blue = r.nextInt(255);
                        v.setPacketColor(new Color(red, green, blue));
                        return v;
                    }
                };
        Transformer<EdgeMetadata, ComLink> edgeTransformer
                = new Transformer<EdgeMetadata, ComLink>() {
                    public ComLink transform(EdgeMetadata metadata) {
                        int latency = Integer.parseInt(metadata.getProperty("latency"));
                        String ID = metadata.getProperty("ID");
                        boolean ok = Boolean.getBoolean(metadata.getProperty("down"));
                        ComLink e = new ComLink(ID);
                        e.setLatency(latency);
                        e.setDefinedlatency(latency);
                        e.setDown(ok);
                        ComLinkFactory.updateLinkCount();
                        return e;
                    }
                };
        Transformer<HyperEdgeMetadata, ComLink> hyperEdgeTransformer
                = new Transformer<HyperEdgeMetadata, ComLink>() {
                    public ComLink transform(HyperEdgeMetadata metadata) {
                        ComLink e = ComLinkFactory.getInstance().create();
                        return e;
                    }
                };

        GraphMLReader2<Graph<Device, ComLink>, Device, ComLink> graphReader
                = new GraphMLReader2<Graph<Device, ComLink>, Device, ComLink>(
                        fileReader, graphTransformer, vertexTransformer,
                        edgeTransformer, hyperEdgeTransformer);
        try {
            /* Get the new graph object from the GraphML file */

            Graph<Device, ComLink> g = graphReader.readGraph();
            Topology.getInstance().setGraph((CustomUndirectedGraph<Device, ComLink>) g);
            graph = g;
        } catch (GraphIOException ex) {
        }

        Layout l = new StaticLayout<Device, ComLink>(Topology.getInstance().getGraph(),
                new Transformer<Device, Point2D>() {
                    @Override
                    public Point2D transform(Device v) {
                        Point2D p = new Point2D.Double(v.getX(), v.getY());
                        return p;
                    }
                }
        );
        l.setInitializer(vv.getGraphLayout());
        l.setSize(vv.getSize());
        LayoutTransition<Device, ComLink> lt
                = new LayoutTransition<>(vv, vv.getGraphLayout(), l);
        Animator animator = new Animator(lt);
        animator.start();
        vv.getRenderContext().getMultiLayerTransformer().setToIdentity();
        //vv.getModel().setGraphLayout(l);
        vv.repaint();
    }
}
