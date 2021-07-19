package img_hole_fill.utils;

/**
 * Classifying a pixel as a hole using a threshold
 */
public class ThresholdHoleCondition implements HoleCondition{
    private static final float HOLE_THRESHOLD = (float) 0.5;

    /**
     * The single instance of this class
     */
    private static final ThresholdHoleCondition singleton = new ThresholdHoleCondition();

    /**
     * Get an instance of ThresholdHoleCondition
     * @return An instance of ThresholdHoleCondition
     */
    public static ThresholdHoleCondition getInstance(){
        return singleton;
    }
    /**
     * Using the private modifier in order to apply the singleton design pattern
     */
    private ThresholdHoleCondition(){}

    /**
     * The condition to classify by
     * @param grayMat The gray scale matrix representing the pixels of the image
     * @param x The x coordinate of the point to classify
     * @param y The y coordinate of the point to classify
     * @return True iff the point is classified as a hole pixel
     */
    @Override
    public boolean isHole(float[][] grayMat, int x, int y) {
        return grayMat[x][y] < HOLE_THRESHOLD;
    }
}
