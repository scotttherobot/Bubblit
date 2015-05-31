package com.universalquantification.examgrader.reader;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import org.neuroph.contrib.imgrec.ImageRecognitionPlugin;
import org.neuroph.core.NeuralNetwork;
import java.awt.image.RasterFormatException;

/**
 * Provides functionality for recognizing a character from a handwritten sample.
 * It uses the neuroph OCR library to process an image of a single handwritten
 * letter and return a list of likely characters.
 *
 * @author Luis Cuellar
 * @version 2.0
 */
public class CharacterRecognizer
{
    // neural network stuff
    private static final String kResourcePath = "resources";
    private NeuralNetwork nnet;

    // number of top characters to return
    private static final int kTopChars = 15;

    /**
     * Construct a recognizer.
     */
    public CharacterRecognizer()
    {
        // initiate the OCR lib
        InputStream nnetStream = this.getClass().getResourceAsStream(
            kResourcePath + "/neuralNetwork.nnet");
        nnet = NeuralNetwork.load(nnetStream);
    }

    /**
     * Attempt recognize a character from an image.
     *
     * @param img the image to recognize
     * @return a StochasticCharacter with probabilities for each char or null if
     * nothing was found.
     */
    public StochasticCharacter recognizeStochasticCharacter(BufferedImage img)
    {
        // recognize the character
        // SET character possibilities list TO NeurophOCR.recognizeImage(img)
        //ShowImages.showDialog(img);
        Bounds letterOnly = getBounds(img);

        // if the bounding box is too small, its an empty box.
        try
        {
            img = img.getSubimage(
                letterOnly.minX,
                letterOnly.minY,
                letterOnly.maxX - letterOnly.minX,
                letterOnly.maxY - letterOnly.minY
            );
        }
        catch (RasterFormatException ex)
        {
            return null;
        }
        //check to see that we don't have an image thats less that half the size.
        if (1.0 * (letterOnly.maxY - letterOnly.minY) / img.getHeight() < .4)
        {
            return null;
        }
        double scale = determineImageScale(img.getWidth(), img.getHeight(), 40,
            40);
        img = resize(img, 40, 40);
        //System.out.println("recognizing this:");
        //ShowImages.showDialog(img);
        ImageRecognitionPlugin imageRecognition = 
            (ImageRecognitionPlugin) nnet.getPlugin(
                ImageRecognitionPlugin.IMG_REC_PLUGIN_NAME);
        HashMap<String, Double> possibilities = imageRecognition.recognizeImage(
            img);
        StochasticCharacter character = new StochasticCharacter();

        // Add the possible characters and their probabilities
        for (String key : possibilities.keySet())
        {
            //System.out.println(key.charAt(0) + " ==> " +  possibilities.get(key));
            character.setProbability(key.charAt(0), possibilities.get(key));
        }

        return character;
    }

    /**
     * resize a buffered image
     * @param img the image to resize
     * @param newW the new width
     * @param newH the new height
     * @return the resized buffered image
     */
    private static BufferedImage resize(BufferedImage img, int newW, int newH)
    {

        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH,
            BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    private static double determineImageScale(int sourceWidth, int sourceHeight,
        int targetWidth, int targetHeight)
    {

        double scalex = (double) targetWidth / sourceWidth;
        double scaley = (double) targetHeight / sourceHeight;
        return Math.min(scalex, scaley);
    }

    /**
     * get the smallest bounding box for a BW image by finding the first/last
     * pixels for X & Y. It basically removes margins.
     *
     * @param img the image to process
     * @return the bounds for the smallest bounding box.
     */
    public static Bounds getBounds(BufferedImage img)
    {
        int width = img.getWidth();
        int height = img.getHeight();
        int black = -16777216;

        int minx = width;
        int maxx = 0;
        int miny = height;
        int maxy = 0;

        // got through every x coordinate
        for (int onX = 0; onX < width; onX++)
        {
            // go through every y coordinate
            for (int onY = 0; onY < height; onY++)
            {
                int color = img.getRGB(onX, onY);
                
                // if the color is black, take note of it.
                if (color == black)
                {
                    // set the minimum x coordinate
                    if(onX < minx) 
                    {
                        minx = onX;
                    }
                    // ste the maximum x coordinate
                    if(onX > maxx)
                    {
                        maxx = onX;
                    }
                    // set the minimum y coordinate
                    if(onY < miny)
                    {
                        miny = onY;
                    }
                    // set the maximum y coordinate
                    if(onY > maxy)
                    {
                        maxy = onY;
                    }
                }
            }
        }

        Bounds bounds = new Bounds(minx, maxx, miny, maxy);
        return bounds;
    }
}
