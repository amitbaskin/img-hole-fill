package img_hole_fill.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Various utilities for BufferedImage objects
 */
public abstract class BuffImgUtils {
    static final float MAX_COLOR_VAL = 255;
    private static final String DEFAULT_IMG_FORMAT = "png";

    /**
     * Gets a buffered image object from a file name of an image
     * @param fileName The file name of the subject image in the current working directory
     * @return A buffered image object from the file name of the image
     * @throws IOException In case there's a problem reading the provided file
     */
    public static BufferedImage getBufferedImg(String fileName) throws IOException {
        File imgFile = new File(System.getProperty("user.dir"), fileName);
        return ImageIO.read(imgFile);
    }

    /**
     * Converts a matrix to a gray scale image
     * @param grayMat The matrix to convert
     * @return The converted image
     */
    public static BufferedImage getGrayImgFromMat(float[][] grayMat){
        int width = grayMat[0].length;
        int height = grayMat.length;
        int uniformVal;
        BufferedImage grayImg = new BufferedImage(grayMat[0].length, grayMat.length,
                BufferedImage.TYPE_BYTE_GRAY);
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
                grayMat[i][j] = (grayMat[i][j] == -1 ? 0 : grayMat[i][j]);
                uniformVal = (int) (MAX_COLOR_VAL * grayMat[i][j]);
                grayImg.setRGB(i, j, (new Color(uniformVal, uniformVal, uniformVal).getRGB()));
            }
        } return grayImg;
    }

    /**
     * Gets a gray scale version of an image provided by its file name
     * @param fileName The file name of an image present in the current working directory
     * @return The gray scale version of the image provided by its file name
     * @throws IOException In case there's a problem reading the provided file
     */
    public static BufferedImage getGrayImgFromFileName(String fileName) throws IOException {
        float[][] grayMat = MatImgUtils.getGrayMatFromFile(fileName);
        return BuffImgUtils.getGrayImgFromMat(grayMat);
    }

    /**
     * Writes a buffered image to a file
     * @param img The image to write
     * @param fileName The file name of the new file to create
     * @throws IOException In case there's a problem writing the file
     */
    public static void writeImg(BufferedImage img, String fileName) throws IOException {
        File curDir = new File(System.getProperty("user.dir"));
        File grayImgFile = new File(curDir, fileName);
        ImageIO.write(img, DEFAULT_IMG_FORMAT, grayImgFile);
    }
}
