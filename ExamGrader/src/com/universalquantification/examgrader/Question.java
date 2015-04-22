/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

import java.util.HashSet;


/**
 * Describes a single question on an exam.
 *
 */
public class Question implements Comparable<Question>
{
    private int qNum;
    private HashSet<String> choices;
    private boolean correct;

    /**
     * Get the question number
     * @return question number
     */
    public int getqNum()
    {
        return qNum;
    }

    /**
     * Set the question number
     * @param num the number for the question
     */
    public void setqNum(int num)
    {
        this.qNum = num;
    }

    /**
     * Get the choice (a,b,c,d) for the question
     * @return choice
     */
    public HashSet getChoices()
    {
        return choices;
    }

    /**
     * Set the choice for the question
     * @param choice letter to set
     */
    public void addChoice(String choice)
    {
        this.choices.add(choice);
    }

    /**
     * check if the question is correct.
     * @return if its correct
     */
    public boolean isCorrect()
    {
        return correct;
    }

    /**
     * Set correct
     * @param correct is the question correct?
     */
    public void setCorrect(boolean correct)
    {
        this.correct = correct;
    }

    /**
     * Compare two questions
     * @param question the other question
     * @return whether two questions are equal
     */
    @Override
    public int compareTo(Question question)
    {
        return this.qNum - question.qNum;
    }
    
    public Question()
    {
        this.choices = new HashSet<String>();
    }
}
