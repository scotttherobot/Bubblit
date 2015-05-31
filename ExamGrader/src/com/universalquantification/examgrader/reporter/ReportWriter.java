package com.universalquantification.examgrader.reporter;

import com.google.common.io.Files;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.GradedExamCollection;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
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
    
    private static final String RESOURCE_PATH = "resources";
    
    /**
     * Instantiate a writer to a particular file path.
     * If outputDirectoryOverride is not set, the default output path will
     * be used.
     * @param outputDirectory the directory to write to
     */
    public ReportWriter(File outputDirectory)
    {
        // SET this.outputDirectory to outputDirectory
        this.outputDirectory = outputDirectory;
    }
    
    private File getOutputDirectory(File file)
    {
        File parent;
        if (outputDirectory == null)
        {
            parent = file.getParentFile();
        }
        else
        {
            parent = outputDirectory;
        }
        return parent;
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
            // CALL getOutputDirectory AS f
            File outputDirectory = getOutputDirectory(f);
            GradedExamCollection gradedExams = entry.getValue();
        
            // INIT a new FileWriter with the destination path
            // Create the output directory for the HTML reports
            String nameWithoutExtension = Files.getNameWithoutExtension(f.getName());
            FileWriter outfile = new FileWriter(new File(outputDirectory, nameWithoutExtension + "_aggregate.csv"));
            // INIT a new FileReader with the template path
   
            InputStreamReader aggregateTemplate = new InputStreamReader(this.getClass().getResourceAsStream(
                RESOURCE_PATH + "/aggregate_report.csv"));
            //FileReader aggregateTemplate = new FileReader(ior);
            // INIT a new AggregateReport with file + "_aggregate", examCollection
            AggregateReport ar = new AggregateReport(gradedExams, outfile, aggregateTemplate);
            // CALL AggregateReport.writeReport
            ar.writeReport();
            
            File outfolder = new File(outputDirectory, nameWithoutExtension + "_www");
            outfolder.mkdir();
            
            // FOR EACH exam in the examCollection
            for (Exam graded : gradedExams.getGradedExams())
            {
                // INIT a new File with the destination path
                File location = new File(outfolder,
                        graded.getStudentRecord().getId() + ".html");
                // INIT a new FileWriter with the destination path
                FileWriter examReportOut = 
                 new FileWriter(location.getAbsolutePath());
                // INIT a new FileReader with the template path
                //FileReader examReportTemplate = new FileReader("src/com/universalquantification/examgrader/reporter/resources/exam_report.html");
                InputStreamReader examReportTemplate = new InputStreamReader(this.getClass().getResourceAsStream(
                RESOURCE_PATH + "/exam_report.html"));
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
