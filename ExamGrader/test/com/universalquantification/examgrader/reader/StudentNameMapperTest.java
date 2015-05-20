/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reader;

import com.universalquantification.examgrader.models.Student;
import java.io.Reader;
import junit.framework.TestCase;

/**
 *
 * @author admin
 */
public class StudentNameMapperTest extends TestCase {
    
    public StudentNameMapperTest(String testName) {
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
     * Test of getName method, of class StudentNameMapper.
     */
    public void testGetName() {
        System.out.println("getName");
        char[][] usrName = null;
        StudentNameMapper instance = null;
        Student expResult = null;
        Student result = instance.getName(usrName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateRoster method, of class StudentNameMapper.
     */
    public void testUpdateRoster() throws Exception {
        System.out.println("updateRoster");
        Reader reader = null;
        StudentNameMapper instance = null;
        instance.updateRoster(reader);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
