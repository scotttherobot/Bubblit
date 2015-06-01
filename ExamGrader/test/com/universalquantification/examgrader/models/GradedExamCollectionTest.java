package com.universalquantification.examgrader.models;

import java.util.LinkedHashMap;
import java.util.TreeSet;
import java.util.Set;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static com.universalquantification.examgrader.helpers.ExamHelper.createExam;

/**
 *
 * @author Jenny Wang
 */
public class GradedExamCollectionTest extends TestCase {
    
    
    private Exam answerKey;
    private List<Exam> studentExams;
    private GradedExamCollection gradedExamCollection;
    
    public GradedExamCollectionTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
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
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    /**
     * Test of getAnswerKey method, of class GradedExamCollection.
     */
    public void testGetAnswerKey() {
        assertEquals(gradedExamCollection.getAnswerKey(), answerKey);
        
     
    }

    /**
     * Test of get method, of class GradedExamCollection.
     */
    public void testGet() {
        assertEquals(gradedExamCollection.get(0), studentExams.get(0));
    }

    /**
     * Test of getQuestions method, of class GradedExamCollection.
     */
    public void testGetQuestions() {
        assertEquals(gradedExamCollection.getQuestions(),
            new HashSet<>(Arrays.asList(1, 2, 3, 4)));
    }

    /**
     * Test of getNumExams method, of class GradedExamCollection.
     */
    public void testGetNumExams() {
        assertEquals(gradedExamCollection.getNumExams(), studentExams.size());
    }

    /**
     * Test of getAverageScore method, of class GradedExamCollection.
     */
    public void testGetAverageScore() {
        
        assertEquals(gradedExamCollection.getAverageScore(), 5.0 /
                studentExams.size());
    }

    /**
     * Test of getStdDeviation method, of class GradedExamCollection.
     */
    public void testGetStdDeviation() {
       assertEquals(gradedExamCollection.getStdDeviation(), 2.0817, 0.001);
    }

    /**
     * Test of getAnswerFrequency method, of class GradedExamCollection.
     */
    public void testGetQuestionMissCounts() {
        Set<Map.Entry<String, String>> result = gradedExamCollection.getQuestionMissCounts();
        
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("1", "2");
        expected.put("2", "2");
        expected.put("3", "2");
        expected.put("4", "1");
        
        
        assertEquals(expected.entrySet(), result);
    }

    /**
     * Test of hashCode method, of class GradedExamCollection.
     */
    public void testHashCode() {
        assertNotNull(gradedExamCollection.hashCode());
    }

    /**
     * Test of equals method, of class GradedExamCollection.
     */
    public void testEquals() {
        Exam answerKey2 = createExam(Arrays.asList(0x0001, 0x0010, 0x1111, 0x1111),
                "answer", "key");
        
        List<Exam> studentExams2 = Arrays.asList(
            createExam(Arrays.asList(0x1000, 0x1000, 0x1000, 0x1000), "Exam", "Taker"),
            createExam(new ArrayList<Integer>(), "Exam", "Taker2"),
            createExam(Arrays.asList(0x0001, 0x0010, 0x0100, 0x1000), "Exam", "Taker 3")
        );
        
        for (Exam exam: studentExams2)
        {
            exam.grade(answerKey2);
        }
        
        GradedExamCollection gradedExamCollection2 = new
                GradedExamCollection(answerKey2, studentExams2);
        
        assertEquals(gradedExamCollection, gradedExamCollection);
        assertFalse(gradedExamCollection.equals(gradedExamCollection2));
    }

    /**
     * Test of toString method, of class GradedExamCollection.
     */
    public void testToString() {
        assertNotNull(gradedExamCollection.toString());
    }
}
