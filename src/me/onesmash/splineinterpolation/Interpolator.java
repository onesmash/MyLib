/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.onesmash.splineinterpolation;

/**
 *
 * @author xuhui
 */
public interface Interpolator {
    
    SplineCurve interpolate(double x[], double y[]);
    
}
