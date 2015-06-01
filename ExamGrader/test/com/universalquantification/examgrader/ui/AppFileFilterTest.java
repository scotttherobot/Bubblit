/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.ui;

import com.universalquantification.examgrader.utils.AppFileFilter;
import java.io.File;
import junit.framework.TestCase;

/**
 *
 * @author jenny
 */
public class AppFileFilterTest extends TestCase {
    
    public AppFileFilterTest(String testName) {
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
    
    public void testAppFilter() throws Exception {
        AppFileFilter filter = new AppFileFilter("aoeu", new String[] {"pdf"});
        assertEquals(filter.getDescription(), "aoeu");
        assertFalse(filter.accept(new File("aoeu")));
        assertFalse(filter.accept(new File("aoeu.txt")));
        assertTrue(filter.accept(new File("aoeu.pdf")));
        
        assertTrue(filter.accept(new File(".")));
        assertFalse(filter.accept(new File("aoeu.")));
        
        assertFalse(filter.accept(new File(".bashrc")));
        assertTrue(filter.accept(new File("..")));
    }
}
