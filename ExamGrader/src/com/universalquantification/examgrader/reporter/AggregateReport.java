/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reporter;

import com.universalquantification.examgrader.models.GradedExamCollection;
import java.io.File;

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
     * Instantiates an AggregateReport for a collection of exams
     * @param exams - the GradedExamCollection of graded exams
     * @param writeFile - the file to write the aggregate report to
     */
    public AggregateReport(GradedExamCollection exams, File writeFile)
    {
        
    }
    
    /**
     * Writes the report to the file.
     */
    public void writeReport()
    {
    }
}
