/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.grader;

import com.universalquantification.examgrader.models.Exam;
import junit.framework.TestCase;

/**
 *
 * @author luis
 */
public class RosterEntryTest extends TestCase
{
    
    public RosterEntryTest(String testName)
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
     * Test of getSequenceNumber method, of class RosterEntry.
     */
    public void testGetSequenceNumber()
    {
        System.out.println("getSequenceNumber");
        RosterEntry instance = new RosterEntry(1, "First", "Last", "1234");
        int expResult = 1;
        int result = instance.getSequenceNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFirst method, of class RosterEntry.
     */
    public void testGetFirst()
    {
        System.out.println("getFirst");
        RosterEntry instance = new RosterEntry(1, "First", "Last", "1234");
        String expResult = "First";
        String result = instance.getFirst();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLast method, of class RosterEntry.
     */
    public void testGetLast()
    {
        System.out.println("getLast");
        RosterEntry instance = new RosterEntry(1, "First", "Last", "1234");
        String expResult = "Last";
        String result = instance.getLast();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class RosterEntry.
     */
    public void testGetId()
    {
        System.out.println("getId");
        RosterEntry instance = new RosterEntry(1, "First", "Last", "1234");
        String expResult = "1234";
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class RosterEntry.
     */
    public void testToString()
    {
        System.out.println("toString");
        RosterEntry instance = new RosterEntry(1, "First", "Last", "1234");
        String expResult = "RosterEntry [seqno=" + instance.getSequenceNumber() + ", first=" + instance.getFirst()
            + ", last=" + instance.getLast() + "]";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
