/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.onesmash.splineinterpolation;

import org.apache.commons.math3.linear.*;

/**
 *
 * @author xuhui
 */
public class BezierSplineInterpolator implements Interpolator {

    @Override
    public SplineCurve interpolate(double[] x, double[] y) {
        int bCtrPNum = x.length;
        if(bCtrPNum > 3) {
            RealMatrix m141 = createMatrix141(bCtrPNum - 2);
            RealMatrix inverseM = (new LUDecomposition(m141)).getSolver().getInverse();
            RealMatrix endPM = createMatrixEndP(x, y);
            RealMatrix res = inverseM.multiply(endPM);
            RealMatrix bCtrM = MatrixUtils.createRealMatrix(bCtrPNum, 2);
            bCtrM.setEntry(0, 0, x[0]);
            bCtrM.setEntry(0, 1, y[0]);
            for(int i = 0; i < res.getRowDimension(); i++) {
                bCtrM.setEntry(i + 1, 0, res.getEntry(i, 0));
                bCtrM.setEntry(i + 1, 1, res.getEntry(i, 1));
            }
            bCtrM.setEntry(bCtrPNum - 1, 0, x[bCtrPNum - 1]);
            bCtrM.setEntry(bCtrPNum - 1, 1, y[bCtrPNum - 1]);
            int segments = bCtrPNum - 1;
            Curve[] curves =  new CubicBezierCurve[segments];
            double[] ctrX = new double[4];
            double[] ctrY = new double[4];
            for(int i = 0; i < bCtrM.getRowDimension() - 1; i++) {
                double[] bPointCur =  bCtrM.getRow(i);
                double[] bPointNext =  bCtrM.getRow(i + 1);
                double deltaX = (bPointNext[0] - bPointCur[0]) / 3;
                double deltaY = (bPointNext[1] - bPointCur[1]) / 3;
               
                ctrX[0] = x[i];
                ctrX[1] = bCtrM.getEntry(i, 0) + deltaX;
                ctrX[2] = bCtrM.getEntry(i, 0) + 2 * deltaX;
                ctrX[3] = x[i + 1];
                ctrY[0] = y[i];
                ctrY[1] = bCtrM.getEntry(i, 1) + deltaY;
                ctrY[2] = bCtrM.getEntry(i, 1) + 2 * deltaY;
                ctrY[3] = y[i + 1];
                curves[i] = new CubicBezierCurve(ctrX, ctrY);
            }
            return new SplineCurve(x, curves);
        }
        if(bCtrPNum == 2) {
            double[] ctrX = new double[4];
            double[] ctrY = new double[4];
            ctrX[0] = x[0];
            ctrX[1] = x[0];
            ctrX[2] = x[1];
            ctrX[3] = x[1];
            ctrY[0] = y[0];
            ctrY[1] = y[0];
            ctrY[2] = y[1];
            ctrY[3] = y[1];
            Curve[] curves =  new CubicBezierCurve[1];
            curves[0] = new CubicBezierCurve(ctrX, ctrY);
            return new SplineCurve(x, curves);
        }
        if(bCtrPNum == 3) {
            double b1X = 1.5 * x[1] - x[0] / 4 - x[2] / 4;
            double b1Y = 1.5 * y[1] - y[0] / 4 - y[2] / 4;
            RealMatrix bCtrM = MatrixUtils.createRealMatrix(bCtrPNum, 2);
            bCtrM.setEntry(0, 0, x[0]);
            bCtrM.setEntry(0, 1, y[0]);
            bCtrM.setEntry(1, 0, b1X);
            bCtrM.setEntry(1, 1, b1Y);
            bCtrM.setEntry(2, 0, x[2]);
            bCtrM.setEntry(2, 1, y[2]);
            int segments = bCtrPNum - 1;
            Curve[] curves = new CubicBezierCurve[segments];
            double[] ctrX = new double[4];
            double[] ctrY = new double[4];
            for(int i = 0; i < 2; i++) {
                double[] bPointCur =  bCtrM.getRow(i);
                double[] bPointNext =  bCtrM.getRow(i + 1);
                double deltaX = (bPointNext[0] - bPointCur[0]) / 3;
                double deltaY = (bPointNext[1] - bPointCur[1]) / 3;
               
                ctrX[0] = x[i];
                ctrX[1] = bCtrM.getEntry(i, 0) + deltaX;
                ctrX[2] = bCtrM.getEntry(i, 0) + 2 * deltaX;
                ctrX[3] = x[i + 1];
                ctrY[0] = y[i];
                ctrY[1] = bCtrM.getEntry(i, 1) + deltaY;
                ctrY[2] = bCtrM.getEntry(i, 1) + 2 * deltaY;
                ctrY[3] = y[i + 1];
                curves[i] = new CubicBezierCurve(ctrX, ctrY);
            }
            return new SplineCurve(x, curves);
        }
        return null;
    }
    
    private RealMatrix createMatrix141(int size) {
        ArrayRealVector v = new ArrayRealVector(size, 4);
        RealMatrix m = MatrixUtils.createRealDiagonalMatrix(v.getDataRef());
        for (int i = 0; i < size; i++) {
            int left = i - 1;
            int right = i + 1;
            if(left >= 0) {
                m.setEntry(i, left, 1);
            }
            if(right < size) {
                m.setEntry(i, right, 1);
            }
        }
        
        return m;
    }
    
    private RealMatrix createMatrixEndP(double x[], double y[]) {
        int rows = x.length - 2;
        RealMatrix m = null; 
        
        if(rows > 0) {
            m = MatrixUtils.createRealMatrix(rows, 2);
            for(int i = 0; i < rows; i++) {
                m.setEntry(i, 0, 6 * x[i + 1]);
                m.setEntry(i, 1, 6 * y[i + 1]);
            }
            m.setEntry(0, 0, 6 * x[1] - x[0]);
            m.setEntry(0, 1, 6 * y[1] - y[0]);
            if(rows >= 2) {
                int lastRow = rows - 1;
                m.setEntry(lastRow, 0, 6 * x[x.length - 2] - x[x.length - 1]);
                m.setEntry(lastRow, 1, 6 * y[y.length - 2] - y[y.length - 1]);
            }
        }
        
        return m;
       
    }
    
    
    public static void main (String[] args) {
        BezierSplineInterpolator interpolator = new BezierSplineInterpolator();
        double[] x = {1, 2, 3, 4};
        double[] y = {1, 2, 3, 4};
        interpolator.interpolate(x, y);
    }
    
}
