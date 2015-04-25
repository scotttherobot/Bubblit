/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

import java.util.List;

/**
 *
 * @author jenwang
 */
public class Report {
   
    public final String[] headers;
    public final List<String[]> rows;
    
    public Report(String[] headers, List<String[]> rows)
    {
        this.headers = headers;
        this.rows = rows;
    }
    
}
