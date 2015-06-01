/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reader;

import junit.framework.TestCase;

/**
 *
 * @author jenny
 */
public class ImagePointTest extends TestCase {
    
    public ImagePointTest(String testName) {
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
    
    public void testImagePoint() throws Exception {
        ImagePoint ip = new ImagePoint();
        ip.setpX(12);
        ip.setpY(24);
        
        assertEquals("point [x: 12,y: 24] ", ip.toString());
    }

    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
}
