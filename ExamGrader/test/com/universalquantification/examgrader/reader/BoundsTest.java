/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.universalquantification.examgrader.reader;

import junit.framework.TestCase;

/**
 *
 * @author jdalbey
 */
public class BoundsTest extends TestCase {
    
    public BoundsTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Test of toString method, of class Bounds.
     */
    public void testToString() {
        System.out.println("Bounds toString");
        Bounds instance = new Bounds(1,2,3,4);
        String expResult = "Bounds: [minX 1]"
            + "[maxX 2]"
            + "[minY 3]"
            + "[maxY 4]";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
