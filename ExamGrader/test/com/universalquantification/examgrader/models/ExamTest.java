package com.universalquantification.examgrader.models;

import com.google.common.collect.Maps;
import java.util.HashSet;
import java.util.Arrays;
import junit.framework.TestCase;

import static com.universalquantification.examgrader.helpers.ExamHelper.createExam;
import static com.universalquantification.examgrader.helpers.ExamHelper.createAnswer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;

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
        Exam exam = createExam(Arrays.asList(0b11000, 0b01100, 0b00000, 0b00010), "Late", "Night");
        Exam answerKey = createExam(Arrays.asList(0b11000, 0b01100, 0b00100, 0b00100), "Still", "Light");
        
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
        Exam answerKey = createExam(Arrays.asList(0b10000, 0b10000, 0b10000, 0b10000), "1/4", "Correct");
        Exam exam = createExam(Arrays.asList(0b10000, 0b10000, 0b01000, 0b00000), "Great", "Student");

        exam.grade(answerKey);

        assertEquals(exam.getRawScore(), 2);
        assertEquals(exam.getPercentScore(), 0.5);

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

        assertNotNull(exam.toString());
    }
    
    public void testGetMaxScore()
    {
       Exam exam = createExam(Arrays.asList(0b10000, 0b01000, 0b00100, 0b00010), "Nickel", "Dime");
       Exam answerKey = createExam(Arrays.asList(0b10000, 0b00001, 0b10000, 0b00010), "5 Dollar Coffee", "Crime");

       exam.grade(answerKey);
        
       assertEquals(exam.getMaxScore(), answerKey.getQuestionCount());
    }
    
    public void getImage()
    {
        Exam exam = createExam(Arrays.asList(0b10000, 0b01000, 0b00100, 0b00010), "Nickel", "Dime");
        assertNotNull(exam.getExamImageB64());
    }
    
    public void testGetCorrectQuestionsFeedback()
    {
       Exam exam = createExam(Arrays.asList(0b10000, 0b01000, 0b00100, 0b00010), "Nickel", "Dime");
       Exam answerKey = createExam(Arrays.asList(0b10000, 0b00001, 0b10000, 0b00010), "5 Dollar Coffee", "Crime");

       exam.grade(answerKey);
        
       Map<String, String> correct = Maps.newLinkedHashMap();
       correct.put("1", exam.getAnswer(1).toString());
       correct.put("4", exam.getAnswer(4).toString());
       
       List<Entry<String, String>> expected = new ArrayList<>(exam.getCorrectFeedback());
       
       List<Entry<String, String>> correctEntries =  new ArrayList<>(correct.entrySet());
      
       for (int i = 0; i < correctEntries.size(); i++)
       {
           assertEquals(expected.get(i).getKey(), correctEntries.get(i).getKey());  
           assertEquals(expected.get(i).getValue(), correctEntries.get(i).getValue());
       }
       
       assertEquals(expected.size(), correctEntries.size());
    }
    
    public void testGetIncorrectQuestionsFeedback()
    {
       Exam exam = createExam(Arrays.asList(0b10000, 0b01000, 0b00100, 0b00010), "Nickel", "Dime");
       Exam answerKey = createExam(Arrays.asList(0b10000, 0b00001, 0b10000, 0b00010), "5 Dollar Coffee", "Crime");

       exam.grade(answerKey);
        
       Map<String, String> incorrectActual = Maps.newLinkedHashMap();
       incorrectActual.put("2", answerKey.getAnswer(2).toString());
       incorrectActual.put("3", answerKey.getAnswer(3).toString());
       
       List<Entry<String, String>> expected = new ArrayList<>(exam.getIncorrectFeedback());
       
       List<Entry<String, String>> incorrect =  new ArrayList<>(incorrectActual.entrySet());
      
       for (int i = 0; i < incorrect.size(); i++)
       {
           assertEquals(expected.get(i).getKey(), incorrect.get(i).getKey());  
           assertEquals(expected.get(i).getValue(), incorrect.get(i).getValue());
       }
       
       assertEquals(expected.size(), incorrect.size());
    }
}
