/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reporter;

import com.google.common.collect.Maps;
import static com.universalquantification.examgrader.helpers.ExamHelper.createExam;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.GradedExamCollection;
import com.universalquantification.examgrader.utils.PreferencesManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import junit.framework.TestCase;
import static org.mockito.Mockito.mock;

/**
 *
 * @author jenny
 */
public class ReportWriterTest extends TestCase {
    
    private GradedExamCollection gradedExamCollection;
    
    public ReportWriterTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        Exam answerKey = createExam(Arrays.asList(0b0001, 0b0010, 0b0100, 0b1000),
                "answer", "key");
        
        List<Exam> studentExams = Arrays.asList(
            createExam(Arrays.asList(0b1000, 0b1000, 0b1000, 0b1000), "1/4", "Correct"),
            createExam(Arrays.asList(0b0000, 0b0000, 0b0000, 0b0000), "All", "Empty"),
            createExam(Arrays.asList(0b0001, 0b0010, 0b0100, 0b1000), "Perfect", "Score")
        );
        
        for (Exam exam: studentExams)
        {
            exam.grade(answerKey);
        }
        
        gradedExamCollection =
                new GradedExamCollection(answerKey, studentExams);
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testWriteReports() throws IOException
    {
        Path overrideDir = Files.createTempDirectory("override");
        File dirFile = new File(overrideDir.toUri());
        dirFile.deleteOnExit();
        PreferencesManager.getInstance().set("output-path", dirFile.getAbsolutePath());
        ReportWriter writer = new ReportWriter();
        
        Map<File, GradedExamCollection> map = Maps.newHashMap();
        map.put(new File("foo.txt"), gradedExamCollection);
        map.put(new File("bar.pdf"), gradedExamCollection);
        
        writer.writeReports(map);
        
        File[] list = dirFile.listFiles();
        Set<File> files = new HashSet<File>(Arrays.asList(list));
        assertTrue(files.contains(new File(dirFile, "foo_aggregate.csv")));
        assertTrue(files.contains(new File(dirFile, "bar_aggregate.csv"))); 
        
        File barDir = new File(dirFile, "bar_www");
        File fooDir = new File(dirFile, "foo_www");
        assertTrue(files.contains(barDir));
        assertTrue(files.contains(fooDir));
        
        Set<File> fooReports = new HashSet<>(Arrays.asList(fooDir.listFiles()));
        
       
        assertTrue(fooReports.contains(new File(fooDir, "id Correct.html")));
        assertTrue(fooReports.contains(new File(fooDir, "id Empty.html")));
        assertTrue(fooReports.contains(new File(fooDir, "id Score.html")));
        
    }
}
