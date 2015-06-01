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
public class StochasticStringTest extends TestCase {
    
    public StochasticStringTest(String testName) {
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

    
    public void testToString()
    {
        StochasticString str = new StochasticString();
        StochasticCharacter ch = new StochasticCharacter();
        ch.setProbability('c', 1);
        str.append(ch);
        assertNotNull(str.toString());
    }
}
