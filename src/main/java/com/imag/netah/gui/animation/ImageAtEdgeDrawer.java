/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.gui.animation;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.MultiLayerTransformer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
//import static jungtester.JUNGEdgePathTest.computePointAt;

/**
 *
 * @author epaln
 * @param <V>
 * @param <E>
 */
public class ImageAtEdgeDrawer <V, E> implements VisualizationViewer.Paintable {
    
        private final VisualizationViewer<V, E> vv;
        private final E edge;
        private final BufferedImage image;
        private double imageLocation;

        public ImageAtEdgeDrawer(
            VisualizationViewer<V, E> vv, 
            E edge,
            BufferedImage image)
        {
            this.vv = vv;
            this.edge = edge;
            this.image = image;
        }

        public void setImageLocation(double imageLocation)
        {
            this.imageLocation = imageLocation;
        }

        @Override
        public void paint(Graphics gr)
        {
            Graphics2D g = (Graphics2D)gr;
            Shape shape = getTransformedEdgeShape(vv, vv.getGraphLayout(), edge);
            Point2D p = computePointAt(shape, 0.2, imageLocation);
            //g.setColor(Color.BLUE);
            //g.draw(shape);
            //System.out.println(p);
            gr.drawImage(image, (int)p.getX(), (int)p.getY(), null);
        }
        @Override
        public boolean useTransform()
        {
            return true;
        }
        
        // This method is take from JUNG ShapePickSupport.java
    private static <V, E>  Shape getTransformedEdgeShape(
        VisualizationViewer<V, E> vv, Layout<V, E> layout, E e) {
        Pair<V> pair = layout.getGraph().getEndpoints(e);
        V v1 = pair.getFirst();
        V v2 = pair.getSecond();
        boolean isLoop = v1.equals(v2);
        RenderContext<V, E> rc = vv.getRenderContext();
        MultiLayerTransformer multiLayerTransformer = 
            rc.getMultiLayerTransformer();
        Point2D p1 = multiLayerTransformer.transform(
            Layer.LAYOUT, layout.transform(v1));
        Point2D p2 = multiLayerTransformer.transform(
            Layer.LAYOUT, layout.transform(v2));
        if(p1 == null || p2 == null) 
            return null;
        float x1 = (float) p1.getX();
        float y1 = (float) p1.getY();
        float x2 = (float) p2.getX();
        float y2 = (float) p2.getY();
        AffineTransform xform = AffineTransform.getTranslateInstance(x1, y1);
        Shape edgeShape = 
            rc.getEdgeShapeTransformer().transform(
                Context.<Graph<V,E>,E>getInstance(
                    vv.getGraphLayout().getGraph(),e));
        if(isLoop) {
            Shape s2 = rc.getVertexShapeTransformer().transform(v2);
            Rectangle2D s2Bounds = s2.getBounds2D();
            xform.scale(s2Bounds.getWidth(),s2Bounds.getHeight());
            xform.translate(0, -edgeShape.getBounds2D().getHeight()/2);
        } else {
            float dx = x2 - x1;
            float dy = y2 - y1;
            double theta = Math.atan2(dy,dx);
            xform.rotate(theta);
            float dist = (float) Math.sqrt(dx*dx + dy*dy);
            xform.scale(dist, 1.0f);
        }
        edgeShape = xform.createTransformedShape(edgeShape);
        return edgeShape;
    }

        public static Point2D computePointAt(
        Shape shape, double flatness, double alpha)
    {
        alpha = Math.min(1.0, Math.max(0.0, alpha));
        double totalLength = computeLength(shape, flatness);
        double targetLength = alpha * totalLength;
        double currentLength = 0;
        PathIterator pi = shape.getPathIterator(null, flatness);
        double[] coords = new double[6];
        double previous[] = new double[2];
        while (!pi.isDone())
        {
            int segment = pi.currentSegment(coords);
            switch (segment)
            {
                case PathIterator.SEG_MOVETO:
                    previous[0] = coords[0];
                    previous[1] = coords[1];
                    break;

                case PathIterator.SEG_LINETO:
                    double dx = previous[0]-coords[0];
                    double dy = previous[1]-coords[1];
                    double segmentLength = Math.sqrt(dx*dx+dy*dy);
                    double nextLength = currentLength + segmentLength;
                    if (nextLength >= targetLength)
                    {
                        double localAlpha = 
                            (currentLength - targetLength) / segmentLength;
                        //System.out.println("current "+currentLength+" target "+targetLength+" seg "+segmentLength);
                        double x = previous[0] + localAlpha * dx;
                        double y = previous[1] + localAlpha * dy;
                        return new Point2D.Double(x,y);
                    }
                    previous[0] = coords[0];
                    previous[1] = coords[1];
                    currentLength = nextLength;
                    break;
            }
            pi.next();
        }
        return null;
    }
        
        private static double computeLength(Shape shape, double flatness)
    {
        double length = 0;
        PathIterator pi = shape.getPathIterator(null, flatness);
        double[] coords = new double[6];
        double previous[] = new double[2];
        while (!pi.isDone())
        {
            int segment = pi.currentSegment(coords);
            switch (segment)
            {
                case PathIterator.SEG_MOVETO:
                    previous[0] = coords[0];
                    previous[1] = coords[1];
                    break;

                case PathIterator.SEG_LINETO:
                    double dx = previous[0]-coords[0];
                    double dy = previous[1]-coords[1];
                    length += Math.sqrt(dx*dx+dy*dy);
                    previous[0] = coords[0];
                    previous[1] = coords[1];
                    break;
            }
            pi.next();
        }
        return length;
    }
}
