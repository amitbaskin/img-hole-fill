package img_hole_fill.utils;

/**
 * An interface for deciding when a given pixel is considered part of a hole
 */
public interface HoleCondition {

    /**
     * Determine if the given pixel is a hole
     * @param grayMat The matrix of the values of the pixels in a grayscale image
     * @param x The x value of the pixel to check
     * @param y The y value of the pixel to check
     * @return Whether or not this pixel is considered part of the hole
     */
    boolean isHole(float[][] grayMat, int x, int y);
}
