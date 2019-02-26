package ca.uwaterloo.crysp.acoustictracker;

import ca.uwaterloo.crysp.acoustictracker.helpers.Kalman1D;
import ca.uwaterloo.crysp.acoustictracker.utils.DataLoader;

import java.util.List;

public class TestInterface {
    public static void main(String[] args) {
        UserTracker ut = new UserTracker();
        Kalman1D km = new Kalman1D(0.25f, 0.001f, 0.0f, 1.0f);
        float[] observations = {2, 3, 3.7f, 4.2f, 5.2f, 5.9f, 6.7f, 6};
        for(float obs: observations) {
            float[] results = km.add(obs);
            System.out.println(results[0]);
        }
        System.out.println("===================");
        List<float[]> mList = DataLoader.readRawFile("/Users/jiayichen/Dropbox/data_analysis/real_segments/real_dccafe_pixel_ba_window_3_ch1_clip85_185.txt");
        for(float[] data: mList) {
            ut.add(data);
        }
    }
}
