/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

/**
 * Tests the CSV Writer.
 *
 * @author scottvanderlind
 */
public class CSVTest
{

    /**
     * Runs the tests.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {

        CSVWriter writer = new CSVWriter("testout.csv");

        String[] cols =
        {
            "col a", "col b", "col c", "col d"
        };
        writer.setColumns(cols);

        String[] l1 =
        {
            "one", "two", "three", "four"
        };
        writer.addLine(l1);

        String[] l2 =
        {
            "100", "200", "300", "400"
        };
        writer.addLine(l2);

        writer.writeFile();

    }

}
