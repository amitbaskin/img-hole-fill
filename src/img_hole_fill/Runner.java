package img_hole_fill;

import img_hole_fill.algos.*;
import img_hole_fill.utils.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;

/**
 * The solutions for filling a hole in a given image
 */
public abstract class Runner {
    private static final String HOLED_MAT_PREFIX = "ByHoledMat";
    private static final String HOLE_FILE_PREFIX = "ByHoleFile";
    private static final String EIGHT_CONNECTED_PREFIX = "EightConnected";
    private static final String FOUR_CONNECTED_PREFIX = "FourConnected";
    
    /**
     * Fixing an image given that the matrix representing it already contains the hole, and writing it to the
     * current working directory.
     * @param imgFileName The file name of the image
     * @param imgMat The matrix of pixels representing the image
     * @param func The weighting function with which to calculate the values to fill the hole with
     * @param isEightConnected Whether or not to apply the 8-connected approach (the default is the 
     * 4-connected approach)
     * @param algo The filling algorithm to use in order to fill the hole
     * @throws IOException In case there's a problem writing the output image to a file
     */
    public static void fillByHoledMat(String imgFileName, float[][] imgMat, WeightFunction func,
                                      boolean isEightConnected, FillHoleAlgo algo) throws IOException {
        HashSet<IntPair> holeSet = HoleUtils.getHoleSetFromMat(imgMat, StrictHoleCondition.getInstance());
        fillImg(imgFileName, imgMat, holeSet, func, HOLED_MAT_PREFIX + algo.toString() + func.toString(),
                 isEightConnected, algo);
    }
    
    /**
     * Fixing the original image given the hole in a separate file, and writing the solution to the current 
     * working directory
     * @param imgFileName The file name of the image
     * @param holeFileName The file name of the hole file
     * @param func The weighting function with which to calculate the values to fill the hole with
     * @param isEightConnected Whether or not to apply the 8-connected approach (the default is the 
     * 4-connected approach)
     * @param algo The filling algorithm to use in order to fill the hole
     * @throws IOException In case there's a problem reading an image or writing the result
     */
    public static void fillByHoleFile(String imgFileName, String holeFileName,
                                      WeightFunction func, boolean isEightConnected,
                                      FillHoleAlgo algo)
            throws IOException {
        float[][] grayImgMat = MatImgUtils.getGrayMatFromFile(imgFileName);
        HashSet<IntPair> holeSet = HoleUtils.getHoleSetFromFile(holeFileName);
        fillImg(imgFileName, grayImgMat, holeSet, func,
                 HOLE_FILE_PREFIX + algo.toString() + func.toString(), isEightConnected, algo);
    }
    
    /**
     * Fixing an image given its matrix and the set of holes, and writing the solution to the current 
     * working directory.
     * @param imgFileName The file name of the image
     * @param imgMat The matrix of pixels representing the image with the hole
     * @param holeSet The pixels in the hole
     * @param func The weighting function with which to calculate the values to fill the hole with
     * @param fileNamePrefix The prefix to add to the file name of the result
     * @param isEightConnected Whether or not to apply the 8-connected approach (the default is the 
     * 4-connected approach)
     * @param algo The filling algorithm to use in order to fill the hole
     * @throws IOException In case there's a problem writing the output image to a file
     */
    public static void fillImg(String imgFileName, float[][] imgMat, HashSet<IntPair> holeSet,
                               WeightFunction func, String fileNamePrefix, boolean isEightConnected,
                               FillHoleAlgo algo)
            throws IOException {
        HashSet<IntPair> boundarySet = BoundaryUtils.getBoundarySet(holeSet, isEightConnected);

        // The following hash-tagged lines are for printing the accuracy of the algorithm against the base
        // algorithm

        // ##################################################################################################
        // ##################################################################################################
        float[][] imgMatCopy = GeneralUtils.copyArr(imgMat);
        HashSet<IntPair> holeSetCopy = new HashSet<>(holeSet);
        HashSet<IntPair> boundarySetCopy = new HashSet<>(boundarySet);
        // ##################################################################################################
        // ##################################################################################################


        algo.apply(imgMat, holeSet, boundarySet, func, isEightConnected);
        String algoName = (isEightConnected ?
                EIGHT_CONNECTED_PREFIX : FOUR_CONNECTED_PREFIX) + fileNamePrefix + imgFileName;


        // ##################################################################################################
        // ##################################################################################################
        Tests.printAccuracy(algoName, imgMat, imgMatCopy, holeSetCopy, boundarySetCopy, func, isEightConnected);
        // ##################################################################################################
        // ##################################################################################################

        BufferedImage grayImg = BuffImgUtils.getGrayImgFromMat(imgMat);
        BuffImgUtils.writeImg(grayImg, algoName);
    }

    /**
     * Running the base algorithm with the input connected approach
     * @param imgFileName The file name of the image
     * @param holeFileName The file name of the hole
     * @param isEightConnected Whether or not to apply the 8-connected approach (the default is the
     *                         4-connected approach)
     * @throws IOException In case there's a problem reading an image or writing the result
     */
    public static void mainSection(String imgFileName, String holeFileName,
                                   boolean isEightConnected) throws IOException {
        Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(), isEightConnected,
                BaseAlgo.getInstance());
    }

    /**
     * Running the base algorithm with both connected approaches (8 and 4)
     * @param imgFileName The file name of the image
     * @param holeFileName The file name of the hole
     * @throws IOException In case there's a problem reading an image or writing the result
     */
    public static void runBaseAlgo(String imgFileName, String holeFileName) throws IOException {
        Runner.mainSection(imgFileName, holeFileName, true);
        Runner.mainSection(imgFileName, holeFileName, false);
    }

    /**
     * Running the Approximation algorithm with both connected approaches (8 and 4)
     * @param imgFileName The file name of the image
     * @param holeFileName The file name of the hole
     * @throws IOException In case there's a problem reading an image or writing the result
     */
    public static void runApproxAlgo(String imgFileName, String holeFileName) throws IOException {
        Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(),
                true, new ApproxAlgo(true, false, true));
        Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(),
                false, new ApproxAlgo(true, false, true));
    }

    /**
     * Running the Boundary Line Space algorithm with both connected approaches (8 and 4)
     * @param imgFileName The file name of the image
     * @param holeFileName The file name of the hole
     * @throws IOException In case there's a problem reading an image or writing the result
     */
    public static void runBoundaryLineSpaceAlgo(String imgFileName, String holeFileName) throws IOException {
        Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(),
                true, new BoundaryLineSpaceAlgo(false));
        Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(),
                false, new BoundaryLineSpaceAlgo(false));
    }

    /**
     * Running the Hole Line Space algorithm with both connected approaches (8 and 4)
     * @param imgFileName The file name of the image
     * @param holeFileName The file name of the hole
     * @throws IOException In case there's a problem reading an image or writing the result
     */
    public static void runHoleLineSpaceAlgo(String imgFileName, String holeFileName) throws IOException {
        Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(),
                true, HolesLineSpaceAlgo.getHolesLineSpaceAlgoInstance());
        Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(),
                false, HolesLineSpaceAlgo.getHolesLineSpaceAlgoInstance());
    }
}
