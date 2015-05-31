/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.grader;

import junit.framework.TestCase;

/**
 *
 * @author jenny
 */
public class GradingExceptionTest extends TestCase {
    
    public GradingExceptionTest(String testName) {
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

    public void testGradingException() {
        assertNotNull(new GradingException());
    }
}
