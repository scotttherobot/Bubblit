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
public class ImagePointOffsetTest extends TestCase {
    
    public ImagePointOffsetTest(String testName) {
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

    public void testToString() throws Exception {
        assertNotNull(new ImagePointOffset(1, 2).toString());
    }
}
