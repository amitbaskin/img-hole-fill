package img_hole_fill.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;

/**
 * Various utilities for handling holes
 */
public abstract class HoleUtils {
    private static final int HOLE_RGB_VAL = 0;

    /**
     * Gets a "holed matrix" from a "complete" one by given hole pixels
     * @param grayMat The "complete" matrix
     * @param holeSet The holes to set in the matrix
     */
    public static void setHolesInMat(float[][] grayMat, HashSet<IntPair> holeSet){
        for (IntPair hole : holeSet){
            grayMat[hole.getX()][hole.getY()] = StrictHoleCondition.HOLE_MAT_VAL;
        }
    }

    /**
     * Merges a subject image with a hole
     * @param img The subject image
     * @param holeSet The hole
     */
    public static void setHolesInImg(BufferedImage img, HashSet<IntPair> holeSet){
        for (IntPair hole : holeSet){
            img.setRGB(hole.getX(), hole.getY(), HOLE_RGB_VAL);
        }
    }

    /**
     * Gets a "holed matrix" from a "complete" one using a hole file
     * @param holeFileName The file name of the hole image
     * @param grayImgMat The matrix to set the holes in
     * @throws IOException In case there's a problem reading the hole file
     */
    public static void setHolesInMatByHoleFile(String holeFileName, float[][] grayImgMat)
            throws IOException {
        HashSet<IntPair> holeSet = getHoleSetFromFile(holeFileName);
        setHolesInMat(grayImgMat, holeSet);
    }

    /**
     * Gets a set of pixels representing a hole from a given file name of a hole image
     * @param holeFileName The file name of the hole image
     * @return The pixels of the hole
     * @throws IOException In case there's a problem reading the hole image
     */
    public static HashSet<IntPair> getHoleSetFromFile(String holeFileName)
            throws IOException {
        float[][] grayHoleMat = MatImgUtils.getGrayMatFromFile(holeFileName);
        return HoleUtils.getHoleSetFromMat(grayHoleMat, ThresholdHoleCondition.getInstance());
    }

    /**
     * Extracts the set of holes from a given matrix representing an image with a hole in it.
     * @param grayMat The matrix representing the image
     * @param condition The condition for classifying a pixel as a hole
     * @return The set of holes
     */
    public static HashSet<IntPair> getHoleSetFromMat(float[][] grayMat, HoleCondition condition) {
        int rowsAmount = grayMat.length;
        int colsAmount = grayMat[0].length;
        HashSet<IntPair> holeSet = new HashSet<>();
        for (int i = 0; i < rowsAmount; i++) {
            for (int j = 0; j < colsAmount; j++) {
                if (condition.isHole(grayMat, i, j)){
                    holeSet.add(new IntPair(i, j));
                }
            }
        } return holeSet;
    }

    /**
     * Gets a gray scale matrix representing a subject image with a hole in it
     * @param imgFileName The file name of the subject image
     * @param holeFileName The file name of the hole image
     * @return The gray scale matrix
     * @throws IOException In case there's a problem reading either one of the given files
     */
    public static float[][] getHoledGrayMatFromFile(String imgFileName, String holeFileName)
            throws IOException{
        float[][] grayImgMat = MatImgUtils.getGrayMatFromFile(imgFileName);
        HoleUtils.setHolesInMatByHoleFile(holeFileName, grayImgMat);
        return grayImgMat;
    }
}
