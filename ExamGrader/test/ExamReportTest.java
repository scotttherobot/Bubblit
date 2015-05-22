/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.universalquantification.examgrader.models.Answer;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.InputPage;
import com.universalquantification.examgrader.models.Student;
import com.universalquantification.examgrader.reporter.ExamReport;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import junit.framework.TestCase;

/**
 *
 * @author scottvanderlind
 */
public class ExamReportTest extends TestCase
{
    private Exam exam;
    
    public ExamReportTest(String testName)
    {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        
        // TODO: Set up a new Exam object here.
        //this.exam = new Exam();
    }
    
    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
    
    public void testWriteReport()
    {
        String expected = "scott";
        StringWriter output = new StringWriter();
        
        String templateString = "{{exam.name}}";
        StringReader reportFormat = new StringReader(templateString);
        
        ExamReport report = new ExamReport(exam, output, reportFormat);
        report.writeReport();
        
        assertSame(expected, output.toString());
    }
}
