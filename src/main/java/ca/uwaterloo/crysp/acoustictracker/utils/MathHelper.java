package ca.uwaterloo.crysp.acoustictracker.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MathHelper {

    public static float[] absSubtract(float[] current, float[] previous) throws Exception {
        if (current.length != previous.length) throw new Exception("the size of input data does not match");
        float[] results = new float[current.length];
        for(int i = 0; i < current.length; ++i) {
            results[i] = Math.abs(current[i] - previous[i]);
        }
        return results;
    }

    public static float average(@NotNull float[] values) {
        float result = 0.0f;
        for(float value: values) {
            result = result + value;
        }
        return result / values.length;
    }

    public static float median(@NotNull float[] values) {
        float[] sorted = new float[values.length];
        System.arraycopy(values,0, sorted,0, sorted.length);
        Arrays.sort(sorted);

        if (sorted.length % 2 == 0) {
            return (sorted[(sorted.length / 2) - 1] + sorted[sorted.length / 2]) / 2;
        } else {
            return sorted[sorted.length / 2];
        }
    }

    public static List<Integer> upperMADIndex(float[] values, float multiplier) {
        float[] dev = new float[values.length];
        List<Integer> indices = new ArrayList<Integer>();
        float med = median(values);
        for(int i = 0; i < dev.length; ++i) {
            dev[i] = Math.abs(values[i] - med);
        }
        float mdev = median(dev);
        if (mdev == 0.0f) {
            return null;
        }
        for(int i = 0; i < dev.length; ++i) {
            if (dev[i] / mdev > multiplier) {
                indices.add(i);
            }
        }
        return indices;
    }

    public static float[] upperMAD(float[] values, float multiplier) {
        List<Integer> indices = upperMADIndex(values, multiplier);
        float[] results = new float[indices.size()];
        for(int i = 0; i < results.length; ++i) results[i] = values[indices.get(i)];
        return results;
    }
}
