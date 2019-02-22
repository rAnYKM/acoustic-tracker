package ca.uwaterloo.crysp.acoustictracker.settings;

public class TrackerSettings {
    private final static int WINDOW_SIZE = 5;
    private String settingName;

    // =======================================================
    //  Direct transmission interference exclusion parameters
    // =======================================================

    private int edgeIndex; // the magnitude values ahead of edgeIndex are discarded (set as 0)
    private int startIndex; // the magnitude values ahead of startIndex are filtered by directHighMagnitude
    private float directHighMagnitude;

    // =======================================================
    //  Noise exclusion parameters
    // =======================================================

    private int farIndex;
    private float farRawNoise;
    private float farDiffNoise;
    private float glitchNoise;
    private int glitchSize;

    // =======================================================
    //  Outlier detection parameters
    // =======================================================

    private float outlierMultiplier;
    private int clusterTolerance;
    private int maxMovementIndex;
    private int minMovementIndex;
    private float movementDiscount;

    // =======================================================
    //  Kalman filter parameters
    // =======================================================

    private float kalmanP;
    private float kalmanQ;
    private int kalmanDefaultDistance;

    public String getSettingName() { return settingName; }
    public int getEdgeIndex() { return edgeIndex; }
    public int getStartIndex() { return startIndex; }
    public float getDirectHighMagnitude() { return directHighMagnitude; }
    public int getFarIndex() { return farIndex; }
    public float getFarRawNoise() { return farRawNoise; }
    public float getFarDiffNoise() { return farDiffNoise; }
    public float getGlitchNoise() { return glitchNoise; }
    public int getGlitchSize() { return glitchSize; }
    public float getOutlierMultiplier() { return outlierMultiplier; }
    public int getClusterTolerance() { return clusterTolerance; }
    public int getMaxMovementIndex() { return maxMovementIndex; }
    public int getMinMovementIndex() { return minMovementIndex; }
    public float getMovementDiscount() { return movementDiscount; }
    public float getKalmanP() { return kalmanP; }
    public float getKalmanQ() { return kalmanQ; }
    public int getKalmanDefaultDistance() { return kalmanDefaultDistance; }

    public void setSettingName(String name) { this.settingName = name; }
    public void setEdgeIndex(int index) { this.edgeIndex = index; }
    public void setStartIndex(int index) {this.startIndex = index; }
    public void setDirectHighMagnitude(float magnitude) { this.directHighMagnitude = magnitude; }
    public void setFarIndex(int index) { this.farIndex = index; }
    public void setFarRawNoise(float magnitude) { this.farRawNoise = magnitude; }
    public void setFarDiffNoise(float magnitude) { this.farDiffNoise = magnitude; }
    public void setGlitchNoise(float magnitude) { this.glitchNoise = magnitude; }
    public void setGlitchSize(int size) { this.glitchSize = size; }
    public void setOutlierMultiplier(float n) { this.outlierMultiplier = n; }
    public void setClusterTolerance(int n) { this.clusterTolerance = n; }
    public void setMaxMovementIndex(int size) { this.maxMovementIndex = size; }
    public void setMinMovementIndex(int size) { this.minMovementIndex = size; }
    public void setMovementDiscount(float value) { this.movementDiscount = value; }
    public void setKalmanP(float p) { this.kalmanP = p; }
    public void setKalmanQ(float q) { this.kalmanQ = q; }
    public void setKalmanDefaultDistance(int index) { this.kalmanDefaultDistance = index; }

    public TrackerSettings() {
        setSettingName("Generic");
        setEdgeIndex(3);
        setStartIndex(11);
        setDirectHighMagnitude(3000);
        setFarIndex(100);
        setFarRawNoise(400.0f);
        setFarDiffNoise(120.0f);
        setGlitchNoise(4000.0f);
        setGlitchSize(10);
        setOutlierMultiplier(3);
        setClusterTolerance(3);
        setMaxMovementIndex(35);
        setMinMovementIndex(10);
        setMovementDiscount(0.95f);
        setKalmanP(0.25f);
        setKalmanQ(1e-3f);
        setKalmanDefaultDistance(15);
    }



}
