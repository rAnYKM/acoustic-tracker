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
    private int continuousNoiseSlots;

    private void initialize() {
        firstFlag = false;
        firstOffset = 0;
        continuousNoiseSlots = 0;
    }


    public void add(float[] data) {
        rawMagnitude.add(data);

        if (!firstFlag) {
            if (!isRawNoise(data)) firstFlag = true;
            else firstOffset = firstOffset + 1;
            return;
        }
        float[] delta = new float[data.length];
        try {
            delta = MathHelper.absSubtract(data, rawMagnitude.get(rawMagnitude.size() - 2));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: Extract static objects

        if (isRawNoise(data) || isDiffNoise(delta)) {
            diffMagnitude.add(new float[data.length]);
            continuousNoiseSlots = continuousNoiseSlots + 1;
            // TODO: Add log information later
        } else {
            diffMagnitude.add(delta);
            continuousNoiseSlots = 0;
        }

        // Candidate selection code

    }


    public void test() {
        float[] numbers = {4,3,1,1,3,5, 87, 12, 9};
        System.out.println(MathHelper.average(numbers));
        System.out.println(MathHelper.median(numbers));
        float[] results = MathHelper.upperMAD(numbers, 3.0f);
        for(float result: results) System.out.println(result);
    }

    public UserTracker() {
        // read the default settings
        AcousticSettings as = new AcousticSettings();
        dUnit = as.getDistanceUnit();
        tUnit = as.getTimeUnit();
        ts = new TrackerSettings();
        initialize();
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
        return MathHelper.median(col) > ts.getFarRawNoise();
    }

    private boolean isDiffNoise(float[] col) {
        return MathHelper.average(col) > ts.getFarDiffNoise();
    }

}
