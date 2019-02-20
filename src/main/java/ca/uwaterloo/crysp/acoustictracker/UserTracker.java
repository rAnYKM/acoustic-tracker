package ca.uwaterloo.crysp.acoustictracker;

import java.util.List;

import ca.uwaterloo.crysp.acoustictracker.settings.AcousticSettings;
import ca.uwaterloo.crysp.acoustictracker.settings.TrackerSettings;
import ca.uwaterloo.crysp.acoustictracker.utils.MathHelper;

public class UserTracker {
    private float dUnit;
    private float tUnit;
    private TrackerSettings ts;

    private List<float[]> rawMagnitude;
    private List<float[]> diffMagnitude;

    private boolean firstFlag;
    private int firstOffset;

    private void initialize() {
        firstFlag = false;
        firstOffset = 0;
    }


    public void add(float[] data) {
        rawMagnitude.add(data);
    }


    public void test() {
        float[] numbers = {4,3,1,1,3,5, 87, 12, 9};
        System.out.println(MathHelper.getAverage(numbers));
        System.out.println(MathHelper.getMedian(numbers));
        float[] results = MathHelper.getUpperMAD(numbers, 3.0f);
        for(float result: results) System.out.println(result);
    }

    public UserTracker() {
        // read the default settings
        AcousticSettings as = new AcousticSettings();
        dUnit = as.getDistanceUnit();
        tUnit = as.getTimeUnit();
        ts = new TrackerSettings();
    }

    // =======================================================
    //  Distance index converter
    // =======================================================

    public float indexToDistance(int index) {
        return (float) index * dUnit;
    }

    public int distanceToIndex(float distance) {
        return (int) Math.floor(distance / dUnit);
    }

    private boolean isRawNoise(float[] col) {
        // TODO:
        return false;
    }

}
