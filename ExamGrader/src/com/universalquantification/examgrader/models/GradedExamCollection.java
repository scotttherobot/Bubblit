package com.universalquantification.examgrader.models;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A GradedExamCollection is a collection of exams graded by
 * the same answer key. Each GradedExamCollection contains both
 * the answer key that was used to grade the exam and the graded
 * exams themselves. There must always be at least one exam in a
 * GradedExamCollection.
 * 
 * The GradedExamCollection can report the standard deviation and
 * average of graded scores and the frequency of questions. In addition,
 * it is possible to fetch an individual graded exam from the
 * GradedExamCollection.
 * 
 * @author Jenny Wang
 * @version 2.0
 */
public class GradedExamCollection
{
    
    /**
     * Answer key of the collection.
     */
    private Exam answerKey;
    /**
     * Contains a list of Exams that were graded.
     * The original order of the exams passed
     * in is preserved.
     */
    private List<Exam> studentExams;

    /**
     * Creates a GradedExamCollection in which each exam
     * in the list is scored by the answer key.
     * 
     * @param answerKey Exam the Exam to score other Exams with
     * @param exams List a list of Exams to be scored and saved in the
     * collection
     * @throws IllegalArgumentError if exams is empty
     */
    public GradedExamCollection(Exam answerKey, List<Exam> exams)
    {
    }

    /**
     * This returns an Exam containing correct answers for the GradedExamCollection.
     * @return an Exam containing correct answers for the GradedExamCollection
     */
    public Exam getAnswerKey()
    {
        return null;
    }
    
    /**
     * Returns the nth Exam in the collection. A GradedExam will have the 
     * same position as the Exam it was scored from in the list that was passed
     * in the constructor.
     * @param n int 0-indexed integer representing the position of the GradedExam
     * @return the nth Exam
     * @throws IndexOutOfBoundsException if there is no nth GradedExam
     */
    public Exam get(int n)
    {
        return null;
    }

    /**
     * Returns all question numbers that appear in the answer key.
     * @return set of all question numbers appearing in the answer key
     */
    public Set<Integer> getQuestions()
    {
        return null;
    }

    /**
     * Returns the number of exams in the collection.
     * @return the number of exams graded
     */
    public int getNumExams()
    {
        return 0;
    }

    /**
     * Returns the average score of exams in the collection.
     * @return average score of exams in the collection
     */
    public double getAverageScore()
    {
        return 0;
    }

    /**
     * Returns the standard deviation of scores of exams in the collection.
     * @return average standard deviation of exam scores
     */
    public double getStdDeviation()
    {
        return 0;
    }

    /**
     * Returns a map mapping every answer that appeared more than once in
     * the graded exams to the number of times they occurred in the graded 
     * exams.
     * 
     * @param question the question number
     * @return a map of Answer to their frequency
     */
    public Map<Answer, Integer> getAnswerFrequency(int question)
    {
        return null;
    }

    /**
     * Returns a hash code for the GradedExamCollection
     * @return a hash code for the GradedExamCollection
     */
    @Override
    public int hashCode()
    {
        return -1;
    }
    
    /**
     * Checks that the other object is equal to this.
     * @param o object to compare against
     */
    @Override
    public boolean equals(Object o)
    {
        return false;
    }
    
    /**
    * Returns a string representation of a GradedExamCollection
    * @return string representation of a GradedExamCollection
    */
    @Override
    public String toString()
    {
        return null;
    }
    
}
