package img_hole_fill.algos;

import img_hole_fill.utils.IntPair;
import java.util.HashSet;

/**
 * An interface for a hole-filling algorithm
 */
public interface FillHoleAlgo {

    /**
     * Filling the hole
     * @param grayMat The matrix to fill its values
     * @param holeSet The pixels in the hole
     * @param boundarySet The pixels in the boundary
     * @param func The weighting function to use while calculating the values to fill with
     * @param isEightConnected Whether or not to apply the 8-connected approach (4-connected is the default)
     */
    void apply(float[][] grayMat, HashSet<IntPair> holeSet, HashSet<IntPair> boundarySet,
               WeightFunction func, boolean isEightConnected);
}
