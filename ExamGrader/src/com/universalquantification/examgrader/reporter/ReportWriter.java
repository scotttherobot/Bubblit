package com.universalquantification.examgrader.reporter;

import com.universalquantification.examgrader.models.GradedExamCollection;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * a ReportWriter is a dispatcher for writing reports for a map of
 * GradedExamCollections.
 * 
 * @author Scott Vanderlind
 * @date 2015-05-10
 * @version 2.0
 */
public class ReportWriter
{
    private File outputDirectoryOverride;
    
    /**
     * Instantiate a writer to a particular file path.
     * If outputDirectoryOverride is not set, the default output path will
     * be used.
     * @param outputDirectoryOverride - the base directory to write reports to
     */
    public ReportWriter(File outputDirectoryOverride)
    {
    }
    
    /**
     * Writes the output reports given a map of GradedExamCollections.
     * For each GradedExamCollection, an AggregateReport is created and written,
     * and for each Exam in that GradedExamCollection an ExamReport is
     * created and written.
     * @param collection - graded exams
     * @throws IOException 
     */
    public void writeReports(Map<File, GradedExamCollection> collection) throws IOException
    {
    }
    
}
