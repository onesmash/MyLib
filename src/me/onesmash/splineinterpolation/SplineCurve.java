/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.onesmash.splineinterpolation;

import java.util.Arrays;

/**
 *
 * @author xuhui
 */
public class SplineCurve {
    
    private final double[] knots;
    
    private final int segments;
    
    private final Curve[] curves;

    public SplineCurve(double[] knots, Curve[] curves) {
        segments = knots.length - 1; 
        this.knots = new double[segments + 1];
        System.arraycopy(knots, 0, this.knots, 0, segments + 1);
        this.curves = new Curve[segments];
        System.arraycopy(curves, 0, this.curves, 0, segments);
    }
    
    //public double value(double x) {
    //    int i = Arrays.binarySearch(knots, x);
    //    if(i >= curves.length) {
    //        i--;
    //    }
    //    return curves[i].value(x);
    //}
    
    public Curve[] getCurves() {
        Curve[] cs = new Curve[segments];
        System.arraycopy(curves, 0, cs, 0, segments);
        return cs;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < curves.length; i++) {
            str.append(curves[i].toString());
            str.append("\n");
        }
        return str.toString();
    }
    
    
    
    
}
