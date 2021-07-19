package img_hole_fill.utils;

import java.util.HashSet;

/**
 * Getting the outer boundary pixels of a hole in an image
 */
public abstract class BoundaryUtils {

    /**
     * gets the pixels outside the hole that connect to the hole directly
     * @param holeSet The pixels in the hole
     * @param isEightConnected Whether or not to apply 8-connected (the default is 4)
     * @return The outer boundary of the hole
     */
    public static HashSet<IntPair> getBoundarySet(HashSet<IntPair> holeSet, boolean isEightConnected){
        HashSet<IntPair> connectedSet = PixConnectUtils.getEntireFourConnected(holeSet);
        if (isEightConnected) PixConnectUtils.completeEntireEightConnected(holeSet, connectedSet);
        GeneralUtils.applySetsDiff(connectedSet, holeSet);
        return connectedSet;
    }
}
