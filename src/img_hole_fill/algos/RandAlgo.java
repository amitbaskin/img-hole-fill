package img_hole_fill.algos;

import img_hole_fill.utils.GeneralUtils;
import img_hole_fill.utils.IntPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * General template for using randomization for filling a hole in an image using log(n) representatives
 * where n is the number of pixels in the hole to fill. The goal is to achieve the solution in O(nlog(n))
 * instead of O(n^2) as in the basic solution.
 */
public abstract class RandAlgo extends RepresentativesApproach{

    /**
     * Initializes the list from which to get random representatives. The transformation to a list is used
     * in order to collect the representatives by indices.
     * @param randSet The set from which to get random representatives
     */
    protected void initRandLst(HashSet<IntPair> randSet){
        repsLst = new ArrayList<>(randSet);
        Collections.shuffle(repsLst);
    }

    /**
     * Initializes the set of representatives
     */
    protected void initRepsSet(){
        repsSet = new HashSet<>();
        for (int i=0; i<repsAmount; i++){
            repsSet.add(repsLst.get(i));
        }
    }

    /**
     * initializes the amount of representatives to collect
     */
    protected void initRepsAmount() {
        repsAmount = GeneralUtils.getLog2(repsLst.size());
    }

    /**
     * Prepares the ground to apply the randomization algo
     * @param randSet The set of representatives
     */
    protected void initHelper(HashSet<IntPair> randSet){
        initRandLst(randSet);
        initRepsAmount();
        initRepsSet();
    }
}
