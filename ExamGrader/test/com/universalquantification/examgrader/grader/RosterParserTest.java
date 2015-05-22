/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.grader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author luis
 */
public class RosterParserTest extends TestCase
{

    public RosterParserTest(String testName)
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
     * Test of parseRosterEntry method, of class RosterParser.
     */
    public void testParseRosterEntry()
    {
        /*
         System.out.println("parseRosterEntry");
         String line = "";
         RosterEntry expResult = null;
         RosterEntry result = RosterParser.parseRosterEntry(line);
         assertEquals(expResult, result);
         // TODO review the generated test code and remove the default call to fail.
         fail("The test case is a prototype.");
         */
    }

    /**
     * Test of parseRoster method, of class RosterParser.
     */
    public void testParseRoster() throws FileNotFoundException
    {
        Roster studentReader = null;
        FileReader rosterFile = null;

        //try
        //{
            rosterFile = new FileReader("students.tsv");
            studentReader = new Roster(rosterFile);
        //}
        //catch (Exception ex)
        //{
            //System.out.println("Error loading CSV file" + ex.getMessage());
        //}
        
        List<RosterEntry> result = RosterParser.parseRoster(studentReader);
        for(RosterEntry e : result)
        {
            System.out.println(e.getSequenceNumber() + " " + e.getFirst() + " " + e.getLast());
        }
        
        
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
