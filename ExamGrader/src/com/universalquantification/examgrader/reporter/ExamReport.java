/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reporter;

import com.universalquantification.examgrader.models.Exam;
import java.io.File;
import java.util.HashMap;

/**
 * an ExamReport is a per-student report of exam results.
 * Takes an instance of an Exam object and writes an HTML
 * document showing the results.
 *
 * @author Scott Vanderlind
 * @date 2015-05-10
 * @version 2.0
 */
public class ExamReport
{
    /**
     * The exam for which a report will be written.
     */
    private Exam exam;
    
    /**
     * The destination file the report will be written to.
     */
    private File outfile;
    
    /**
     * The variables that will be put into the template.
     */
    private HashMap<String, Object> scope;
    
    /**
     * The name of the template file to compile.
     */
    private static String examReportTemplateName;

    /**
     * Instantiates a new ExamReport given a graded exam and a file
     * to write to
     * @param exam - the graded exam
     * @param writeFile - the output file
     */
    public ExamReport(Exam exam,  File writeFile)
    {
        // SET this.exam to exam
        // SET this.outfile to writeFile
        
        // CALL scope.put with "exam" and this.exam
    }
    
    /**
     * Writes the HTML report to file
     */
    public void writeReport()
    {
        // INIT a new MustacheFactory mf
        // SET generator to mf.compile with examReportTemplateName
        // CALL generator.execute with outfile and scope
    }
    
}
