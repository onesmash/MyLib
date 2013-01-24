/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.onesmash.splineinterpolation;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 *
 * @author xuhui
 */
public abstract class Curve {
    
    protected Point2D.Double[] controlPoints;
    
    protected GeneralPath path = new GeneralPath();
    
    public Point2D.Double[] getControlPoints() {
        int size = controlPoints.length;
        Point2D.Double[] cp = new Point2D.Double[size];
        System.arraycopy(controlPoints, 0, cp, 0, size);
        return cp;
    }
    public GeneralPath getPath() {
        return (GeneralPath)path.clone();
    }
    //public abstract double value(double t);
}
