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
    /**
     * The directory where the reports shall be written
     */
    private File outputDirectory;
    
    /**
     * Instantiate a writer to a particular file path.
     * If outputDirectoryOverride is not set, the default output path will
     * be used.
     * @param outputDirectoryOverride - the base directory to write reports to
     */
    public ReportWriter(File outputDirectory)
    {
        // SET this.outputDirectory to outputDirectory
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
        // FOR EACH file, examCollection in the collection map
            // INIT a new AggregateReport with file + "_aggregate", examCollection
            // CALL AggregateReport.writeReport
            //
            // FOR EACH exam in the examCollection
                // INIT a new ExamReport with exam, file + "_" + exam.student.id
                // CALL ExamReport.writeReport
            // END FOR
        // END FOR
    }
    
}
