package img_hole_fill.algos;

import img_hole_fill.utils.IntPair;
import img_hole_fill.utils.PixConnectUtils;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Approximating the basic solution in O(n) instead of O(n^2) where n is the number of pixels in the hole
 */
public record ApproxAlgo(boolean isLargeInnerLayer, boolean isAddToBoundaryAsWeGo,
                         boolean isEntireBoundary) implements FillHoleAlgo {
    private static final int MAX_CONNECTED = 8;
    private static final String name = "Approx";

    /**
     * Gets the connected pixels for a given pixel, 4-connected or 8-connected, depends on the input
     * @param pix The pixel to get the connected pixels to it
     * @param isEightConnected Whether or not to apply 8-connected (the default is 4)
     * @return The list of connected pixels
     */
    private static ArrayList<IntPair> getConnectedPixels(IntPair pix, boolean isEightConnected) {
        ArrayList<IntPair> connected = new ArrayList<>(MAX_CONNECTED);
        connected.addAll(PixConnectUtils.getSingleFourConnected(pix));
        if (isEightConnected)
            connected.addAll(PixConnectUtils.getSingleFourComplementToEight(pix));
        return connected;
    }

    /**
     * Gets the pixels in the hole set that 4-connect to the boundary set
     * @param boundarySet The pixels in the boundary
     * @param holeSet The pixels in the hole
     * @return The pixels in the hole set that 4-connect to the boundary set
     */
    private static HashSet<IntPair> getSmallInnerLayer(HashSet<IntPair> boundarySet,
                                                      HashSet<IntPair> holeSet) {
        return getInnerLayerHelper(boundarySet, holeSet, false);
    }

    /**
     * Gets the pixels in the hole set that 8-connect to the boundary set
     * @param boundarySet The pixels in the boundary
     * @param holeSet The pixels in the hole
     * @return The pixels in the hole set that 8-connect to the boundary set
     */
    private static HashSet<IntPair> getLargeInnerLayer(HashSet<IntPair> boundarySet,
                                                      HashSet<IntPair> holeSet) {
        return getInnerLayerHelper(boundarySet, holeSet, true);
    }

    /**
     * Gets the pixels in the hole set that connect to the boundary set (4-connect or 8-connect depending
     * on the input
     * @param boundarySet The pixels in the boundary
     * @param holeSet The pixels in the hole
     * @param isEightConnected Whether or not to apply 8-connected (the default is 4)
     * @return The pixels in the hole set that connect to the boundary set (4-connect or 8-connect depending
     * on the input
     */
    private static HashSet<IntPair> getInnerLayerHelper(HashSet<IntPair> boundarySet,
                                                        HashSet<IntPair> holeSet, boolean isEightConnected) {
        HashSet<IntPair> innerLayer = new HashSet<>();
        ArrayList<IntPair> curSuspects;
        for (IntPair boundaryPix : boundarySet) {
            curSuspects = getConnectedPixels(boundaryPix, isEightConnected);
            for (IntPair suspectedPix : curSuspects) {
                if (holeSet.contains(suspectedPix)) innerLayer.add(suspectedPix);
            }
        }
        return innerLayer;
    }

    /**
     * Gets the connected pixels in the given boundary, for a given pixel, 4-connected or 8-connected, depends
     * on the input
     * @param holePix The hole pixel to get the connected pixels to it
     * @param boundarySet The pixels in the boundary
     * @param isEightConnected Whether or not to apply 8-connected (the default is 4)
     * @return The list of connected pixels in the boundary to the given hole pixel
     */
   private static HashSet<IntPair> getSingleBoundarySet(IntPair holePix, HashSet<IntPair> boundarySet,
                                                 boolean isEightConnected) {
        HashSet<IntPair> singleBoundarySet = new HashSet<>(MAX_CONNECTED);
        singleBoundarySet.addAll(PixConnectUtils.getSingleFourConnected(holePix));
        if (isEightConnected)
            singleBoundarySet.addAll(PixConnectUtils.getSingleFourComplementToEight(holePix));
        singleBoundarySet.removeIf(suspectedBoundaryPix -> !boundarySet.contains(suspectedBoundaryPix));
        return singleBoundarySet;
    }

    /**
     * Fills the hole pixels by layers: each time taking the current hole pixels that connect to the
     * current boundary and applying the base algo on each hole pixel based only on the boundary pixels that
     * connect to it directly. Next setting the hole layer just filled as the current boundary and going on
     * for the next inner layer. Continuing this way until all the hole pixels are filled.
     * @param grayMat The gray scale matrix to fill
     * @param holeSet The pixel in the hole
     * @param boundarySet The pixels in the initial boundary
     * @param func The weight function to use in the base algo
     * @param isEightConnected Whether or not to use 8-connected (the default is 4)
     */
    public void apply(float[][] grayMat, HashSet<IntPair> holeSet, HashSet<IntPair> boundarySet,
                      WeightFunction func, boolean isEightConnected) {
        HashSet<IntPair> curInnerLayer;
        HashSet<IntPair> curSingleBoundary;
        float cur;
        while (!holeSet.isEmpty()) {
            if (isLargeInnerLayer)
                curInnerLayer = getLargeInnerLayer(boundarySet, holeSet);
            else curInnerLayer = getSmallInnerLayer(boundarySet, holeSet);
            for (IntPair holePix : curInnerLayer) {
                if (!isEntireBoundary) curSingleBoundary = getSingleBoundarySet(holePix, boundarySet,
                         isEightConnected);
                else curSingleBoundary = boundarySet;
                cur = BaseAlgo.getPixFillVal(holePix, grayMat, curSingleBoundary, func);
                grayMat[holePix.getX()][holePix.getY()] = cur;
                if (isAddToBoundaryAsWeGo) {
                    boundarySet.add(holePix);
                }
            }
            boundarySet = curInnerLayer;
            holeSet.removeAll(curInnerLayer);
        }
    }

    /**
     * This is used in the writing of the result generated by this algo. The output string will be
     * part of the name of the output image.
     * @return The string that should be concatenated with the file name of the output image generated by
     * this algo
     */
    @Override
    public String toString() {
        return name + (isLargeInnerLayer ? "Large" : "Small") + "InnerLayer" + (isAddToBoundaryAsWeGo ?
                "Dynamic" : "Static") + (isEntireBoundary ? "EntireBoundary" : "PartialBoundary");
    }
}
