/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.models;

import java.io.File;
import java.io.IOException;
import junit.framework.TestCase;

/**
 *
 * @author jenny
 */
public class InputFileTest extends TestCase {
    
    private InputFile file;
    
    protected void setUp() throws IOException
    {
        file = new InputFile(new File("test-input-files/Carole.pdf"));   
    }
    
    public void testHashCode()
    {
        assertNotNull(file.hashCode());
    }
    
    public void testEquals()
    {
        assertFalse(file.equals(null));
        assertFalse(file.equals(new Object()));
        assertTrue(file.equals(file));
    }
    
    public void testToString()
    {
        assertNotNull(file.toString());
    }
    
    public void testMetaImage()
    {
        assertNotNull(file.getAnswerKeyPage().getMetaBufferedImage());
    }
    
}
