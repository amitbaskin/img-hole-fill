package img_hole_fill.utils;

/**
 * Classifying a hole strictly by a certain value
 */
public class StrictHoleCondition implements HoleCondition{
    static final float HOLE_MAT_VAL = -1;

    /**
     * The single instance of this class
     */
    private static final StrictHoleCondition singleton = new StrictHoleCondition();

    /**
     * Get a StrictHoleCondition instance
     * @return A StrictHoleCondition instance
     */
    public static StrictHoleCondition getInstance(){
        return singleton;
    }

    /**
     * Using a private modifier in order to apply the singleton design pattern
     */
    private StrictHoleCondition(){}

    /**
     * The condition
     * @param grayMat The gray scale matrix representing the pixels of the image
     * @param x The x coordinate of the point to check
     * @param y The y coordinate of the point to check
     * @return True iif the given point is classified as hole pixel
     */
    @Override
    public boolean isHole(float[][] grayMat, int x, int y) {
        return grayMat[x][y] == HOLE_MAT_VAL;
    }
}
