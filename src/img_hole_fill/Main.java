package img_hole_fill;

import java.io.IOException;

/**
 * The running class
 */
public class Main {

    /**
     * Running the program
     * @param args The arguments
     * @throws IOException In case there's a problem reading either one of the files provided by file names
     * in the arguments, or if there's a problem writing the result
     */
    public static void main(String[] args) throws IOException {
        String subjectImgFileName;
        String maskFileName;
        try{
            subjectImgFileName = args[0];
            maskFileName = args[1];
        boolean isEightConnected = args.length > 2;
        Runner.mainSection(subjectImgFileName, maskFileName, isEightConnected);

        // Run the following lines for further results

        Runner.runBaseAlgo(subjectImgFileName, maskFileName);
        Runner.runApproxAlgo(subjectImgFileName, maskFileName);
        Runner.runBoundaryLineSpaceAlgo(subjectImgFileName, maskFileName);
        Runner.runHoleLineSpaceAlgo(subjectImgFileName, maskFileName);

        Tests.runBoundaryLineSpaceAlgoWithRotation(subjectImgFileName, maskFileName);
        Tests.runRandHoleAlgo(subjectImgFileName, maskFileName);
        Tests.runRandBoundaryAlgo(subjectImgFileName, maskFileName);
        Tests.runVarious(subjectImgFileName, maskFileName);

        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("""
                    Usage:
                    Must provide at least two arguments:
                    The first one for the subject image file name and the second for the hole file name.
                    Both images must be in the current working directory and of the following formats:
                    GIF, PNG, JPEG, BMP or WBMP.
                    The two images should be of the same dimensions, otherwise an
                    ArrayIndexOutOfBoundsException may occur.
                    The default is 4-connectivity, however you may provide a third argument requesting
                    8-connectivity.
                    A third argument will count as 8-connectivity regardless of its content.
                    For further results, uncomment the commented lines in the main method.
                    """);
        }
    }
}
