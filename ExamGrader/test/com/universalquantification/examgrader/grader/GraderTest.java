/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.grader;

import com.sun.pdfview.PDFFile;
import com.universalquantification.examgrader.models.GradedExamCollection;
import com.universalquantification.examgrader.models.InputFileList;
import com.universalquantification.examgrader.models.InputPage;
import com.universalquantification.examgrader.reader.ExamReader;
import com.universalquantification.examgrader.reader.NameRecognitionGateway;
import com.universalquantification.examgrader.reader.StudentNameMapper;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author luis
 */
public class GraderTest extends TestCase
{
    
    public GraderTest(String testName)
    {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Test of cancel method, of class Grader.
     */
    public void testCancel()
    {
        /*
        System.out.println("cancel");
        Grader instance = null;
        instance.cancel();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of grade method, of class Grader.
     */
    public void testGrade() throws Exception
    {
        System.out.println("grade");
        //public Grader(InputFileList inputFileList, ExamReader examReader, StudentNameMapper mapper)
       
        InputFileList list = new InputFileList();
        File pdfFile = new File("ExamsScannedDalbey.pdf");
        list.addInputFile(pdfFile);

        StudentNameMapper mapper = new StudentNameMapper(null);
        NameRecognitionGateway g = new NameRecognitionGateway();
        Grader instance = new Grader(list, new ExamReader(mapper,g), mapper);
        Map<File, GradedExamCollection> expResult = null;
        Map<File, GradedExamCollection> result = instance.grade();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of updateRoster method, of class Grader.
     */
    public void testUpdateRoster() throws Exception
    {
//        System.out.println("updateRoster");
//        File file = null;
//        Grader instance = null;
//        instance.updateRoster(file);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getPagesGraded method, of class Grader.
     */
    public void testGetPagesGraded()
    {
//        System.out.println("getPagesGraded");
//        Grader instance = null;
//        int expResult = 0;
//        int result = instance.getPagesGraded();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalPagesToGrade method, of class Grader.
     */
    public void testGetTotalPagesToGrade()
    {
//        System.out.println("getTotalPagesToGrade");
//        Grader instance = null;
//        int expResult = 0;
//        int result = instance.getTotalPagesToGrade();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
