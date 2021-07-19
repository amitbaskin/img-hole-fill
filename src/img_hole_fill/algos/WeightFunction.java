package img_hole_fill.algos;

import img_hole_fill.utils.IntPair;

/**
 * An interface for a weighting function used by algorithms to fill a hole in an image
 */
public interface WeightFunction {

    /**
     * Gets the weight
     * @param p1 First point
     * @param p2 Second point
     * @return The weight
     */
    float getWeight(IntPair p1, IntPair p2);
}
