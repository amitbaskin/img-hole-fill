package img_hole_fill.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * General utilities to use throughout the process of filling a hole in an image
 */
public abstract class GeneralUtils {

    /**
     * Removes one set from the other
     * @param targetSet The set in interest to remove pixels from it
     * @param removeSet The set of pixels to remove from the target set
     */
    public static void applySetsDiff(HashSet<IntPair> targetSet, HashSet<IntPair> removeSet){
        targetSet.removeAll(removeSet);
    }

    /**
     * Creates an array list of IntPairs out of a hash set and sorts it
     * @param sourceSet The set to sort
     * @return The sorted list
     */
    public static ArrayList<IntPair> getSortedLst(HashSet<IntPair> sourceSet){
        ArrayList<IntPair> lst = new ArrayList<>(sourceSet);
        Collections.sort(lst);
        return lst;
    }

    /**
     * Gets the log in base 2 of a given number
     * @param num The number to gets its log
     * @return The result
     */
    public static int getLog2(int num) {
        return (int) (Math.log(num) / Math.log(2));
    }

    /**
     * Copying a float 2D array (used for the accuracy test)
     * @param org The array to copy
     * @return The copied array
     */
    public static float[][] copyArr(float[][] org){
        float[][] copy = new float[org.length][org[0].length];
        for (int i=0; i<org.length; i++){
            System.arraycopy(org[i], 0, copy[i], 0, org[0].length);
        } return copy;
    }
}
