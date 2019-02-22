package ca.uwaterloo.crysp.acoustictracker;

import ca.uwaterloo.crysp.acoustictracker.utils.Kalman1D;

public class TestInterface {
    public static void main(String[] args) {
        UserTracker ut = new UserTracker();
        ut.test();
        Kalman1D km = new Kalman1D(0.05f, 0.25f, 0.001f, 0.0f, 1.0f);
        float[] observations = {2, 3, 3.7f, 4.2f, 5.2f, 5.9f, 6.7f, 6};
        for(float obs: observations) {
            float[] results = km.add(obs);
            System.out.println(results[0]);
        }

    }
}
