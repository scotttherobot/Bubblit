package com.universalquantification.examgrader.grader;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Roster loads in a student roster file and turns it into a list of map of
 * student information.
 *
 * @version 2.0
 * @author Scott Vanderlind
 */
public class Roster
{
    private final static int kRosterHeaderLineCount = 8;

    /**
     * A list of maps, each of which represents the record for a student.
     */
    private List<HashMap> students = new ArrayList<HashMap>();

    private static final String kTSVHeaderFormat
        = "No.\tStudent Name\tStudent Username\tEMPLID\t";

    /**
     * A Roster takes in a reader.
     *
     * The reader must contain content with the format defined <a href=
     * "http://pastebin.com/raw.php?i=QuutPx6h">here.</a>
     *
     * @param reader reader to take in
     *
     * @pre the reader must not be closed
     */
    public Roster(Reader reader) throws Exception
    {
        // Skip the header
        Scanner input = new Scanner(reader);
        String line = "";
        int numLines = 0;

        // skip the correct number of lines
        while (input.hasNext() && numLines++ <= kRosterHeaderLineCount)
        {
            line = input.nextLine();
            //System.out.println(line);
        }

        // ensure that we still have input left and that we have a valid header.
        if (!input.hasNext() || !line.startsWith(kTSVHeaderFormat))
        {
            throw new Exception();
        }

        // The first line is our headers
        String[] columns = explode(line);

        boolean doContinue = true;

        line = input.nextLine();
        // The line after the roster starts with a tab
        while (!line.startsWith("\t") && doContinue)
        {
            // We have a row. Explode it.
            String[] values = explode(line);
            // Map the columns to the values if they're 1:1
            if (values.length > 3)
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
                //System.out.println("ERROR: COLUMN/VALUE MISMATCH! " + line);
                //System.out.println(values.length + "!=" + columns.length);
            }

            // make sure we still have input
            if (input.hasNext())
            {
                line = input.nextLine();
            }
            else
            {
                doContinue = false;
            }

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

    /**
     * Get the number of students on this roster.
     *
     * @return the number of students.
     */
    public int getNumStudents()
    {
        return this.students.size();
    }
}
