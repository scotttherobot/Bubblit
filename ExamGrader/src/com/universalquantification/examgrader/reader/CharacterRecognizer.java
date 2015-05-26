package com.universalquantification.examgrader.reader;

import boofcv.gui.image.ShowImages;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
    private static final String RESOURCE_PATH = "resources";
    private NeuralNetwork nnet;

    // number of top characters to return
    private static final int TOP_CHARS = 15;

    /**
     * Construct a recognizer.
     */
    public CharacterRecognizer()
    {
        // initiate the OCR lib
        InputStream nnetStream = this.getClass().getResourceAsStream(
                RESOURCE_PATH + "/neuralNetwork.nnet");
        nnet = NeuralNetwork.load(nnetStream);
    }

    /**
     * Return a list of the top characters from most likely to least likely that
     * were detected in an image of a letter.
     *
     * @param img an image that has been cropped to fit only the letter
     * @return a list of likely characters .
     */
    public char[] recognizeCharacter(BufferedImage img)
    {
        // recognize the character
        // SET character possibilities list TO NeurophOCR.recognizeImage(img)
        ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin) nnet.
                getPlugin(ImageRecognitionPlugin.IMG_REC_PLUGIN_NAME);
        HashMap<String, Double> possibilities = imageRecognition.recognizeImage(
                img);

        // return the top most likely ones.
        return getTopPossibilities(possibilities);
    }

    /**
     * Attempt recognize a character from an image.
     *
     * @param img
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
        if(1.0 * (letterOnly.maxY - letterOnly.minY)/img.getHeight() < .4)
        {
            return null;
        }
        double scale = determineImageScale(img.getWidth(), img.getHeight(), 40,
                40);
        img = resize(img, 40, 40);
        //System.out.println("recognizing this:");
        //ShowImages.showDialog(img);
        ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin) nnet.
                getPlugin(ImageRecognitionPlugin.IMG_REC_PLUGIN_NAME);
        HashMap<String, Double> possibilities = imageRecognition.recognizeImage(
                img);
        StochasticCharacter character = new StochasticCharacter();

        for (String key : possibilities.keySet())
        {
            //System.out.println(key.charAt(0) + " ==> " +  possibilities.get(key));
            character.setProbability(key.charAt(0), possibilities.get(key));
        }

        return character;
    }

    /**
     * Internal class to keep track of characters and their probability.
     */
    private class CharProbability implements Comparable<CharProbability>
    {
        char c;
        Double probability;

        /**
         * Construct a character probability object.
         *
         * @param ch the character
         * @param prob its probability of being correct according to OCR
         */
        public CharProbability(char ch, double prob)
        {
            c = ch;
            probability = prob;
        }

        @Override
        public int compareTo(CharProbability o)
        {
            return o.probability.compareTo(this.probability);
        }
    }

    /**
     * sort the top possibilites from the OCR then return the top ones
     *
     * @param possibilities the map of characters to their probabilities
     * @return a list of top characters sorted from most likely to least.
     */
    private char[] getTopPossibilities(HashMap<String, Double> possibilities)
    {
        // SET top chars to list of size TOP CHARS
        char[] top = new char[TOP_CHARS];
        List<String> mapKeys = new ArrayList<String>(possibilities.keySet());

        // SET probs to list of CharProbability
        ArrayList<CharProbability> probs = new ArrayList<CharProbability>();

        // FOR EACH character possibility from the Neuroph results
        for (String key : mapKeys)
        {
            // ADD a new CharProbability to probs with character and probability
            probs.
                    add(new CharProbability(key.charAt(0), possibilities.
                                    get(key)));
        }
        // ENDFOR

        // SORT the probs
        Collections.sort(probs);

        // SET top to the most likely characters
        for (int i = 0; i < TOP_CHARS; i++)
        {
            top[i] = probs.get(i).c;
        }

        return top;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH)
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
     * @param img
     * @return the bounds for the smallest bounding box.
     */
    public static Bounds getBounds(BufferedImage img)
    {
        int w = img.getWidth();
        int h = img.getHeight();
        int black = -16777216;
        int white = -1;

        int minx = w;
        int maxx = 0;
        int miny = h;
        int maxy = 0;

        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                int color = img.getRGB(x, y);
                if (color == black)
                {
                    minx = x < minx ? x : minx;
                    maxx = x > maxx ? x : maxx;

                    miny = y < miny ? y : miny;
                    maxy = y > maxy ? y : maxy;
                }
                else if (color != white)
                {
                    System.out.println("WHAT THE SHIT");
                }
            }
        }

        Bounds bounds = new Bounds(minx, maxx, miny, maxy);
        return bounds;
    }
}
