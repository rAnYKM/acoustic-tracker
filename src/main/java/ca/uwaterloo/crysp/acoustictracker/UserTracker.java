package ca.uwaterloo.crysp.acoustictracker;

import java.util.ArrayList;
import java.util.Arrays;
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

        if (isRawNoise(data) || isDiffNoise(delta) || hasGlitch(data)) {
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

    // =======================================================
    //  Noise detection
    // =======================================================

    private boolean isRawNoise(float[] col) {
        return MathHelper.median(Arrays.copyOfRange(col, ts.getFarIndex(), col.length)) > ts.getFarRawNoise();
    }

    private boolean isDiffNoise(float[] col) {
        return MathHelper.average(Arrays.copyOfRange(col, ts.getFarIndex(), col.length)) > ts.getFarDiffNoise();
    }

    private boolean hasGlitch(float[] col) {
        int size = 0;
        for (float elem: col)
            if (elem > ts.getGlitchNoise()) size++;
        return size >= ts.getGlitchSize();
    }

    // =======================================================
    //  Candidate selection algorithms
    // =======================================================

    // candidate format: {startIndex, size, peakIndex}
    private List<int[]> detectCandidates(float[] col) {
        // exclude the direct transmission
        float[] trimmedCol = new float[col.length];
        System.arraycopy(col, 0, trimmedCol, 0, col.length);
        for(int i = 0; i < ts.getEdgeIndex(); ++i) trimmedCol[i] = 0.0f;
        for(int i = ts.getEdgeIndex(); i < ts.getStartIndex(); ++ i)
            if (trimmedCol[i] < ts.getDirectHighMagnitude()) trimmedCol[i] = 0.0f;

        List<Integer> outliers = MathHelper.upperMADIndex(trimmedCol, ts.getOutlierMultiplier());
        List<int[]> candidates = new ArrayList<int[]>();
        // clustering the outliers - 1D clustering
        int formerIndex = -1;
        for(int index: outliers) {
            if (candidates.size() == 0) {
                int[] tmp = {index, 1, index};
                candidates.add(tmp);
            } else if (index - formerIndex > ts.getClusterTolerance()) {
                    int[] tmp = {index, 1, index};
                    candidates.add(tmp);
            } else {
                    int[] tmp = candidates.get(candidates.size() - 1);
                    // update size and peak
                    tmp[1] = index - formerIndex;
                    if (trimmedCol[tmp[2]] < trimmedCol[index]) tmp[2] = index;
            }
            formerIndex = index;
        }
        return candidates;
    }

}
