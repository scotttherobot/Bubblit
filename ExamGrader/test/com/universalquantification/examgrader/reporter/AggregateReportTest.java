/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reporter;

import static com.universalquantification.examgrader.helpers.ExamHelper.createExam;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.GradedExamCollection;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 *
 * @author scottvanderlind
 */
public class AggregateReportTest extends TestCase
{
    
    private Exam answerKey;
    private List<Exam> studentExams;
    private GradedExamCollection gradedExamCollection;
    
    public AggregateReportTest(String testName)
    {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        answerKey = createExam(Arrays.asList(0b0001, 0b0010, 0b0100, 0b1000),
                "answer", "key");
        
        studentExams = Arrays.asList(
            createExam(Arrays.asList(0b1000, 0b1000, 0b1000, 0b1000), "1/4", "Correct"),
            createExam(Arrays.asList(0b0000, 0b0000, 0b0000, 0b0000), "All", "Empty"),
            createExam(Arrays.asList(0b0001, 0b0010, 0b0100, 0b1000), "Perfect", "Score")
        );
        
        for (Exam exam: studentExams)
        {
            exam.grade(answerKey);
        }
        
        gradedExamCollection = new GradedExamCollection(answerKey, studentExams);
    }
    
    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    public void testWriteReport()
    {
        String expected = "4";
        StringWriter output = new StringWriter();
        
        String templateString = "{{#exams}}{{#getAnswerKey}}{{getQuestionCount}}{{/getAnswerKey}}{{/exams}}";
        StringReader reportFormat = new StringReader(templateString);
        
        AggregateReport report = new AggregateReport(this.gradedExamCollection, output, reportFormat);
        report.writeReport();
        
        assertEquals(expected, output.toString());
    }
}
