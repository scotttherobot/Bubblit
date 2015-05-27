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
        
        NameRecognitionGateway instance = new NameRecognitionGateway();
        
        char[] result = instance.detectCharacter(image);

        for(int i = 0; i < result.length; i++)
        {
            System.out.print(result[i] + " ");
        }
    }

    /**
     * Test of invertBW method, of class NameRecognitionGateway
     * @return BufferedImage of inverted white/black colors
     */
    public BufferedImage testInvertBW() {
        System.out.println("invertBW on image A_1.png");
        String fileName = PATH + "A_1.png";
        BufferedImage image = null;
        
        try {
            
            image = ImageIO.read(new File(fileName));
            
        }catch (IOException ex) {    
            Logger.getLogger(NameRecognitionGatewayTest.class.getName()).
                    log(Level.SEVERE, null, ex);  
        }
        //make the image binary first
        image = NameRecognitionGateway.getBinaryImage(image);
        NameRecognitionGateway.invertBW(image);
        // TODO review the generated test code and remove the default call to fail.
        return image;
    }

    /**
     * Test of getBinaryImage method, of class NameRecognitionGateway
     * @return BufferedImage that only has black and white pixels
     */
    public BufferedImage testGetBinaryImage() {
        System.out.println("getBinaryImage on image S.png");
        String fileName = PATH + "S.png";
        BufferedImage image = null;
        BufferedImage result = null;
        
        try {
            
            image = ImageIO.read(new File(fileName));
            
        }catch (IOException ex) {    
            Logger.getLogger(NameRecognitionGatewayTest.class.getName()).
                    log(Level.SEVERE, null, ex);  
        }
        
        result = NameRecognitionGateway.getBinaryImage(image);
        
        return result;
    }
}
