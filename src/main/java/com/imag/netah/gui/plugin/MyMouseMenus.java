/*
 * MyMouseMenus.java
 *
 * Created on March 21, 2007, 3:34 PM; Updated May 29, 2007
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */
package com.imag.netah.gui.plugin;

import com.imag.netah.network.devices.ComLink;
import com.imag.netah.network.devices.Device;
import com.imag.netah.network.devices.DeviceFactory;
import com.imag.netah.network.devices.DeviceType;
import com.imag.netah.network.devices.Junction;
import com.imag.netah.runtime.client.EventConsumer;
import com.imag.netah.core.EPUnit;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

/**
 * A collection of classes used to assemble popup mouse menus for the custom
 * edges and vertices developed in this example.
 *
 * @author Dr. Greg M. Bernstein
 */
public class MyMouseMenus {

    public static class EdgeMenu extends JPopupMenu {

        // private JFrame frame; 
        public EdgeMenu(final JFrame frame) {
            super("Edge Menu");
            // this.frame = frame;
            this.add(new DeleteEdgeMenuItem<ComLink>());
            this.addSeparator();
            this.add(new SizeDisplay());
            this.add(new LatencyDisplay());
            this.add(new StdDevDisplay());
            this.add(new BandwidthDisplay());
            this.add(new LossRateDisplay());
            this.addSeparator();
            this.add(new EdgePropItem(frame));
        }

    }

