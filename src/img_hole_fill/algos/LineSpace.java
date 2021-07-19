package img_hole_fill.algos;

import img_hole_fill.utils.GeneralUtils;
import img_hole_fill.utils.IntPair;
import java.util.HashSet;

/**
 * A general template to get log(n) "representatives" in equal distances from each other out of a given set
 * of IntPairs
 */
public abstract class LineSpace extends RepresentativesApproach{

    /**
     * The distance that should be between one collected representative to the next collected one
     */
    protected int repsDist;

    /**
     * The index of the next representative to collect
     */
    protected int nextRepIndex = 0;

    /**
     * Initializes the template of the line space
     * @param repsSource The set from which to collect the representatives
     */
    protected void initLineSpace(HashSet<IntPair> repsSource){
        repsLst = GeneralUtils.getSortedLst(repsSource);
        initRepsAmount();
        initRepsDist();
        initReps();
    }

    /**
     * Collecting the representatives
     */
    protected void initReps(){
        repsSet = new HashSet<>(repsAmount);
        for (int i = 0; i < repsDist; i++) {
            repsSet.add(repsLst.get(nextRepIndex));
            incrementNextRepIndex();
        }
    }

    /**
     * Initializes the distance that should be between every two representatives
     */
    protected void initRepsDist() {
        repsDist = repsLst.size() / repsAmount;
    }

    /**
     * Initializes the index of the next "representative" on the source set
     */
    protected void incrementNextRepIndex() {
        nextRepIndex = (nextRepIndex + repsDist) % repsLst.size();
    }
}
