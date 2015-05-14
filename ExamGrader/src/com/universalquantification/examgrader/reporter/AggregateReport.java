/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reporter;

import com.universalquantification.examgrader.models.GradedExamCollection;
import java.io.File;
import java.util.HashMap;

/**
 * an AggregateReport is a results report for a GradedExamCollection.
 * It is a modified CSV format (non-IEEE conforming) defined by
 * Mr. Dalbey in the piazza thread: https://piazza.com/class/i89evu12g2cfo?cid=9
 *
 * @author Scott Vanderlind
 * @date 2015-05-10
 * @version 2.0
 */
public class AggregateReport
{
    
    /**
     * The collection of exams to create a report for.
     */
    private GradedExamCollection exams;
    
    /**
     * The output file.
     */
    private File outfile;
    
    /**
     * The variables that will be put into the template.
     */
    private HashMap<String, Object> scope;
    
    /**
     * The name of the template file to compile.
     */
    private static String aggregateReportTemplateName;

    
    /**
     * Instantiates an AggregateReport for a collection of exams
     * @param exams - the GradedExamCollection of graded exams
     * @param writeFile - the file to write the aggregate report to
     */
    public AggregateReport(GradedExamCollection exams, File writeFile)
    {
        // SET this.exams to exams
        // SET this.outfile to writeFile
        
        // CALL scope.put with this.exams
    }
    
    /**
     * Writes the report to the file.
     */
    public void writeReport()
    {
        // INIT a new MustacheFactory mf
        // SET generator to mf.compile with examReportTemplateName
        // CALL generator.execute with outfile and scope
    }
}