    public static class EdgePropItem extends JMenuItem implements EdgeMenuListener<ComLink>,
            MenuPointListener {

        ComLink edge;
        VisualizationViewer visComp;
        Point2D point;

        @Override
        public void setEdgeAndView(ComLink edge, VisualizationViewer visComp) {
            this.edge = edge;
            this.visComp = visComp;
        }

        @Override
        public void setPoint(Point2D point) {
            this.point = point;
        }

        public EdgePropItem(final JFrame frame) {
            super("Edit link Properties...");
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    EdgePropertyDialog dialog = new EdgePropertyDialog(frame, edge);
//                    dialog.setLocation((int)point.getX()+ frame.getX(), (int)point.getY()+ frame.getY());
//                    dialog.setVisible(true);
                    //System.out.println("display interface to edit properties of " + edge.toString());
                    EdgePropertyDialog dialog = new EdgePropertyDialog(frame, edge);
                    dialog.setLocation((int) point.getX() + frame.getX(), (int) point.getY() + frame.getY());
                    dialog.setVisible(true);
                }

            });
        }

    }

    public static class OperatorsDisplay extends JMenu implements VertexMenuListener<Device> {

        public OperatorsDisplay() {
            super("Operators");
        }

        @Override
        public void setVertexAndView(Device v, VisualizationViewer visView) {
            this.removeAll();
            if (v.getOperators().isEmpty()) {
                this.add(new JMenuItem("No Operator"));
            } else {
                for (final EPUnit epu : v.getOperators()) {
                    JMenuItem epuMenu = new JMenuItem(epu.getInfo());
                    if (epu instanceof EventConsumer) {
                        epuMenu.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                GraphEditor.showEPGraph(((EventConsumer) epu).getGraph());
                            }
                        });
                    }

                    this.add(epuMenu);
                }
            }
        }

    }

    public static class LatencyDisplay extends JMenuItem implements EdgeMenuListener<ComLink> {

        public void setEdgeAndView(ComLink e, VisualizationViewer visComp) {
            this.setText("latency = " + e.getLatency());
        }
    }

    public static class LossRateDisplay extends JMenuItem implements EdgeMenuListener<ComLink> {

        public void setEdgeAndView(ComLink e, VisualizationViewer visComp) {
            this.setText("loss rate = " + e.getLossRate());
        }
    }

    public static class BandwidthDisplay extends JMenuItem implements EdgeMenuListener<ComLink> {

        public void setEdgeAndView(ComLink e, VisualizationViewer visComp) {
            this.setText("Bandwidth = " + e.getBandwidth());
        }
    }

    public static class StdDevDisplay extends JMenuItem implements EdgeMenuListener<ComLink> {

        public void setEdgeAndView(ComLink e, VisualizationViewer visComp) {
            this.setText("Std deviation = " + e.getDev());
        }
    }

    public static class SizeDisplay extends JMenuItem implements EdgeMenuListener<ComLink> {

        public void setEdgeAndView(ComLink e, VisualizationViewer visComp) {
            this.setText("Length = " + e.getSize());
        }
    }

    public static class VertexMenu extends JPopupMenu {

        public VertexMenu(final JFrame frame) {
            super("Device Menu");
            this.add(new DeleteVertexMenuItem<Device>());
            this.addSeparator();
            this.add(new DeviceTypeDisplay());
            this.add(new CPUDisplay());
            this.add(new TotalMemoryDisplay());
            this.add(new RemainingMemoryDisplay());
            this.add(new OperatorsDisplay());
            this.addSeparator();
            this.add(new VertexPropItem(frame));
            this.add(new AddOperatorItem(frame));
            this.add(new RemoveOperatorItem(frame));

            //this.add(new tdmCheckBox());
        }
    }

    public static class DeviceChoiceMenu extends JPopupMenu {

        public DeviceChoiceMenu() {
            super("Device type");
            ButtonGroup group = new ButtonGroup();
            JRadioButtonMenuItem meter = new JRadioButtonMenuItem("Smart meter");
            meter.addActionListener(new MyMouseMenus.CustomAction(DeviceType.AMI));
            JRadioButtonMenuItem sensor = new JRadioButtonMenuItem("SACOMUT");
            sensor.addActionListener(new MyMouseMenus.CustomAction(DeviceType.SACOMUT));
            JRadioButtonMenuItem dc = new JRadioButtonMenuItem("Data Concentrator");
            dc.addActionListener(new MyMouseMenus.CustomAction(DeviceType.DC));
            JRadioButtonMenuItem pa = new JRadioButtonMenuItem("Poste Asservi");
            pa.addActionListener(new MyMouseMenus.CustomAction(DeviceType.PA));
            JRadioButtonMenuItem htaCoord = new JRadioButtonMenuItem("HTA Coordinator");
            htaCoord.addActionListener(new MyMouseMenus.CustomAction(DeviceType.HTA_COORD));
            JRadioButtonMenuItem utility = new JRadioButtonMenuItem("Utility Device");
            utility.addActionListener(new MyMouseMenus.CustomAction(DeviceType.UTILITY));
            JRadioButtonMenuItem junction = new JRadioButtonMenuItem("Junction");
            junction.addActionListener(new MyMouseMenus.CustomAction(DeviceType.Junction));
            group.add(meter);
            group.add(sensor);
            group.add(dc);
            group.add(htaCoord);
            group.add(utility);
            group.add(pa);
            group.add(junction);
            meter.setSelected(true);
            this.add(meter);
            this.add(sensor);
            this.add(dc);
            this.add(htaCoord);
            this.add(utility);
            this.add(pa);
            this.add(junction);
        }
    }

    public static class CPUDisplay extends JMenuItem implements VertexMenuListener<Device> {

        public void setVertexAndView(Device d, VisualizationViewer visComp) {
            if (d instanceof Junction) {
                this.setText("Undefined");
            } else {
                this.setText("CPU Speed = " + d.getCpuSpeed());
            }

        }
    }

    public static class TotalMemoryDisplay extends JMenuItem implements VertexMenuListener<Device>, MenuPointListener {

        Device device;
        VisualizationViewer visComp;
        Point2D point;

        @Override
        public void setVertexAndView(Device device, VisualizationViewer visComp) {
            this.device = device;
            this.visComp = visComp;
            if (device instanceof Junction) {
                this.setText("Undefined");
            } else {
                this.setText("Total Memory = " + device.getTotalMemory());
            }
        }

        @Override
        public void setPoint(Point2D point) {
            this.point = point;
        }
    }

    public static class DeviceTypeDisplay extends JMenuItem implements VertexMenuListener<Device> {

        public void setVertexAndView(Device d, VisualizationViewer visComp) {
            this.setText("Device Type  = " + d.getDeviceType().toString());
        }
    }

    public static class RemainingMemoryDisplay extends JMenuItem implements VertexMenuListener<Device> {

        public void setVertexAndView(Device d, VisualizationViewer visComp) {
            if (d instanceof Junction) {
                this.setText("Undefined");
            } else {
                this.setText("Available memory  = " + d.getRemainingMemory());
            }
        }
    }
    /*
     public static class pscCheckBox extends JCheckBoxMenuItem implements VertexMenuListener<GraphElements.MyVertex> {

     GraphElements.MyVertex v;

     public pscCheckBox() {
     super("PSC Capable");
     this.addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e) {
     v.setPacketSwitchCapable(isSelected());
     }

     });
     }

     @Override
     public void setVertexAndView(GraphElements.MyVertex v, VisualizationViewer visComp) {
     this.v = v;
     this.setSelected(v.isPacketSwitchCapable());
     }

     }
     */

    /*    public static class tdmCheckBox extends JCheckBoxMenuItem implements VertexMenuListener<GraphElements.MyVertex> {

     GraphElements.MyVertex v;

     public tdmCheckBox() {
     super("TDM Capable");
     this.addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent e) {
     v.setTdmSwitchCapable(isSelected());
     }

     });
     }

     @Override
     public void setVertexAndView(GraphElements.MyVertex v, VisualizationViewer visComp) {
     this.v = v;
     this.setSelected(v.isTdmSwitchCapable());
     }

     }
     */
    public static class VertexPropItem extends JMenuItem implements VertexMenuListener<Device>,
            MenuPointListener {

        Device device;
        VisualizationViewer visComp;
        Point2D point;

        @Override
        public void setVertexAndView(Device device, VisualizationViewer visComp) {
            this.device = device;
            this.visComp = visComp;
            if (device instanceof Junction) {
                this.setText("Undefined");
                if (this.getActionListeners().length > 0) {
                    this.removeActionListener(this.getActionListeners()[0]);
                }
            }

        }

        @Override
        public void setPoint(Point2D point) {
            this.point = point;
        }

        public VertexPropItem(final JFrame frame) {
            super("Edit Device Properties...");
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    VertexPropertyDialog dialog = new VertexPropertyDialog(frame, device);
                    dialog.setLocation((int) point.getX() + frame.getX(), (int) point.getY() + frame.getY());
                    dialog.setVisible(true);
                    // System.out.println("display interface to edit properties of " + device.toString());
                }

            });
        }

    }

    public static class AddOperatorItem extends JMenuItem implements VertexMenuListener<Device>,
            MenuPointListener {

        Device device;
        VisualizationViewer visComp;
        Point2D point;

        @Override
        public void setVertexAndView(Device device, VisualizationViewer visComp) {
            this.device = device;
            this.visComp = visComp;
            if (device instanceof Junction) {
                this.setText("Undefined");
                this.removeActionListener(this.getActionListeners()[0]);
            }
        }

        @Override
        public void setPoint(Point2D point) {
            this.point = point;
        }

        public AddOperatorItem(final JFrame frame) {
            super("Add Operators...");
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddOperatorDialog dialog = new AddOperatorDialog(frame, device);
                    dialog.setLocation((int) point.getX() + frame.getX(), (int) point.getY() + frame.getY());
                    dialog.setVisible(true);
                    // System.out.println("display interface to edit properties of " + device.toString());
                    visComp.repaint();
                }

            });
        }
    }

    public static class RemoveOperatorItem extends JMenuItem implements VertexMenuListener<Device>,
            MenuPointListener {

        Device device;
        VisualizationViewer visComp;
        Point2D point;

        @Override
        public void setVertexAndView(Device device, VisualizationViewer visComp) {
            this.device = device;
            this.visComp = visComp;
            if (device instanceof Junction) {
                this.setText("Undefined");
                this.removeActionListener(this.getActionListeners()[0]);
            }
        }

        @Override
        public void setPoint(Point2D point) {
            this.point = point;
        }

        public RemoveOperatorItem(final JFrame frame) {
            super("Remove Operators...");
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    RemoveOperatorDialog dialog = new RemoveOperatorDialog(frame, device);
                    dialog.setLocation((int) point.getX() + frame.getX(), (int) point.getY() + frame.getY());
                    dialog.setVisible(true);
                    visComp.repaint();
                }

            });
        }

    }

    public static class CustomAction implements ActionListener {

        private DeviceType type;

        public CustomAction(DeviceType _type) {
            type = _type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DeviceFactory.getInstance().setTypeToCreate(type);
        }

    }

}
