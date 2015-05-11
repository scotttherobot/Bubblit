package com.universalquantification.examgrader.reader;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Roster loads in a student roster file and turns it into a list of map 
 * of student information.
 * @version 2.0
 * @author Scott Vanderlind
 */
public class Roster
{
    private final static int rosterHeaderLineCount = 8;
    
    /**
     * A list of maps, each of which represents the record for a student.
     */
    private List<HashMap> students = new ArrayList<HashMap>();
    
    /**
     * A Roster takes in a reader.
     * 
     * The reader must contain content with the format defined <a href=
     * "http://pastebin.com/raw.php?i=QuutPx6h">here.</a>
     * @param reader reader to take in
     * 
     * @pre the reader must not be closed
     */
    public Roster (Reader reader)
    {
        // TODO: sub in the count variable here.
        // Skip the header
        Scanner input = new Scanner(reader).skip("(?:.*\\r?\\n|\\r){8}");
        //String line = "";
        
        // The first line is our headers
        String line = input.nextLine();
        String[] columns = explode(line);
        
        line = input.nextLine();
        // The line after the roster starts with a tab
        while (!line.startsWith("\t"))
        {
            // We have a row. Explode it.
            String[] values = explode(line);
            // Map the columns to the values if they're 1:1
            if (values.length == columns.length)
            {
                HashMap<String, String> record
                    = new HashMap<String, String>();
                // add all values to their columns
                for (int onVal = 0; onVal < values.length; onVal++)
                {
                    record.put(columns[onVal], values[onVal]);
                }
                // Add to master hashmap
                this.students.add(record);
            }
            else
            {
                System.out.println("ERROR: COLUMN/VALUE MISMATCH!");
            }
            
            line = input.nextLine();
        }
    }
    
    /**
     * Return a record by a known column value
     *
     * For example, to get the record of a student by their ID, call this with
     * column = "id" and value = {their student id}.
     *
     * @param column - the column to match on
     * @param value - the value of the column you'd like to see.
     * @return record for the row
     */
    public HashMap getRecordByColumnValue(String column, String value)
    {
        // find the appropriate row
        for (HashMap record : this.students)
        {
            // if we found the row, return it
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
     * Strips the set line return and then splits on the designated delimiter.
     *
     * @param row - the CSV string.
     * @return - an array of strings that were between the delimiter.
     */
    private String[] explode(String row)
    {
        String record = row;
        // Strip out our line return
        record = record.replace("\n", "");
        // Split on the delimiter and return
        return row.split("\t");
    }
}
