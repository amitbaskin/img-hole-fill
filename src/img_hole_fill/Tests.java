package img_hole_fill;

import img_hole_fill.algos.*;
import img_hole_fill.utils.BuffImgUtils;
import img_hole_fill.utils.HoleUtils;
import img_hole_fill.utils.IntPair;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Tests for reviewing various results
 */
public abstract class Tests {
    private static final String DEFAULT_GRAY_FILE_NAME_PREFIX = "Gray";
    private static final String HOLED_PREFIX_BY_HOLE_FILE_NAME = "HoledByHoleFileName";
    private static final String HOLED_PREFIX_BY_HOLED_MAT = "HoledByHoledMat";

    /**
     * Prints the percentage of pixels in the given matrix that are not 0
     * @param algoName The name of the algorithm that its accuracy is measured
     * @param imgMat The matrix after the algorithm modified it
     * @param holeSet The pixels in the hole
     * @param boundarySet The pixels in the boundary
     * @param func The weight function used by the algorithm
     * @param isEightConnected Whether or not to apply the 8-connected approach (the default is the
     *                         4-connected approach)
     */
    static void printAccuracy(String algoName, float[][] imgMat,
                              float[][] copyImgMat, HashSet<IntPair> holeSet,
                              HashSet<IntPair> boundarySet, WeightFunction func,
                              boolean isEightConnected){
        System.out.println(algoName);
        BaseAlgo.getInstance().apply(copyImgMat, holeSet, boundarySet, func, isEightConnected);
        float base;
        float other;
        float tmp;
        for (IntPair pix : holeSet){
            other = imgMat[pix.getX()][pix.getY()];
            base = copyImgMat[pix.getX()][pix.getY()];
            tmp = Math.abs(base - other);
            copyImgMat[pix.getX()][pix.getY()] -= imgMat[pix.getX()][pix.getY()];
            tmp = copyImgMat[pix.getX()][pix.getY()];
        }
        int counter = 0;
        float diff = 0;
        float cur;
        float tmpErr;
        for (IntPair pix : holeSet){
            cur = copyImgMat[pix.getX()][pix.getY()];
            if (cur > 0.00001) {
                counter++;
                tmpErr = Math.abs(cur);
                diff += Math.abs(tmpErr);
            }
        }
        float accuracy = 100 * (holeSet.size() - counter) / (float) holeSet.size();
        float error = 100 * diff / (float) holeSet.size();
        System.out.printf("Accuracy: %f\n", accuracy);
        System.out.printf("Error: %f\n\n", error);
    }

    /**
     * Converts an image to grayscale and writs it to the current working directory
     * @param fileName The file name of the image
     * @throws IOException In case there's a problem reading the given image or writing the result
     */
    public static void writeGrayImgTest(String fileName) throws IOException {
        BufferedImage grayImg = BuffImgUtils.getGrayImgFromFileName(fileName);
        BuffImgUtils.writeImg(grayImg, DEFAULT_GRAY_FILE_NAME_PREFIX + fileName);
    }

    /**
     * Merging the subject image and the hole image and writing the result to the current working directory
     * @param imgFileName The file name of the image
     * @param holeFileName The file name of the hole
     * @throws IOException In case there's a problem reading an image or writing the result
     */
    public static void writeHoledImgByHoleFileTest(String imgFileName, String holeFileName) throws IOException {
        BufferedImage grayImg = BuffImgUtils.getGrayImgFromFileName(imgFileName);
        HashSet<IntPair> holeSet = HoleUtils.getHoleSetFromFile(holeFileName);
        HoleUtils.setHolesInImg(grayImg, holeSet);
        BuffImgUtils.writeImg(grayImg, HOLED_PREFIX_BY_HOLE_FILE_NAME + imgFileName);
    }

    /**
     * First getting the matrix representing the subject image with the hole in it and then writing it to the
     * current working directory
     * @param imgFileName The file name of the image
     * @param holeFileName The file name of the hole
     * @throws IOException In case there's a problem reading an image or writing the result
     */
    public static void writeHoledImgByHoledMatTest(String imgFileName, String holeFileName)
            throws IOException {
        float[][] grayMat = HoleUtils.getHoledGrayMatFromFile(imgFileName, holeFileName);
        BufferedImage grayImg = BuffImgUtils.getGrayImgFromMat(grayMat);
        BuffImgUtils.writeImg(grayImg, HOLED_PREFIX_BY_HOLED_MAT + imgFileName);
    }

