/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes CSVs
 *
 * @author scottvanderlind
 */
public class CSVWriter
{
    // Administrative variables
    private String filename;    // The filename to write
    private String delimiter;   // The column delimiter
    private String linereturn;  // The line return character

    private String[] columns;
    private List<String> lines = new ArrayList<String>();

    /**
     * Constructs a new writer.
     *
     * @param filename the filename to write to.
     */
    public CSVWriter(String filename)
    {
        this(filename, ","); // comma is the default delimiter
    }

    /**
     * Instantiate with a filename and a delimiter
     *
     * @param filename file path
     * @param delimiter the delimiter
     */
    public CSVWriter(String filename, String delimiter)
    {
        this(filename, delimiter, "\n"); // \n is the default line return
    }

    /**
     * Instantiate with a custom filename, delimiter, and linereturn.
     *
     * @param filename input file
     * @param delimiter delimiter
     * @param linereturn linereturn
     */
    public CSVWriter(String filename, String delimiter, String linereturn)
    {
        this.filename = filename;
        this.delimiter = delimiter;
        this.linereturn = linereturn;
    }

    /**
     * Set the column headers.
     *
     * @param columns - an array of strings in the order they should appear in
     * the written file.
     */
    public void setColumns(String[] columns)
    {
        this.columns = columns;
    }

    /**
     * Add a line of values to the file.
     *
     * @param values - an array of strings in the order they should appear in
     * the written file.
     */
    public void addLine(String[] values)
    {
        this.lines.add(collapse(values));
    }

    /**
     * Flush the file buffer to the file.
     *
     * @return true if the write is successful, false otherwise
     */
    public int writeFile()
    {
        FileWriter outfile = null;
        BufferedWriter out = null;
        try
        {
            // Open the file for writing
            outfile = new FileWriter(this.filename);
            out = new BufferedWriter(outfile);

            // Write the column headers
            String cols = collapse(this.columns);
            out.write(cols);

            // Write each line
            for (String str : this.lines)
            {
                out.write(str);
            }

        }
        catch (IOException exception)
        {
            System.out.println("exection cvb werter");
        }
        finally
        {
            // Close the file.
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                }
            }
        }

        return 1;
    }

    private String collapse(String[] values)
    {
        StringBuilder result = new StringBuilder();

        // Concatenate each, add a comma if it's not the last
        for (int onVal = 0; onVal < values.length; onVal++)
        {
            result.append(values[onVal]);
            // IF it's not the last value, add a delimiter
            if (onVal < values.length - 1)
            {
                result.append(this.delimiter);
            }
            // IF it is the last value, add a line return
            else if (onVal == values.length - 1)
            {
                result.append(this.linereturn);
            }
        }

        return result.toString();
    }
}
