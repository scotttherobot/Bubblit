/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.controller;

import com.universalquantification.examgrader.grader.RosterEntry;
import com.universalquantification.examgrader.models.InputFileList;
import java.util.ArrayList;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

/**
 *
 * @author jenny
 */
public class GraderFactoryTest extends TestCase {
    
    public GraderFactoryTest(String testName) {
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
    
    public void testCreateGrader() throws Exception
    {
        assertNotNull(new GraderFactory().buildNewGrader(
                mock(InputFileList.class), new ArrayList<RosterEntry>()));
    }
}
