import java.util.List;

public class UserTracker {
    private float dUnit;
    private float tUnit;

    private List<float[]> rawMagnitude;
    private List<float[]> diffMagnitude;

    public void add(float[] data) {
        rawMagnitude.add(data);

    }

    public UserTracker() {
        // read the default settings
        TrackerProfile tp = new TrackerProfile();
        dUnit = tp.getDistanceUnit();
        tUnit = tp.getTimeUnit();
    }
}
