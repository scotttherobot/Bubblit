/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Describes an exam with a list of questions.
 *
 */
public class Exam 
{

    private HashMap<Integer, Question> qMap;
    private String studentName;
    private int studentID;
    
    /**
     * Create a new exam with given questions
     *
     * @param questions questions for the exam
     */
    public Exam(ArrayList<Question> questions) 
    {
        this.qMap = new HashMap<Integer, Question>();
        /* Add all questions to the question hash map.*/
        for (Question question : questions) 
        {
            this.qMap.put(question.getqNum(), question);
        }
    } /* Add all questions to the question hash map.*/


    /**
     * Grade an exam with a given key
     *
     * @param key list of questions with correct answers
     */
    public void grade(ArrayList<Question> key) 
    {
        /* check that all of the answers match the key. */
        for (Question question : key) 
        {
            //if this exam has that question answered, check its choice,
            // otherwise mark it wrong.
            if (this.qMap.containsKey(question.getqNum())) 
            {
                Question toGrade = this.qMap.get(question.getqNum());
                toGrade.setCorrect(toGrade.getChoices().equals(question.getChoices()));
                this.qMap.put(question.getqNum(), toGrade);
            } 
            else 
            {
                Question blank = new Question();
                blank.setCorrect(false);
                blank.setqNum(question.getqNum());
                this.qMap.put(question.getqNum(), blank);
            }
        }
    }
    
    public int rawScore() {
        int raw = 0;
        
        for (Entry<Integer,Question> e : this.qMap.entrySet()) {
            Integer num = e.getKey();
            Question q = e.getValue();
            
            if (q.isCorrect()) {
                raw++;
            }
        }
        
        return raw;
    }

    public double percentCorrect() {
        return ((double)this.rawScore() / 100.0) * 100;
    }
    
    /**
     * Get the list of questions for this exam.
     *
     * @return the list of questions
     */
    public ArrayList<Question> getAnswers() 
    {
        return new ArrayList<Question>(this.qMap.values());
    }

}
