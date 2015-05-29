/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reader;

import com.sun.pdfview.PDFFile;
import com.universalquantification.examgrader.models.Answer;
import com.universalquantification.examgrader.models.Bubble;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.InputPage;
import com.universalquantification.examgrader.models.Student;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
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
    public void testGetExam1() throws Exception
    {
        System.out.println("Test getExam for Carole.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Carole.pdf";
        String exam1Answers[] =
        {
            "C",
            "B",
            "B",
            "A",
            "D",
            "B",
            "C",
            "A",
            "B",
            "D",
            "C",
            "D",
            "B",
            "C",
            "A",
            "A",
            "CE",
            "",
            "C",
            "B",
            "D",
            "A",
            "B",
            "C",
            "B", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam2() throws Exception
    {
        System.out.println("Test getExam for CY.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/CY.pdf";
        String exam1Answers[] =
        {
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "B",
            "B",
            "B",
            "B",
            "",
            "B",
            "B",
            "",
            "",
            "",
            "",
            "",
            "",
            "E",
            "E",
            "D",
            "",
            "D",
            "",
            "",
            "C",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "C",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "A",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam3() throws Exception
    {
        System.out.println("Test getExam for Erma.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Erma.pdf";

        String exam1Answers[] =
        {
            "E",
            "D",
            "C",
            "B",
            "A",
            "BC",
            "D",
            "C",
            "B",
            "C",
            "B",
            "AD",
            "C",
            "B",
            "C",
            "D",
            "D",
            "C",
            "B",
            "A",
            "B",
            "C",
            "C",
            "B",
            "C",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam3Rotated() throws Exception
    {
        System.out.println("Test getExam for Erma_Rotated.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Erma_Rotated.pdf";

        String exam1Answers[] =
        {
            "E",
            "D",
            "C",
            "B",
            "A",
            "BC",
            "D",
            "C",
            "B",
            "C",
            "B",
            "AD",
            "C",
            "B",
            "C",
            "D",
            "D",
            "C",
            "B",
            "A",
            "B",
            "C",
            "C",
            "B",
            "C",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam4() throws Exception
    {
        System.out.println("Test getExam for Felicia.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Felicia.pdf";
        String exam1Answers[] =
        {
            "A",
            "A",
            "B",
            "B",
            "C",
            "C",
            "D",
            "D",
            "E",
            "E",
            "D",
            "D",
            "C",
            "B",
            "A",
            "B",
            "C",
            "D",
            "CE",
            "ABCDE",
            "C",
            "C",
            "C",
            "C",
            "C",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam5() throws Exception
    {
        System.out.println("Test getExam for Jenny.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Jenny.pdf";
        String exam1Answers[] =
        {
            "E",
            "D",
            "C",
            "B",
            "A",
            "B",
            "C",
            "D",
            "E",
            "D",
            "ABCDE",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam6() throws Exception
    {
        System.out.println("Test getExam for Luis.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Luis.pdf";
        String exam1Answers[] =
        {
            "A",
            "C",
            "B",
            "D",
            "C",
            "B",
            "E",
            "B",
            "A",
            "C",
            "D",
            "B",
            "C",
            "E",
            "D",
            "C",
            "B",
            "A",
            "AC",
            "BD",
            "CE",
            "D",
            "D",
            "D",
            "D",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam6Rotated() throws Exception
    {
        System.out.println("Test getExam for Luis_Rotated.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Luis_Rotated.pdf";
        String exam1Answers[] =
        {
            "A",
            "C",
            "B",
            "D",
            "C",
            "B",
            "E",
            "B",
            "A",
            "C",
            "D",
            "B",
            "C",
            "E",
            "D",
            "C",
            "B",
            "A",
            "AC",
            "BD",
            "CE",
            "D",
            "D",
            "D",
            "D",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam6RotatedUpsidedown() throws Exception
    {
        System.out.println("Test getExam for Luis_Rotated_Upsidedown.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Luis_Rotated_Upsidedown.pdf";
        String exam1Answers[] =
        {
            "A",
            "C",
            "B",
            "D",
            "C",
            "B",
            "E",
            "B",
            "A",
            "C",
            "D",
            "B",
            "C",
            "E",
            "D",
            "C",
            "B",
            "A",
            "AC",
            "BD",
            "CE",
            "D",
            "D",
            "D",
            "D",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam7() throws Exception
    {
        System.out.println("Test getExam with non-exam file NonExam.pdf");
        File pdfFile = new File("test-input-files/NonExam.pdf");
        RandomAccessFile raf = new RandomAccessFile(pdfFile, "r");
        FileChannel channel = raf.getChannel();
        MappedByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                channel.size());
        PDFFile pdf = new PDFFile(buf);
        InputPage ip = new InputPage(pdfFile, pdf.getPage(1));

        NameRecognitionGateway g = new NameRecognitionGateway();
        ExamReader er = new ExamReader(g);
        try
        {
            er.getExam(ip);
            fail("Did not realize that this was not a valid exam file");
        }
        catch (InvalidExamException e)
        {

        }
    }

    public void testGetExam8() throws Exception
    {
        System.out.println("Test getExam for Raul.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Raul.pdf";
        String exam1Answers[] =
        {
            "C",
            "E",
            "AB",
            "CD",
            "B",
            "B",
            "A",
            "E",
            "C",
            "B",
            "C",
            "B",
            "A",
            "A",
            "D",
            "C",
            "C",
            "B",
            "C",
            "B",
            "D",
            "E",
            "E",
            "E",
            "AE",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam9() throws Exception
    {
        System.out.println("Test getExam for Santi.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Santi.pdf";

        String exam1Answers[] =
        {
            "A",
            "B",
            "C",
            "D",
            "E",
            "A",
            "A",
            "A",
            "A",
            "A",
            "B",
            "B",
            "B",
            "B",
            "B",
            "C",
            "C",
            "C",
            "C",
            "C",
            "D",
            "D",
            "D",
            "D",
            "D",
            "AB",
            "BC",
            "CD",
            "DE",
            "AC",
            "BD",
            "CE",
            "AE",
            "AD",
            "BE",
            "C",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);

    }

    public void testGetExam9Upsidedown() throws Exception
    {
        System.out.println("Test getExam for Santi_Upsidedown.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Santi_Upsidedown.pdf";

        String exam1Answers[] =
        {
            "A",
            "B",
            "C",
            "D",
            "E",
            "A",
            "A",
            "A",
            "A",
            "A",
            "B",
            "B",
            "B",
            "B",
            "B",
            "C",
            "C",
            "C",
            "C",
            "C",
            "D",
            "D",
            "D",
            "D",
            "D",
            "AB",
            "BC",
            "CD",
            "DE",
            "AC",
            "BD",
            "CE",
            "AE",
            "AD",
            "BE",
            "C",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam9Rotated() throws Exception
    {
        System.out.println("Test getExam for Santi_Rotated.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Santi_Rotated.pdf";

        String exam1Answers[] =
        {
            "A",
            "B",
            "C",
            "D",
            "E",
            "A",
            "A",
            "A",
            "A",
            "A",
            "B",
            "B",
            "B",
            "B",
            "B",
            "C",
            "C",
            "C",
            "C",
            "C",
            "D",
            "D",
            "D",
            "D",
            "D",
            "AB",
            "BC",
            "CD",
            "DE",
            "AC",
            "BD",
            "CE",
            "AE",
            "AD",
            "BE",
            "C",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam10() throws Exception
    {
        System.out.println("Test getExam for Scott.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Scott.pdf";
        String exam1Answers[] =
        {
            "A",
            "AB",
            "BC",
            "CD",
            "DE",
            "E",
            "DE",
            "CD",
            "BC",
            "AB",
            "A",
            "BC",
            "C",
            "B",
            "B",
            "C",
            "D",
            "D",
            "C",
            "B",
            "B",
            "C",
            "D",
            "D",
            "C",
            "ACDE",
            "ACE",
            "ABCE",
            "",
            "ABCDE",
            "AE",
            "AE",
            "",
            "ABCDE",
            "AE",
            "ABCDE",
            "",
            "E",
            "ABCDE",
            "E",
            "",
            "E",
            "ABCDE",
            "E",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam10Rotated() throws Exception
    {
        System.out.println("Test getExam Scott_Rotated.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Scott_Rotated.pdf";

        String exam1Answers[] =
        {
            "A",
            "AB",
            "BC",
            "CD",
            "DE",
            "E",
            "DE",
            "CD",
            "BC",
            "AB",
            "A",
            "BC",
            "C",
            "B",
            "B",
            "C",
            "D",
            "D",
            "C",
            "B",
            "B",
            "C",
            "D",
            "D",
            "C",
            "ACDE",
            "ACE",
            "ABCE",
            "",
            "ABCDE",
            "AE",
            "AE",
            "",
            "ABCDE",
            "AE",
            "ABCDE",
            "",
            "E",
            "ABCDE",
            "E",
            "",
            "E",
            "ABCDE",
            "E",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam11() throws Exception
    {
        System.out.println("Test getExam for Yvonne.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Yvonne.pdf";

        String exam1Answers[] =
        {
            "C",
            "B",
            "B",
            "C",
            "B",
            "A",
            "AB",
            "B",
            "CE",
            "BD",
            "BC",
            "AC",
            "D",
            "D",
            "C",
            "B",
            "D",
            "B",
            "D",
            "A",
            "C",
            "B",
            "C",
            "A",
            "B",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam11Rotated() throws Exception
    {
        System.out.println("Test getExam for Yvonne_Rotated.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Yvonne_Rotated.pdf";

        String exam1Answers[] =
        {
            "C",
            "B",
            "B",
            "C",
            "B",
            "A",
            "AB",
            "B",
            "CE",
            "BD",
            "BC",
            "AC",
            "D",
            "D",
            "C",
            "B",
            "D",
            "B",
            "D",
            "A",
            "C",
            "B",
            "C",
            "A",
            "B",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    public void testGetExam11RotatedUpsidedown() throws Exception
    {
        System.out.println("Test getExam for Yvonne_Rotated_Upsidedown.pdf");

        NameRecognitionGateway gateway = new NameRecognitionGateway();
        ExamReader instance = new ExamReader(gateway);
        String choices[] =
        {
            "A", "B", "C", "D", "E"
        };

        String exam1File = "test-input-files/Yvonne_Rotated_Upsidedown.pdf";

        String exam1Answers[] =
        {
            "C",
            "B",
            "B",
            "C",
            "B",
            "A",
            "AB",
            "B",
            "CE",
            "BD",
            "BC",
            "AC",
            "D",
            "D",
            "C",
            "B",
            "D",
            "B",
            "D",
            "A",
            "C",
            "B",
            "C",
            "A",
            "B",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

        examTestHelper(exam1File, exam1Answers, choices, instance, 100);
    }

    private static InputPage inputPageHelper(String path) throws
            FileNotFoundException, IOException
    {
        File pdfFile = new File(path);
        RandomAccessFile raf = new RandomAccessFile(pdfFile, "r");
        FileChannel channel = raf.getChannel();
        MappedByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                channel.size());
        PDFFile pdf = new PDFFile(buf);
        return new InputPage(pdfFile, pdf.getPage(1));
    }

    private static Exam examCreator(String answers[], int numQuestions,
            String choices[], InputPage page)
    {
        ArrayList<Answer> answerList = new ArrayList<Answer>();

        for (int onQ = 1; onQ < numQuestions + 1; onQ++)
        {
            ArrayList<Bubble> bubbles = new ArrayList<Bubble>();
            for (String c : choices)
            {
                bubbles.add(new Bubble(answers[onQ - 1].contains(c), c));
            }
            answerList.add(new Answer(bubbles, onQ));
        }

        Student s = new Student("student", "student", "student", null, null);
        Exam exam = new Exam(answerList, s, page);
        return exam;
    }

    private static void examTestHelper(String examFile, String examAnswers[],
            String choices[], ExamReader instance, int numQuestions) throws
            IOException, InvalidExamException
    {
        InputPage exam1Page = inputPageHelper(examFile);
        Exam exam1Expected = examCreator(examAnswers, 100, choices, exam1Page);
        Exam exam1Result = instance.getExam(exam1Page);

        for (int onQ = 1; onQ <= numQuestions; onQ++)
        {
            Answer expectedAnswer = exam1Expected.getAnswer(onQ);
            Answer gotAnswer = exam1Result.getAnswer(onQ);
            assertEquals(expectedAnswer, gotAnswer);
        }

    }
}
