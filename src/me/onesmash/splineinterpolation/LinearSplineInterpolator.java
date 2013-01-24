/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.onesmash.splineinterpolation;

/**
 *
 * @author xuhui
 */
public class LinearSplineInterpolator implements Interpolator {

    @Override
    public SplineCurve interpolate(double[] x, double[] y) {
        int ctrPNum = x.length;
        if(ctrPNum < 2) return null;
        int segments = ctrPNum - 1;
        Curve[] curves =  new LinearCurve[segments];
        double[] ctrX = new double[2];
        double[] ctrY = new double[2];
        for(int i = 0; i < segments; i++) {
            ctrX[0] = x[i];
            ctrX[1] = x[i + 1];
            ctrY[0] = y[i];
            ctrY[1] = y[i + 1];
            curves[i] = new LinearCurve(ctrX, ctrY);
        }
        return new SplineCurve(x, curves);
    }
    
    
    
}
