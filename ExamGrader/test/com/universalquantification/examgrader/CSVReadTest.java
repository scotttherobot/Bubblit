/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author scottvanderlind
 */
public class CSVReadTest extends TestCase
{
    
    public CSVReadTest(String testName)
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

    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
    
    public void testWrite() {
        CSVWriter writer = new CSVWriter("testout.csv");
        
        String[] cols =
        {
            "id", "col b", "col c", "col d"
        };
        writer.setColumns(cols);

        String[] l1 =
        {
            "123454321", "two", "three", "four"
        };
        writer.addLine(l1);

        String[] l2 =
        {
            "100", "200", "300", "400"
        };
        writer.addLine(l2);

        writer.writeFile();
        
        // Now, test the reader.
        CSVReader reader;
        try
        {
            reader = new CSVReader("testout.csv");
            
            // To get a record by a column value (ie get a record by student id)
            // use getRecordByColumnValue()
            System.out.println("ByColumnValue:");
            System.out.println(reader.getRecordByColumnValue("id", "123454321").toString());
            
            HashMap record = reader.getRecordByColumnValue(cols[0], l1[0]);
            
            // test getRecordByColumnValue
            assertEquals(record.get(cols[0]), l1[0]);
            assertEquals(record.get(cols[1]), l1[1]);
        }
        catch (Exception ex)
        {
            Logger.getLogger(CSVReadTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
