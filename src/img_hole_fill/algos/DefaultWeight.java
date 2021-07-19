package img_hole_fill.algos;

import img_hole_fill.utils.IntPair;

/**
 * The default weight function for applying an algorithm for filling a hole in an image
 */
public record DefaultWeight(float z, float epsilon) implements WeightFunction {
    static final float DEFAULT_Z = 3;
    static final float DEFAULT_EPSILON = (float) 0.01;
    static final String name = "DefaultWeight";

    /**
     * Create a new default weight function
     *
     * @param z       The exponent of the distance
     * @param epsilon The small value to add to the denominator
     */
    public DefaultWeight {}

    /**
     * Getter for default weight function with default parameters
     *
     * @return The new instance produced
     */
    public static DefaultWeight getInstance() {
        return new DefaultWeight(DEFAULT_Z, DEFAULT_EPSILON);
    }

    /**
     * Gets the weight of two given pixels
     *
     * @param p1 First pixel
     * @param p2 Second Pixel
     * @return The weight
     */
    @Override
    public float getWeight(IntPair p1, IntPair p2) {
        float result;
        float eucDist = (float) Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) +
                Math.pow((p2.getY() - p1.getY()), 2));
        result = (float) (1.0 / (Math.pow(eucDist, z) + epsilon));
        return result;
    }

    /**
     * This is used in order to concat this string to the file name of the image file generated using this
     * weight function
     *
     * @return The string to concat
     */
    @Override
    public String toString() {
        return name;
    }
}
