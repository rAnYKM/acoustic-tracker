package ca.uwaterloo.crysp.acoustictracker.utils;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.List;

// To further improve the performance, may adopt Procedural functions

public class Kalman1D {
    private float dt;
    private SimpleMatrix F;
    private SimpleMatrix G;
    private SimpleMatrix H;
    private SimpleMatrix x0;
    private SimpleMatrix Q;
    private SimpleMatrix R;
    private List<SimpleMatrix> xHat;
    private List<SimpleMatrix> pHat;

    public float[] add(float obs) {
        float[][] arrayObs = {{obs}};
        SimpleMatrix ob = new SimpleMatrix(arrayObs);
        SimpleMatrix xm = F.mult(xHat.get(xHat.size() - 1));
        SimpleMatrix pm = F.mult(pHat.get(pHat.size() - 1)).mult(F.transpose()).plus(Q);
        SimpleMatrix inverted = H.mult(pm).mult(H.transpose()).plus(R).invert();
        SimpleMatrix K = pm.mult(H.transpose()).mult(inverted);
        SimpleMatrix xt = xm.plus(K.mult(ob.minus(H.mult(xm))));
        SimpleMatrix pt = SimpleMatrix.identity(2).minus(K.mult(H)).mult(pm);
        xHat.add(xt.copy());
        pHat.add(pt.copy());
        float[] results = {(float)xt.get(0), (float)xt.get(1)};
        return results;
    }

    public Kalman1D(float t, float q, float r, float d0, float p0) {
        dt = t;
        float[][] arrayF = {{1, dt}, {0, 1}};
        F = new SimpleMatrix(arrayF);
        float[][] arrayG = {{dt * dt / 2}, {dt}};
        G = new SimpleMatrix(arrayG);
        float[][] arrayH = {{1, 0}};
        H = new SimpleMatrix(arrayH);
        float[][] arrayX0 = {{d0}, {0}};
        x0 = new SimpleMatrix(arrayX0);
        float[][] arrayQ = {{q, 0}, {0, q}};
        Q = new SimpleMatrix(arrayQ);
        float[][] arrayR = {{r}};
        R = new SimpleMatrix(arrayR);
        xHat = new ArrayList<SimpleMatrix>();
        xHat.add(x0.copy());
        float[][] arrayP0 = {{p0, 0}, {0, p0}};
        pHat = new ArrayList<SimpleMatrix>();
        pHat.add(new SimpleMatrix(arrayP0));
    }
}
