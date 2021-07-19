package img_hole_fill.algos;

import img_hole_fill.utils.GeneralUtils;
import img_hole_fill.utils.IntPair;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A general template for algorithms using representatives pixels in the process of filling a hole in an
 * image
 */
public abstract class RepresentativesApproach {
    /**
     * The set of representatives
     */
    protected HashSet<IntPair> repsSet;

    /**
     * The list of representatives. A list is used here in addition to a set in order to collect the
     * representatives by their indices. And a set is used in order to comply with the API of the
     * FillHoleAlgo which uses HashSets as this class is used by algorithms for filling holes in
     * an image.
     */
    protected ArrayList<IntPair> repsLst;

    /**
     * The amount of representatives to collect
     */
    protected int repsAmount;

    /**
     * Initializes the amount of representatives in the pixSet
     */
    protected void initRepsAmount() {
        repsAmount = GeneralUtils.getLog2(repsLst.size());
    }
}
