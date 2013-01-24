/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.onesmash.splineinterpolation;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

/**
 *
 * @author xuhui
 */
public class LinearCurve extends Curve {

    public LinearCurve(double[] x, double[] y) {
        path = new GeneralPath();
        controlPoints = new Point2D.Double[2];
        path.moveTo(x[0], y[0]);
        path.lineTo(x[1], y[1]);
        for(int i = 0; i < 2; i++) {
            controlPoints[i] = new Point2D.Double(x[i], y[i]);
        }
    }
    
}
