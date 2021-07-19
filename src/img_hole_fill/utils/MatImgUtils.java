package img_hole_fill.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 */
public abstract class MatImgUtils {
//    private static final float RED_FACTOR = (float) 0.299;
//    private static final float GREEN_FACTOR = (float) 0.587;
//    private static final float BLUE_FACTOR = (float) 0.114;
    private static final float RED_FACTOR = (float) 0.3333;
    private static final float GREEN_FACTOR = (float) 0.3333;
    private static final float BLUE_FACTOR = (float) 0.3333;

    /**
     * Gets the RGB matrix (by color objects) of a given buffered image
     * @param img The image
     * @return The matrix
     */
    public static Color[][] getColorMatFromImg(BufferedImage img) {
        Color[][] colorMat = new Color[img.getHeight()][img.getWidth()];
        for (int i=0; i<img.getHeight(); i++){
            for (int j=0; j<img.getWidth(); j++){
                colorMat[i][j] = new Color(img.getRGB(i, j));
            }
        } return colorMat;
    }

    /**
     * Gets a gray scale matrix from a colored one
     * @param colorMat The colored matrix
     * @return The gray scale matrix
     */
    public static float[][] getGrayMatFromColorMat(Color[][] colorMat){
        int rowsAmount = colorMat.length;
        int colsAmount = colorMat[0].length;
        float[][] grayMat = new float[rowsAmount][colsAmount];
        Color curColor;
        for (int i=0; i<rowsAmount; i++){
            for (int j=0; j<colsAmount; j++){
                curColor = colorMat[i][j];
                grayMat[i][j] = (RED_FACTOR * curColor.getRed() +
                        GREEN_FACTOR * curColor.getGreen() +
                        BLUE_FACTOR * curColor.getBlue()) / BuffImgUtils.MAX_COLOR_VAL;
            }
        } return grayMat;
    }

    /**
     * Gets a gray scale matrix from an image given by its file name
     * @param fileName The file name of the image
     * @return The gray scale matrix
     * @throws IOException In case there's a problem reading the image file
     */
    public static float[][] getGrayMatFromFile(String fileName) throws IOException {
        BufferedImage img = BuffImgUtils.getBufferedImg(fileName);
        Color[][] colorMat = getColorMatFromImg(img);
        return getGrayMatFromColorMat(colorMat);
    }
}
