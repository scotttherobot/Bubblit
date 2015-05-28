package com.universalquantification.examgrader.reporter;

import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.GradedExamCollection;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
        this.outputDirectory = outputDirectory;
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
        for (Map.Entry<File, GradedExamCollection> entry : collection.entrySet())
        {
            File f = entry.getKey();
            GradedExamCollection gradedExams = entry.getValue();
        
            // INIT a new FileWriter with the destination path
            FileWriter outfile = new FileWriter(outputDirectory.getAbsolutePath() + f.getName() + "_aggregate.csv");
            // INIT a new FileReader with the template path
            FileReader aggregateTemplate = new FileReader("aggregate_report.csv");
            // INIT a new AggregateReport with file + "_aggregate", examCollection
            AggregateReport ar = new AggregateReport(gradedExams, outfile, aggregateTemplate);
            // CALL AggregateReport.writeReport
            ar.writeReport();
            
            // FOR EACH exam in the examCollection
            for (Exam graded : gradedExams.getGradedExams())
            {
                // INIT a new FileWriter with the destination path
                FileWriter examReportOut = 
                 new FileWriter(outputDirectory.getAbsolutePath() + "/" 
                 + f.getName() + "_www/" + graded.getStudentRecord().getId() 
                 + ".html");
                // INIT a new FileReader with the template path
                FileReader examReportTemplate = new FileReader("exam_report.html");
                // INIT a new ExamReport with exam, file + "_" + exam.student.id
                ExamReport eReport = new ExamReport(graded, examReportOut, examReportTemplate);
                // CALL ExamReport.writeReport
                eReport.writeReport();
            // END FOR
            }
        // END FOR
        }
    }
    
}
