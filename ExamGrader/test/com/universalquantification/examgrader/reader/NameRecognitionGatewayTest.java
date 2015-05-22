/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reader;

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
public class NameRecognitionGatewayTest extends TestCase {
    
    static final String PATH = "OCRTestImages/";
    
    public NameRecognitionGatewayTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of detectCharacter method, of class NameRecognitionGateway.
     */
    public void testDetectCharacter() {
        
        
        String fileName = PATH + "A_1.png";
        System.out.println("detectCharacter: " + fileName);
        BufferedImage image = null;
        
        try {
            
            image = ImageIO.read(new File(fileName));
            
        }catch (IOException ex) {    
            Logger.getLogger(NameRecognitionGatewayTest.class.getName()).
                    log(Level.SEVERE, null, ex);  
        }
        
        NameRecognitionGateway instance = new NameRecognitionGateway(image);
        // char[] expResult = null;
        char[] result = instance.detectCharacter(image);
        // assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
        for(int i = 0; i < result.length; i++)
        {
            System.out.print(result[i] + " ");
        }
        
        // fail("The test case is a prototype.");
    }

    /**
     * Test of invertBW method, of class NameRecognitionGateway.
     */
//    public void testInvertBW() {
//        System.out.println("invertBW");
//        BufferedImage img = null;
//        NameRecognitionGateway.invertBW(img);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getBounds method, of class NameRecognitionGateway.
     */
//    public void testGetBounds() {
//        System.out.println("getBounds");
//        BufferedImage img = null;
//        Bounds expResult = null;
//        Bounds result = NameRecognitionGateway.getBounds(img);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getLetters method, of class NameRecognitionGateway.
//     */
//    public void testGetLetters() {
//        System.out.println("getLetters");
//        BufferedImage img = null;
//        int numLetters = 0;
//        ArrayList<BufferedImage> expResult = null;
//        ArrayList<BufferedImage> result = NameRecognitionGateway.getLetters(img, numLetters);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
