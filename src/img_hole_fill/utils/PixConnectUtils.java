package img_hole_fill.utils;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Utilities for getting connected pixels
 */
public abstract class PixConnectUtils {

    /**
     * Get the 4-connected pixels to the given pixel
     * @param pix The given pixel to get its neighbours
     * @return The neighbours of the given pixel
     */
    public static ArrayList<IntPair> getSingleFourConnected(IntPair pix){
        ArrayList<IntPair> singleFourConnected = new ArrayList<>(4);
        singleFourConnected.add(new IntPair(pix.getX() + 1, pix.getY()));
        singleFourConnected.add(new IntPair(pix.getX() - 1, pix.getY()));
        singleFourConnected.add(new IntPair(pix.getX(), pix.getY() + 1));
        singleFourConnected.add(new IntPair(pix.getX(), pix.getY() - 1));
        return singleFourConnected;
    }

    /**
     * Get the complement of 4-connected to 8-connected
     * @param pix The given pixel to get its neighbours
     * @return The complement of 4-connected to 8-connected
     */
    public static ArrayList<IntPair> getSingleFourComplementToEight(IntPair pix){
        ArrayList<IntPair> singleEightConnected = new ArrayList<>(4);
        singleEightConnected.add(new IntPair(pix.getX() + 1, pix.getY() + 1));
        singleEightConnected.add(new IntPair(pix.getX() + 1, pix.getY() - 1));
        singleEightConnected.add(new IntPair(pix.getX() - 1, pix.getY() + 1));
        singleEightConnected.add(new IntPair(pix.getX() - 1, pix.getY() - 1));
        return singleEightConnected;
    }

    /**
     * Get the entire 4-connected neighbours of the hole set
     * @param holeSet The pixels in the hole
     * @return The neighbours
     */
    public static HashSet<IntPair> getEntireFourConnected(HashSet<IntPair> holeSet){
        HashSet<IntPair> fourConnectedSet = new HashSet<>();
        for (IntPair hole : holeSet){
            fourConnectedSet.addAll(getSingleFourConnected(hole));
        } return fourConnectedSet;
    }

    /**
     * Complement the entire 4-connected to the entire 8-connected
     * @param holeSet The pixels in the hole
     * @param fourConnectedSet The 4-connected neighbours of the pixels in the hole
     */
    public static void completeEntireEightConnected(HashSet<IntPair> holeSet,
                                                    HashSet<IntPair> fourConnectedSet){
        for (IntPair hole : holeSet){
            fourConnectedSet.addAll(getSingleFourComplementToEight(hole));
        }
    }
}
