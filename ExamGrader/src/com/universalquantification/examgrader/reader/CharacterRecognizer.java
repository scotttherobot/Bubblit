package com.universalquantification.examgrader.reader;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.neuroph.contrib.imgrec.ImageRecognitionPlugin;
import org.neuroph.core.NeuralNetwork;

/**
 * Provides functionality for recognizing a character from a handwritten sample.
 * It uses the neuroph OCR library to process an image of a single handwritten
 * letter and return a list of likely characters.
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
        InputStream nnetStream = this.getClass().getResourceAsStream(RESOURCE_PATH + "/neuralNetwork.nnet");
        nnet = NeuralNetwork.load(nnetStream);
    }

    /**
     * Return a list of the top characters from most likely to least likely
     * that were detected in an image of a letter.
     * @param img an image that has been cropped to fit only the letter
     * @return a list of likely characters .
     */
    public char[] recognizeCharacter(BufferedImage img)
    {
        // recognize the character
        ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin) nnet.
                getPlugin(ImageRecognitionPlugin.IMG_REC_PLUGIN_NAME);
        HashMap<String, Double> possibilities = imageRecognition.recognizeImage(img);
        
        // return the top most likely ones.
        return getTopPossibilities(possibilities);
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
         * @param ch the character
         * @param prob its probability of being correct according to OCR
         */
        public CharProbability(char ch, double prob)        
        {
            c = ch;
            probability = prob;
        }

        @Override
        public int compareTo(CharProbability o) {
            return o.probability.compareTo(this.probability);
        }
    }
    
    /**
     * sort the top possibilites from the OCR then return the top ones
     * @param possibilities the map of characters to their probabilities
     * @return a list of top characters sorted from most likely to least.
     */
    private char[] getTopPossibilities(HashMap<String, Double> possibilities)
    {
        char[] top = new char[TOP_CHARS];
        List<String> mapKeys = new ArrayList<String>(possibilities.keySet());
        ArrayList<CharProbability> probs = new ArrayList<CharProbability>();

        for(String key : mapKeys)
        {
            probs.add(new CharProbability(key.charAt(0), possibilities.get(key)));
        }
        
        Collections.sort(probs);
        for(int i = 0; i < TOP_CHARS; i++)
        {
            top[i] = probs.get(i).c;
        }
        
        return top;
    }
}
