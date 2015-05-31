/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.grader;

import java.io.File;
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
     * Test of parseRoster method, of class RosterParser.
     */
    public void testParseRoster() throws Exception
    {
        File f = new File("test-input-files/cpe307-01-roster.tsv");
        FileReader r;
        r = new FileReader(f);
        Roster roster = new Roster(r);
        List<RosterEntry> result = RosterParser.parseRoster(roster);
        RosterEntry luis = result.get(6);
        assertEquals(luis.getFirst(), "Luis");
        assertEquals(luis.getLast(), "Cuellar");
        assertEquals(luis.getSequenceNumber(), 7);
        assertEquals(luis.getId(), "'348083977");
    }
    
}
