/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

import java.util.HashSet;

/**
 *
 * @author scottvanderlind
 */
public class CSVTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        CSVWriter w = new CSVWriter("testout.csv");
        
        String[] cols = {"col a", "col b", "col c", "col d"};
        w.setColumns(cols);
        
        
        String[] l1 = {"one", "two", "three", "four"};
        w.addLine(l1);
        
        String[] l2 = {"100", "200", "300", "400"};
        w.addLine(l2);
        
        w.writeFile();
        
    }
    
}
