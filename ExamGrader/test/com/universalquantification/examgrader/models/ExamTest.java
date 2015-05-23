package com.universalquantification.examgrader.models;

import java.util.HashSet;
import java.util.Arrays;
import junit.framework.TestCase;

import static com.universalquantification.examgrader.helpers.ExamHelper.createExam;
import static com.universalquantification.examgrader.helpers.ExamHelper.createAnswer;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Jenny Wang / C.Y. Tan
 */
public class ExamTest extends TestCase 
{
    public ExamTest(String testName) 
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

    public void testGetExamFile() {
        Exam exam = createExam(Arrays.asList(0b10000, 0b01000, 0b00010), "Mathematics", "Numismatics");
        
        assertEquals(exam.getExamFile(), exam.getExamFile());
    }
    
    public void testGetAnswer() 
    {
        Exam exam = createExam(Arrays.asList(0b10000, 0b01000, 0b00010), "Pterodactyl", "Redacted");
        Answer a = createAnswer(0b10000, 1);
        Answer b = createAnswer(0b01000, 2);
        Answer c = createAnswer(0b00010, 3);
        
        assertTrue(exam.getAnswer(1).equals(a));
        assertTrue(exam.getAnswer(2).equals(b));
        assertTrue(exam.getAnswer(3).equals(c));
    }
    
    public void testGetStudentRecord() {
        Exam exam = createExam(Arrays.asList(0b10000, 0b01000, 0b00010), "Hip Hop", "Chop Shop");
        
        assertEquals(exam.getStudentRecord(), exam.getStudentRecord());
    }
    
    public void testIsQuestionCorrect() 
    {
        Exam exam = createExam(Arrays.asList(0b10000, 0b01000, 0b00100, 0b00010), "Nickel", "Dime");
        Exam answerKey = createExam(Arrays.asList(0b10000, 0b00001, 0b10000, 0b00010), "5 Dollar Coffee", "Crime");

        exam.grade(answerKey);
        
        assertTrue(exam.isQuestionCorrect(1));
        assertTrue(!exam.isQuestionCorrect(2));
        assertTrue(!exam.isQuestionCorrect(3));
        assertTrue(exam.isQuestionCorrect(4));
    }
    
    public void testGetCorrectQuestions() 
    {
        Exam exam = createExam(Arrays.asList(0b10000, 0b01000, 0b00100, 0b00010), "Buttons", "String");
        Exam answerKey = createExam(Arrays.asList(0b10000, 0b00001, 0b10000, 0b00010), "Software", "Clothing");

        exam.grade(answerKey);
        
        Set<Integer> correctQuestions = exam.getCorrectQuestions();
        Iterator questionIterator = correctQuestions.iterator();
        
        assertEquals(questionIterator.next(), 1);
        assertEquals(questionIterator.next(), 4);
    }
    
    public void testGetQuestions() 
    {
        Exam exam = createExam(Arrays.asList(0b10000, 0b01000, 0b00100, 0b00010), "Rot", "Weiler");
        
        Set<Integer> questions = exam.getQuestions();
        Iterator questionIterator = questions.iterator();
        int index = 1;
        
        while (questionIterator.hasNext()) {
            assertEquals(questionIterator.next(), index);
            
            index++;
        }
    }
    
    public void testIncorrectQuestions() {
        Exam exam = createExam(Arrays.asList(0b10000, 0b01000, 0b00100, 0b00010), "Hi's", "Byes");
        Exam answerKey = createExam(Arrays.asList(0b10000, 0b00001, 0b10000, 0b00010), "Time", "Flies");
        
        exam.grade(answerKey);
        
        Set<Integer> incorrectQuestions = exam.getIncorrectQuestions();
        Iterator questionIterator = incorrectQuestions.iterator();
        
        assertEquals(questionIterator.next(), 2);
        assertEquals(questionIterator.next(), 3);
    }
    
    public void testGetRawScore() {
        Exam exam = createExam(Arrays.asList(0b11000, 0b01100, 0b00100, 0b00010), "Phone", "Home");
        Exam answerKey = createExam(Arrays.asList(0b11000, 0b01100, 0b00000, 0b00100), "Thoughts", "Alone");
        
        exam.grade(answerKey);
        
        assertEquals(exam.getRawScore(), 2);
    }
    
    public void testGetPercentScore() {
        Exam exam = createExam(Arrays.asList(0b11000, 0b01100, 0b00100, 0b00010), "Late", "Night");
        Exam answerKey = createExam(Arrays.asList(0b11000, 0b01100, 0b00000, 0b00100), "Still", "Light");
        
        exam.grade(answerKey);
        
        assertEquals(exam.getPercentScore(), 0.5);
    }

    public void testFailedExam() 
    {
        Exam answerKey = createExam(Arrays.asList(0b1000, 0b1000, 0b1000, 0b1000), "1/4", "Correct");
        Exam exam = createExam(Arrays.asList(0b0001, 0b0010, 0b0100, 0b0000), "All", "Empty");

        exam.grade(answerKey);

        assertEquals(exam.getRawScore(), 0);

        assertTrue(exam.getCorrectQuestions().isEmpty());
    }

    public void testExam() 
    {
        Exam answerKey = createExam(Arrays.asList(0b1000, 0b1000, 0b1000, 0b1000), "1/4", "Correct");
        Exam exam = createExam(Arrays.asList(0b1000, 0b1000, 0b0100, 0b0000), "Great", "Student");

        exam.grade(answerKey);

        assertEquals(exam.getRawScore(), 2);
        assertEquals(exam.getPercentScore(), 0.5, 0.001);

        assertEquals(exam.getCorrectQuestions(), new HashSet<>(Arrays.asList(1, 2)));
        assertEquals(exam.getIncorrectQuestions(), new HashSet<>(Arrays.asList(3, 4)));

        assertTrue(exam.isQuestionCorrect(1));
        assertTrue(exam.isQuestionCorrect(2));
        assertFalse(exam.isQuestionCorrect(3));
        assertFalse(exam.isQuestionCorrect(4));
    }

    public void testToString()
    {
        Exam exam = createExam(Arrays.asList(0b10000, 0b01000, 0b00100, 0b00010, 0b00001), "Crickets", "Chirp");
        String expectedString = "Exam\n" +
            "Student: " + exam.getStudentRecord().toString() + "\nQuestions:\n" +
            "\t1) A\n\t2) B\n\t3) C\n\t4) D\n\t5) E\n";

        assertTrue(exam.toString().equals(expectedString));
    }
}
