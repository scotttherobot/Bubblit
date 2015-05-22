/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reader;

import com.sun.pdfview.PDFFile;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.InputPage;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import junit.framework.TestCase;

/**
 *
 * @author luis
 */
public class ExamReaderTest extends TestCase
{
    
    public ExamReaderTest(String testName)
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
     * Test of getExam method, of class ExamReader.
     */
    public void testGetExam() throws Exception
    {
        System.out.println("getExam");
        
        File pdfFile = new File("ExamsScannedDalbey.pdf");
        RandomAccessFile raf = new RandomAccessFile(pdfFile, "r");
        FileChannel channel = raf.getChannel();
        MappedByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,channel.size());
        PDFFile pdf = new PDFFile(buf);
        
        InputPage file;
        
        StudentNameMapper mapper = new StudentNameMapper(null);
        ExamReader instance = new ExamReader(mapper);
        Exam expResult = null;
        for(int i = 1; i < pdf.getNumPages() + 1; i++)
        {
            System.out.println("===Exam " + i + "======");
             file = new InputPage(pdfFile, pdf.getPage(i));
             Exam result = instance.getExam(file, mapper);
             System.out.println(result.toString());
        }
        
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
