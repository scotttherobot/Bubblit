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
    
    /**
     * Instantiate with one custom attribute
     * @param filename - the filename of the CSV
     * @throws Exception - the exception if the CSV is invalid
     */
    public CSVReader(String filename) throws Exception 
    {
        this(filename, ",");
    }
    
    /**
     * Instantiate with two custom attributes.
     * @param filename - the filename of the CSV
     * @param delimiter - the delimiter the CSV is split on
     * @throws Exception - the exception if the CSV is invalid
     */
    public CSVReader(String filename, String delimiter) throws Exception
    {
        this(filename, delimiter, "\n");
    }
    
    /**
     * Instantiate with three custom attributes.
     * @param filename - the filename of the CSV
     * @param delimiter - the delimiter the CSV is split on
     * @param linereturn - the line return the CSV returns on
     * @throws Exception  - the exception if the CSV is invalid
     */
    public CSVReader(String filename, String delimiter, String linereturn) throws Exception
    {
        this.filename = filename;
        this.delimiter = delimiter;
        this.linereturn = linereturn;
        parseFile();
    }
    
    /**
     * Actually converts the CSV into data structures
     * 
     * Opens a CSV file and matches the headers to the lines of data.
     * As we read lines, add them to the list of records.
     * @throws Exception - if the number of columns in a data row don't match 
     *                     the number of columns defined on line 0, we throw 
     *                     an error.
     */
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
    
    public List<HashMap> getRows() 
    {
        return this.rows;
    }
    
    /**
     * Return a record by a known column value
     * 
     * For example, to get the record of a student by their ID, call this
     * with column = "id" and value = {their student id}.
     * @param column - the column to match on
     * @param value - the value of the column you'd like to see.
     * @return 
     */
    public HashMap getRecordByColumnValue(String column, String value)
    {
        for (HashMap record : this.rows)
        {
            if (record.get(column).equals(value))
            {
                return record;
            }
        }
        return null;
    }
    
    /**
     * Takes a string of delimiter separated values and returns a string array.
     * 
     * Kinda like the php funcuton explode();
     * It strips the set line return and then splits on the designated delimeter.
     * @param row - the CSV string.
     * @return - an array of strings that were between the delimiter.
     */
    private String[] explode(String row)
    {
        String record = row;
        // Strip out our line return
        record = record.replace(this.linereturn, "");
        // Split on the delimiter and return
        return row.split(this.delimiter);
    }
}
