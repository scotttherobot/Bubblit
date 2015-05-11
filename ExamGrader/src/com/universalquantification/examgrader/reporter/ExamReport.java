/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reporter;

import com.universalquantification.examgrader.models.Exam;
import java.io.File;

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
     * Instantiates a new ExamReport given a graded exam and a file
     * to write to
     * @param exam - the graded exam
     * @param writeFile - the output file
     */
    public ExamReport(Exam exam,  File writeFile)
    {
    }
    
    /**
     * Writes the HTML report to file
     */
    public void writeReport()
    {
    }
    
}
