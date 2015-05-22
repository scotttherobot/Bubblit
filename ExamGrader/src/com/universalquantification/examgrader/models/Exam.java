package com.universalquantification.examgrader.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
    private final Map<Integer, Answer> answerMap;
    /**
     * The input page the exam was generated from.
     */
    private final InputPage examFile; 
    /**
     * The student associated with the Exam.
     */
    private final Student student;
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
        // SET the answers field to answers
        // SET the student field to student
        // SET examFile to inputPage
        
        this.student = student;
        this.examFile = inputPage;
        
        answerMap = new HashMap();
        
        for (Answer a : answers) {
            answerMap.put(a.getNumber(), a);
        }
    } 
    
    /**
     * Returns the input page the exam was created from.
     * 
     * @return the input page the exam was created from
     */
    public InputPage getExamFile() 
    {
        // RETURN examFile
        
        return examFile;
    }
    
    /**
     * Returns the answer for the given question number.
     * @param question A number representing which numbered question to get
     * the answer for.
     * @return The answer for the given question number.
     */
    public Answer getAnswer(int question)
    {
        // BEGIN 
            // READ answer from answerMap for the key question as readAnswer
            //
            // RETURN readAnswer
        // EXCEPTION 
            // CALL error with "Answer for given number not found on exam."
        // END
        
        Answer a = answerMap.get(question);
        
        if (a == null) {
            throw new IllegalArgumentException("No such numbered question on this exam.");
        }
        
        return a;
    }

    /**
     * Gets the student record for the exam.
     * 
     * @return The student record for the exam.
     */
    public Student getStudentRecord() 
    {
        // RETURN student
        
        return student;
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
        // IF correctQuestions contains question
            // RETURN true
        // ELSE
            // RETURN false
        // ENDIF
        
        Answer a = getAnswer(question);
        int questionNumber = a.getNumber();
        
        for (Integer i : correctQuestions) {
            if (i == questionNumber) return true;
        }
        
        return false;
    }

    /**
     * Returns the set of correct answers from grading.
     * 
     * @return The set of correct answers from grading. 
     */
    public Set<Integer> getCorrectQuestions()
    {
        // RETURN correctQuestions
        
        return correctQuestions;
    }

     /**
     * Returns the set of incorrect answers from grading.
     * 
     * @return The set of incorrect answers from grading. 
     */
    public Set<Integer> getIncorrectQuestions()
    {
        // INIT incorrectQuestions as a set
        // INIT iterator as an iterator for answerMap
        // 
        // WHILE iterator.hasNext() is true
            // CALL next on iterator RETURNING entry
            // IF correctQuestions does not contain the key for entry
            // CALL add on incorrectQuestions with the key for entry
        // ENDWHILE
        //
        // RETURN incorrectQuestions
        
        Set<Integer> incorrectQuestions = new HashSet<>();
        
        for (Map.Entry pair : answerMap.entrySet()) {
            int questionNumber = (int) pair.getKey();
            
            if (!isQuestionCorrect(questionNumber)) {
                incorrectQuestions.add(questionNumber);
            }
        }

        return incorrectQuestions;
    }
    
    
    /**
     * Returns a raw score representing the number of correct answers.
     * 
     * @throws IllegalStateException if the Exam has not been graded before
     * @return A raw score representing the number of correct answers.
     */
    public int getRawScore()
    {
        // CALL size on correctQuestions RETURNING score
        // RETURN score
        
        return correctQuestions.size();
    }
    
    /**
     * Returns a percent score representing the percentage of correct answers.
     * 
     * @throws IllegalStateException if the Exam has not been graded before
     * @return A percent score representing the percentage of correct answers.
     */
    public int getPercentScore() 
    {
        // CALL getRawScore RETURNING rawScore
        // CALL size on answerMap RETURNING questionCount
        // 
        // SET percentScore to rawScore divided by questionCount
        //
        // RETURN percentScore
        
        return (correctQuestions.size() / answerMap.size());
    }
    
    /**
     * Grades an exam by comparing it with an answer key
     * @param answerKey 
     * @throws IllegalStateException if the method has been called before
     */
    public void grade(Exam answerKey)
    {
        // READ answerMap for answerKey as answerKey
        // INIT iterator as an iterator for answerKey
        // 
        // WHILE iterator.hasNext() is true
            // CALL next on iterator RETURNING entry
            // SET key to the key for entry
            // CALL containsKey on answerMap with key RETURNING hasKey
            //
            // IF hasKey
                // SET thisAnswer as the Answer in answerMap
                // SET correctAnswer as the Answer in answerKey
                // 
                // CALL equals on thisAnswer with correctAnswer RETURNING isRight
                // 
                // IF isRight AND correctQuestions does not contain key
                    // CALL add on correctQuestions with key
            // ENDIF
        // ENDWHILE
        
        Map<Integer, Answer> correctAnswerMap = answerKey.answerMap;
        
        correctQuestions = new HashSet<>();
        
        for (Map.Entry pair : correctAnswerMap.entrySet()) {
            int questionNumber = (int) pair.getKey();
            Answer thisAnswer = answerMap.get(questionNumber);
            Answer otherAnswer;
            
            if (thisAnswer != null) {
                otherAnswer = (Answer) pair.getValue();
                
                if (thisAnswer.equals(otherAnswer)) {
                    correctQuestions.add(questionNumber);
                }
            }
        }
    }
    
    /**
     * Get the string representation for this exam.
     * @return the string representation
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Exam\n");
        sb.append("Student: ");
        sb.append(this.student.toString());
        sb.append("\nQuestions:\n");
        // Append the question string to the string
        for(Integer qNum : this.answerMap.keySet())
        {
            sb.append("\t");
            sb.append(this.answerMap.get(qNum).toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
