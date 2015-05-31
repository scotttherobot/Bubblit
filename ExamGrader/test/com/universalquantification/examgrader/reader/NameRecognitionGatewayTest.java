/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reader;

import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.core.image.ConvertBufferedImage;
import boofcv.gui.binary.VisualizeBinaryData;
import boofcv.struct.image.ImageFloat32;
import boofcv.struct.image.ImageUInt8;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import junit.framework.TestCase;

/**
 *
 * @author admin
 */
public class NameRecognitionGatewayTest extends TestCase
{

    static final String PATH = "OCRTestImages/";

    public NameRecognitionGatewayTest(String testName)
    {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Test of detectCharacter method, of class NameRecognitionGateway.
     */
    public void testDetectCharacter()
    {

        String fileName = PATH + "A_1.png";
        System.out.println("detectCharacter: " + fileName);
        BufferedImage image = null;

        try
        {

            image = ImageIO.read(new File(fileName));
            image = getBinaryImage(image);
            invertBW(image);

        }
        catch (IOException ex)
        {
            Logger.getLogger(NameRecognitionGatewayTest.class.getName()).
                log(Level.SEVERE, null, ex);
        }

        NameRecognitionGateway instance = new NameRecognitionGateway();

        StochasticCharacter result = instance.detectStochasticCharacter(image);
        assertEquals(result.toString(), "A");
    }

    /**
     * Test of detectCharacter method, of class NameRecognitionGateway.
     */
    public void testDetectCharacter2()
    {

        String fileName = PATH + "B_1.png";
        System.out.println("detectCharacter: " + fileName);
        BufferedImage image = null;

        try
        {
            image = ImageIO.read(new File(fileName));
            image = getBinaryImage(image);
            invertBW(image);

        }
        catch (IOException ex)
        {
            Logger.getLogger(NameRecognitionGatewayTest.class.getName()).
                log(Level.SEVERE, null, ex);
        }

        NameRecognitionGateway instance = new NameRecognitionGateway();

        StochasticCharacter result = instance.detectStochasticCharacter(image);
        assertEquals(result.toString(), "B");
    }

    /**
     * Test of detectCharacter method, of class NameRecognitionGateway.
     */
    public void testDetectCharacter3()
    {

        String fileName = PATH + "C_Scott.png";
        System.out.println("detectCharacter: " + fileName);
        BufferedImage image = null;

        try
        {

            image = ImageIO.read(new File(fileName));
            image = getBinaryImage(image);
            invertBW(image);

        }
        catch (IOException ex)
        {
            Logger.getLogger(NameRecognitionGatewayTest.class.getName()).
                log(Level.SEVERE, null, ex);
        }

        NameRecognitionGateway instance = new NameRecognitionGateway();

        StochasticCharacter result = instance.detectStochasticCharacter(image);
        assertEquals(result.toString(), "C");
    }

    /**
     * Test of detectCharacter method, of class NameRecognitionGateway.
     */
    public void testDetectCharacter4()
    {

        String fileName = PATH + "D_1.png";
        System.out.println("detectCharacter: " + fileName);
        BufferedImage image = null;

        try
        {
            image = ImageIO.read(new File(fileName));
            image = getBinaryImage(image);
            invertBW(image);

        }
        catch (IOException ex)
        {
            Logger.getLogger(NameRecognitionGatewayTest.class.getName()).
                log(Level.SEVERE, null, ex);
        }

        NameRecognitionGateway instance = new NameRecognitionGateway();

        StochasticCharacter result = instance.detectStochasticCharacter(image);
        assertEquals(result.toString(), "D");
    }

    private static BufferedImage getBinaryImage(BufferedImage image)
    {
        // convert into a usable format
        ImageFloat32 input = ConvertBufferedImage.convertFromSingle(image, null,
            ImageFloat32.class);
        ImageUInt8 binary = new ImageUInt8(input.width, input.height);

        // Select a global threshold using Otsu's method.
        double threshold = GThresholdImageOps.computeOtsu(input, 0, 256);
        //double threshold = GThresholdImageOps.computeEntropy(input, 0, 256);
        //double threshold = ImageStatistics.mean(input);

        // Apply the threshold to create a binary image
        ThresholdImageOps.threshold(input, binary, (float) threshold, true);

        // remove small blobs through erosion and dilation
        //ImageUInt8 filtered = BinaryImageOps.erode8(binary, 1, null);
        //filtered = BinaryImageOps.dilate8(filtered, 1, null);
        //GrayImageOps.invert(filtered, 0, null);
        BufferedImage visualFiltered = VisualizeBinaryData.renderBinary(
            binary, null);

        return visualFiltered;
    }

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
}
