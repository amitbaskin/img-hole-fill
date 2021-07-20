package img_hole_fill.algos;

import img_hole_fill.utils.GeneralUtils;
import img_hole_fill.utils.IntPair;
import java.util.HashSet;


/**
 * An algorithm for filling a hole in an image in O(nlog(n)) where n is the number of pixels in the hole.
 * It works by taking log(n) points on the hole in equal distances and filling them using the entire
 * boundary. Next it fills the rest of the hole using the log(n) pixels which were filled at the beginning.
 */
public class HolesLineSpaceAlgo extends LineSpace implements FillHoleAlgo{

    /**
     * Factory for the class
     * @return An instance of this class
     */
    public static HolesLineSpaceAlgo getHolesLineSpaceAlgoInstance(){
        return new HolesLineSpaceAlgo();
    }

    /**
     * Hiding the constructor
     */
    private HolesLineSpaceAlgo(){}

    /**
     * Applying the algorithm to fill the hole
     * @param grayMat The matrix to fill its values
     * @param holeSet The pixels in the hole
     * @param boundarySet The pixels in the boundary
     * @param func The weighting function to use while calculating the values to fill with
     * @param isEightConnected Whether or not to apply the 8-connected approach (4-connected is the default)
     */
    @Override
    public void apply(float[][] grayMat, HashSet<IntPair> holeSet, HashSet<IntPair> boundarySet,
                      WeightFunction func, boolean isEightConnected) {
        initLineSpace(holeSet);
        HashSet<IntPair> holeReps = new HashSet<>(repsLst);
        GeneralUtils.applySetsDiff(holeSet, holeReps);
        BaseAlgo baseAlgo = BaseAlgo.getInstance();
        baseAlgo.apply(grayMat, holeReps, boundarySet, func, isEightConnected);
        baseAlgo.apply(grayMat, holeSet, holeReps, func, isEightConnected);
    }

    /**
     * This is used in the writing of the result generated by this algo. The output string will be
     * part of the name of the output image.
     * @return The string that should be concatenated with the file name of the output image generated by
     * this algo
     */
    @Override
    public String toString(){
        return "HoleLineSpaceAlgo";
    }
}