    /**
     * First getting the matrix representing the subject image with the hole in it. Next fixing it using the
     * filling algorithm and eventually writing the fixed image to the current working directory
     * @param imgFileName The file name of the image
     * @param holeFileName The file name of the hole
     * @param isEightConnected Whether or not to use the 8-connected approach (the default is 4-connected)
     * @param algo The algorithm to use in order to fill the hole
     * @throws IOException In case there's a problem reading an image or writing the result
     */
    public static void fillByHoledMatTest(String imgFileName, String holeFileName, boolean isEightConnected,
                                          FillHoleAlgo algo) throws IOException {
        float[][] grayImgMat = HoleUtils.getHoledGrayMatFromFile(imgFileName, holeFileName);
        Runner.fillByHoledMat(imgFileName, grayImgMat, DefaultWeight.getInstance(), isEightConnected,
                algo);
    }


    /**
     * Fill the hole using the Line Space algorithm but this time use rotation, i.e. for each hole pixel,
     * choose different log(n) pixels from the boundary, each time rotate the head pixel by one from the
     * previous head pixel
     * @param imgFileName The file name of the image
     * @param holeFileName The file name of the hole
     * @throws IOException In case there's a problem reading an image or writing the result
     */
    public static void runBoundaryLineSpaceAlgoWithRotation(String imgFileName, String holeFileName) throws IOException {
        Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(),
                true, new BoundaryLineSpaceAlgo(true));
        Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(),
                false, new BoundaryLineSpaceAlgo(true));
    }

    /**
     * Running the HoleRand algorithm with both connected approaches (8 and 4)
     * @param imgFileName The file name of the image
     * @param holeFileName The file name of the hole
     * @throws IOException In case there's a problem reading an image or writing the result
     */
    public static void runRandHoleAlgo(String imgFileName, String holeFileName) throws IOException {
        Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(),
                true, RandHoleAlgo.getRandHoleAlgoInstance());
        Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(),
                false, RandHoleAlgo.getRandHoleAlgoInstance());
    }

    /**
     * Running the BoundaryRand algorithm with both connected approaches (8 and 4)
     * @param imgFileName The file name of the image
     * @param holeFileName The file name of the hole
     * @throws IOException In case there's a problem reading an image or writing the result
     */

    public static void runRandBoundaryAlgo(String imgFileName, String holeFileName) throws IOException {
        Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(),
                true, RandBoundaryAlgo.getRandBoundaryAlgoInstance());
        Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(),
                false, RandBoundaryAlgo.getRandBoundaryAlgoInstance());
    }

    /**
     * Run various tests
     * @param imgFileName The file name of the image
     * @param holeFileName The file name of the hole
     * @throws IOException In case there's a problem reading an image or writing the result
     */
    public static void runVarious(String imgFileName, String holeFileName) throws IOException {

        // Subject image to grayscale
        writeGrayImgTest(imgFileName);
        // Hole image to grayscale
        writeGrayImgTest(holeFileName);
        // Holed subject image directly from hole image
        writeHoledImgByHoleFileTest(imgFileName, holeFileName);
        // Holed subject image from holed matrix
        writeHoledImgByHoledMatTest(imgFileName, holeFileName);

        // More Algorithms
        runBoundaryLineSpaceAlgoWithRotation(imgFileName, holeFileName);
        runRandHoleAlgo(imgFileName, holeFileName);
        runRandBoundaryAlgo(imgFileName, holeFileName);

        // Approx versions
        ArrayList<FillHoleAlgo> algoLst = new ArrayList<>(8);
        for (boolean bool1 : new boolean[]{true, false}) {
            for (boolean bool2 : new boolean[]{true, false}) {
                for (boolean bool3 : new boolean[]{true, false}) {
                    algoLst.add(new ApproxAlgo(bool1, bool2, bool3));
                }
            }
        }
        for (FillHoleAlgo algo : algoLst){
            for (boolean isEightConnected: new boolean[]{true, false}){
                // Fixed image from holed matrix
                Tests.fillByHoledMatTest(imgFileName, holeFileName, isEightConnected, algo);
                // Fixed image directly from hole image
                Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(),
                        isEightConnected, algo);
            }
        }

//        Tests.fillByHoledMatTest(imgFileName, holeFileName, true, new ApproxAlgo(true, true, true));
//        Runner.fillByHoleFile(imgFileName, holeFileName, DefaultWeight.getInstance(),
//                true, new ApproxAlgo(true, true, true));
    }
}
