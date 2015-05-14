
package com.universalquantification.examgrader.reader;

import boofcv.alg.filter.basic.GrayImageOps;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.core.image.ConvertBufferedImage;
import boofcv.gui.binary.VisualizeBinaryData;
import boofcv.struct.image.ImageFloat32;
import boofcv.struct.image.ImageUInt8;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The NameRecognitionGateway class reads student name 
 * information from the exam form
 * @version 2.0
 * @author Santi Pierini
 * @author lcuellar
 */
public class NameRecognitionGateway {
    
    private CharacterRecognizer cr; // Create a new character recognizer for OCR
    private BufferedImage letters;  // Image to read letters from
    
    /**
     * Creates an instance of NameRecognitionGateway with a BufferedImage
     * to perform OCR on.
     *
     * @param letters - buffered image of writing to do OCR on
     */
    public NameRecognitionGateway(BufferedImage letters)
    {
        this.cr = new CharacterRecognizer();
        this.letters = letters;
    }
    
    /**
     * Does OCR on an image and gets a possible username with individual 
     * character possibilities for each spot included 
     *
     * @param image input image to do OCR on
     * @return the a 2D char array that represents each individual
     * character in the username, with alternate possible choices for each 
     * character based on probability.
     */
    public char[][] detectName(BufferedImage image)
    {
        return null;
    }
    
    /**
     * Get a black-and-white version of an image
     *
     * @param image BufferedImage input to change into black and white
     * @return the resulting black-and-white image
     */
    private static BufferedImage getBinaryImage(BufferedImage image)
    {
        // convert into a usable format
        ImageFloat32 input = ConvertBufferedImage.convertFromSingle(image, null,
                ImageFloat32.class);
        ImageUInt8 binary = new ImageUInt8(input.width, input.height);

        // Select a global threshold using Otsu's method.
        double threshold = GThresholdImageOps.computeOtsu(input, 0, 256);

        // Apply the threshold to create a binary image
        ThresholdImageOps.threshold(input, binary, (float) threshold, true);

        // remove small blobs through erosion and dilation
        ImageUInt8 filtered = BinaryImageOps.erode8(binary, 1, null);
        filtered = BinaryImageOps.dilate8(filtered, 1, null);
        GrayImageOps.invert(filtered, 0, null);

        BufferedImage visualFiltered = VisualizeBinaryData.renderBinary(
                filtered, null);

        return visualFiltered;
    }
    
    /**
     * Invert colors of a black and white image
     * @param img BufferedImage that is to be inverted
     */
    public static void invertBW(BufferedImage img)
    {
        int w = img.getWidth();
        int h = img.getHeight();
        int black = -16777216;
        int white = -1;

        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                if (img.getRGB(x, y) == white)
                {
                    img.setRGB(x, y, black);
                }
                else
                {
                    img.setRGB(x, y, white);
                }
            }
        }
    }
    
    /**
     * Gets the smallest bounding box for a BW image by finding the first/last
     * pixels for X & Y and removes the margins.
     * @param img BufferedImage to get the bounding box of.
     * @return the resulting Bounds for the smallest bounds of the image
     */
    public static Bounds getBounds(BufferedImage img)
    {
        int w = img.getWidth();
        int h = img.getHeight();
        int black = -16777216;
        
        int minx = w;
        int maxx = 0;
        int miny = h;
        int maxy = 0;
        
        for(int x = 0; x < w; x++)
        {
            for(int y = 0; y < h; y++)
            {
                int color = img.getRGB(x, y);
                if(color == black)
                {
                    minx = x < minx ? x : minx;
                    maxx = x > maxx ? x : maxx;
                    
                    miny = y < miny ? y : miny;
                    maxy = y > maxy ? y : maxy;
                }
            }
        }
             
        Bounds bounds = new Bounds(maxx, minx, maxy, miny);
        return bounds;
    }
    
    /**
     * Gets a list of buffered images with the letters cropped to their
     * smallest bounding box.
     * @param img BufferedImage to get letters from.
     * @param numLetters integer representing the number of letters to read
     * @return ArrayList of BufferedImage with letters cropped to 
     * smallest bounding box
     */
    public static ArrayList<BufferedImage> getLetters(BufferedImage img, int numLetters)
    {
        double cellWidth = img.getWidth() / (double)numLetters;
        double cellHeight = img.getHeight();
        double padding = cellWidth * 0.07; // this is to remove the separator
        
        ArrayList<BufferedImage> letters = new ArrayList<BufferedImage>();
        
        for(int onLetter = 0; onLetter < numLetters; onLetter++)
        {
            // remove the padding & crop
            int x0 = (int) (onLetter * cellWidth + padding);
            int x1 = (int) ((onLetter + 1) * cellWidth - padding);
            int y0 = (int) (0 + padding);
            int y1 = (int) (cellHeight - padding);
            
            BufferedImage letter = img.getSubimage(x0, y0, x1 - x0, y1 - y0);
            Bounds letterOnly = getBounds(letter);
            letter = letter.getSubimage(
                    letterOnly.minX,
                    letterOnly.minY,
                    letterOnly.maxX - letterOnly.minX,
                    letterOnly.maxY - letterOnly.minY
            );
            letters.add(letter);
        }
        return letters;
    }
}
