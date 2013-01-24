/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.onesmash.splineinterpolation;

import java.awt.geom.*;

/**
 *
 * @author xuhui
 */
public class CubicBezierCurve extends Curve {
    
    private double distanceX;

    public CubicBezierCurve(double[] x, double[] y)  { 
        path = new GeneralPath();
        controlPoints = new Point2D.Double[4];
        path.moveTo(x[0], y[0]);
        path.curveTo(x[1], y[1], x[2], y[2], x[3], y[3]);
        for(int i = 0; i < 4; i++) {
            controlPoints[i] = new Point2D.Double(x[i], y[i]);
        }
        distanceX = controlPoints[3].getX() - controlPoints[0].getX();
    }
    
    

    //public double value(double x) {
    //    Point2D.Double startP = controlPoints[0];
    //    Point2D.Double ctrP1 = controlPoints[1];
    //    Point2D.Double ctrP2 = controlPoints[2];
    //    Point2D.Double endP = controlPoints[3];
    //    double t = (x - startP.getX()) / distanceX;
    //    return Math.pow(1 - t, 3) * startP.getY() 
    //            + 3 * Math.pow(1 - t, 2) * x * ctrP1.getY() 
    //            + 3 * (1 - t) * Math.pow(t, 2) * ctrP2.getY()
    //            + Math.pow(t, 3) * endP.getY();
    //}

    @Override
    public String toString() {
        return String.format("[(%1$f, %2$f), (%3$f, %4$f), (%5$f, %6$f), (%7$f, %8$f)]", 
                controlPoints[0].x, controlPoints[0].y, 
                controlPoints[1].x, controlPoints[1].y,
                controlPoints[2].x, controlPoints[2].y,
                controlPoints[3].x, controlPoints[3].y);
    }
    
    
    
}
