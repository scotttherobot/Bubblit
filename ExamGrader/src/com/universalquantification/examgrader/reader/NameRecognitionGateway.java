package com.universalquantification.examgrader.reader;

import java.awt.image.BufferedImage;

/**
 * The NameRecognitionGateway class reads student name information from the exam
 * form
 *
 * @version 2.0
 * @author Santi Pierini
 * @author lcuellar
 */
public class NameRecognitionGateway
{

    private CharacterRecognizer cr; // Create a new character recognizer for OCR

    /**
     * Creates an instance of NameRecognitionGateway with a BufferedImage to
     * perform OCR on.
     *
     */
    public NameRecognitionGateway()
    {
        this.cr = new CharacterRecognizer();
    }

    /**
     * Detect a stochastic character in an image
     * @param image the image to proces
     * @return the stochastic character
     */
    public StochasticCharacter detectStochasticCharacter(BufferedImage image)
    {
        return cr.recognizeStochasticCharacter(image);
    }
}
