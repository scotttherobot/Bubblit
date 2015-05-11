package com.universalquantification.examgrader.models;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Exam represents an exam form. An Exam can have a map of answers or 
 * be empty. Additionally, an Exam can have a student record of the student 
 * it belongs to. An exam can also have a file containing the visual 
 * representation to be displayed to the user or in the scoring results. For 
 * convenience, an exam can also be printed.
 * 
 * 
 * @author CY Tan
 * @version 2.0
 */
public class Exam
{
    /**
     * A map of question numbers to answers.
     */
    private Map<Integer, Answer> answerMap;
    /**
     * The input page the exam was generated from.
     */
    private InputPage examFile; 
    /**
     * The student associated with the Exam.
     */
    private Student student;
    /**
     * Questions that were correct. This set will be null until grade()
     * is called.
     */
    private Set<Integer> correctQuestions;
    
     /**
     * Creates a new exam containing the given list and attached to a given 
     * student.
     *
     * @param answers A list of questions to be stored in the exam.
     * @param student The student this exam is to be attached to.
     * @param inputPage input page the exam was generate from
     */
    public Exam(ArrayList<Answer> answers, Student student, InputPage inputPage) 
    {
    } 
    
    /**
     * Returns the input page the exam was created from.
     * 
     * @return the input page the exam was created from
     */
    public InputPage getExamFile() 
    {
        return null;
    }
    
    /**
     * Returns the answer for the given question number.
     * @param question A number representing which numbered question to get
     * the answer for.
     * @return The answer for the given question number.
     */
    public Answer getAnswer(int question)
    {
        return null;
    }

    /**
     * Gets the student record for the exam.
     * 
     * @return The student record for the exam.
     */
    public Student getStudentRecord() 
    {
        return null;
    }
    
    
    
    /**
     * Returns whether or not a given question number was correct.
     * 
     * @param question A number representing which numbered answer to check.
     * @return A boolean representing whether or not the checked answer is
     * correct.
     */
    public boolean isQuestionCorrect(int question)
    {
        return false;
    }

    /**
     * Returns the set of correct answers from grading.
     * 
     * @return The set of correct answers from grading. 
     */
    public Set<Integer> getCorrectQuestions()
    {
        return null;
    }

     /**
     * Returns the set of incorrect answers from grading.
     * 
     * @return The set of incorrect answers from grading. 
     */
    public Set<Integer> getIncorrectQuestions()
    {
        return null;
    }
    
    
    /**
     * Returns a raw score representing the number of correct answers.
     * 
     * @throws IllegalStateException if the Exam has not been graded before
     * @return A raw score representing the number of correct answers.
     */
    public int getRawScore()
    {
        return 0;
    }
    
    /**
     * Returns a percent score representing the percentage of correct answers.
     * 
     * @throws IllegalStateException if the Exam has not been graded before
     * @return A percent score representing the percentage of correct answers.
     */
    public int getPercentScore() 
    {
        return 0;
    }
    
    /**
     * Grades an exam by comparing it with an answer key
     * @param answerKey 
     * @throws IllegalStateException if the method has been called before
     */
    public void grade(Exam answerKey)
    {
        
    }
}
