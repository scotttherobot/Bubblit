/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.grader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.HashMap;
import junit.framework.TestCase;

/**
 *
 * @author luis
 */
public class RosterTest extends TestCase
{
    
    public RosterTest(String testName)
    {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Test of getRecordByColumnValue method, of class Roster.
     * @throws java.lang.Exception
     */
    public void testGetRecordByColumnValue() throws FileNotFoundException, Exception
    {
        File f = new File("test-input-files/cpe307-01-roster.tsv");
        FileReader r = new FileReader(f);
        Roster rr = new Roster(r);
        HashMap<String, String> m = rr.getRecordByColumnValue("No.", "7");
        HashMap<String, String> e = new HashMap<String,String>();
        //No.	Student Name	Student Username	EMPLID	Major	Class	Units	Status	FERPA	Grade
        e.put("Student Name","\"Cuellar, Luis\"");
        e.put("Student Username","lcuellar");
        e.put("EMPLID","'348083977");
        e.put("Major","CSC");
        e.put("Class","Senior");
        e.put("Units","4");
        e.put("Status","Enrolled");
        e.put("FERPA","No");
        e.put("No.", "7");
        assertEquals(e, m);

    }
    
    public void testGetRecordByNullColumnValue() throws FileNotFoundException, Exception
    {
        File f = new File("test-input-files/cpe307-01-roster.tsv");
        FileReader r = new FileReader(f);
        Roster rr = new Roster(r);
        HashMap<String, String> m = rr.getRecordByColumnValue("No.", "notfound");
 
        assertNull(m);

    }
    
    public void testExceptionWithBadRosterFile()
    {
        StringReader reader = new StringReader("I am not a roster file.");
        boolean exceptionCaught = false;
        try
        {
            new Roster(reader);
        }
        catch (Exception e)
        {
            exceptionCaught = true;
        }
        assertTrue(exceptionCaught);
    }

    /**
     * Test of getNumStudents method, of class Roster.
     */
    public void testGetNumStudents() throws FileNotFoundException, Exception
    {
        File f = new File("test-input-files/cpe307-01-roster.tsv");
        FileReader r = new FileReader(f);
        Roster rr = new Roster(r);
        assertEquals(rr.getNumStudents(), 28);
    }
    
}
