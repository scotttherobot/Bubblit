/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reporter;

import static com.universalquantification.examgrader.helpers.ExamHelper.createExam;
import com.universalquantification.examgrader.models.Answer;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.InputPage;
import com.universalquantification.examgrader.models.Student;
import com.universalquantification.examgrader.reporter.ExamReport;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import junit.framework.TestCase;

/**
 *
 * @author scottvanderlind
 */
public class ExamReportTest extends TestCase
{
    
    Exam exam;
    Exam answerKey;
    
    public ExamReportTest(String testName)
    {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        
        this.exam = createExam(Arrays.asList(0b11000, 0b01100, 0b00100, 0b00010), "Phone", "Home");
        this.answerKey = createExam(Arrays.asList(0b11000, 0b01100, 0b00000, 0b00100), "Thoughts", "Alone");
        
        this.exam.grade(answerKey);
    }
    
    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }


    public void testWriteReport()
    {
        String expected = "score: " + this.exam.getRawScore();
        StringWriter output = new StringWriter();
        
        String templateString = "score: {{#exam}}{{getRawScore}}{{/exam}}";
        StringReader reportFormat = new StringReader(templateString);
        
        ExamReport report = new ExamReport(this.exam, output, reportFormat);
        report.writeReport();
        
        assertEquals(expected, output.toString());
    }
}
