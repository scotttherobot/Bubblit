/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unversalquantification.examgrader.utils;

import com.universalquantification.examgrader.utils.PreferencesManager;
import junit.framework.TestCase;

/**
 *
 * @author jenny
 */
public class PreferencesManagerTest extends TestCase {
    
    public PreferencesManagerTest(String testName) {
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

    public void testGetSet()
    {
        assertNull(PreferencesManager.getInstance().get("foo"));
        PreferencesManager.getInstance().set("foo", "bar");
        assertEquals(PreferencesManager.getInstance().get("foo"), "bar");
    }
    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
}
