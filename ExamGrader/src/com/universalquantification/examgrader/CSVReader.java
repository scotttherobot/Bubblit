/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author scottvanderlind
 */
public class CSVReader 
{
 
    private String filename;    // The filename to write
    private String delimiter;   // The column delimiter
    private String linereturn;  // The line return character

    // Our rows
    private List<HashMap> rows = new ArrayList<HashMap>();
    
    public CSVReader(String filename) throws Exception 
    {
        this(filename, ",");
    }
    
    public CSVReader(String filename, String delimiter) throws Exception
    {
        this(filename, delimiter, "\n");
    }
    
    public CSVReader(String filename, String delimiter, String linereturn) throws Exception
    {
        this.filename = filename;
        this.delimiter = delimiter;
        this.linereturn = linereturn;
        parseFile();
    }
    
    private void parseFile() throws Exception 
    {
        // Open the file
        FileReader infile = null;
        BufferedReader in = null;
        try
        {
            infile = new FileReader(this.filename);
            in = new BufferedReader(infile);
            
            // Read the column names
            String headers = in.readLine();
            String[] columns = explode(headers);
            
            
            // Read the rows. Create a hashmap for each row. Insert it into the rows hashmap.
            String row = null;
            while ((row = in.readLine()) != null) {
                // We have a row. Explode it.
                String[] values = explode(row);
                // Map the columns to the values if they're 1:1
                if (values.length == columns.length)
                {
                    HashMap<String, String> record = new HashMap<String, String>();
                    for (int i = 0; i < values.length; i++)
                    {
                        record.put(columns[i], values[i]);
                    }
                    // Add to master hashmap
                    this.rows.add(record);
                }
                else
                {
                    // Error condition. There were not an equal number of keys+values
                    throw new Exception("Invalid CSV file.");
                }
            }
        }
        catch (IOException e)
        {
            
        }
    }
    
    public List<HashMap> getRows() {
        return this.rows;
    }
    
    private String[] explode(String row)
    {
        String record = row;
        // Strip out our line return
        record = record.replace(this.linereturn, "");
        // Split on the delimiter and return
        return row.split(this.delimiter);
    }
}
