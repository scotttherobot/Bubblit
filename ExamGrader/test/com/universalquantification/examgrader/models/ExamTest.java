package com.universalquantification.examgrader.models;

import java.util.HashSet;
import java.util.Arrays;
import junit.framework.TestCase;

import static com.universalquantification.examgrader.helpers.ExamHelper.createExam;

/**
 *
 * @author Jenny Wang
 */
public class ExamTest extends TestCase {

    public ExamTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSetup() {
    }

    public void testFailedExam() {

        Exam answerKey = createExam(Arrays.asList(0b1000, 0b1000, 0b1000, 0b1000), "1/4", "Correct");
        Exam exam = createExam(Arrays.asList(0b0001, 0b0010, 0b0100, 0b0000), "All", "Empty");

        exam.grade(answerKey);

        assertEquals(exam.getRawScore(), 0);

        assertTrue(exam.getCorrectQuestions().isEmpty());
    }

    public void testExam() {

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

    public void testGetQuestions() {
        Exam exam = createExam(Arrays.asList(0b1000, 0b1000, 0b0100, 0b0000), "Great", "Student");

        assertEquals(exam.getQuestions(), new HashSet<>(Arrays.asList(1, 2, 3, 4)));
    }

}
