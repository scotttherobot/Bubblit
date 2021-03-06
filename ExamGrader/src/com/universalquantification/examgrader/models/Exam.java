package com.universalquantification.examgrader.models;

import com.universalquantification.examgrader.utils.PreferencesManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64OutputStream;

/**
 * Exam represents an exam form. An Exam can have a map of answers or be empty.
 * Additionally, an Exam can have a student record of the student it belongs to.
 * An exam can also have a file containing the visual representation to be
 * displayed to the user or in the scoring results. For convenience, an exam can
 * also be printed.
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
     * Questions that were correct. This set will be null until grade() is
     * called.
     */
    private Set<Integer> correctQuestions;

    /**
     * Questions that were incorrect. This set will be null until grade() is
     * called.
     */
    private Set<Integer> incorrectQuestions;

    /**
     * The map of correct answers this exam was graded against.
     */
    private Map<Integer, Answer> correctAnswerMap;

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

        // add all the answers to the exam
        for (Answer answer : answers)
        {
            // on add the answer if it is not empty.
            if (!answer.isEmpty())
            {
                answerMap.put(answer.getNumber(), answer);
            }
        }
    }

    /**
     * Get the filename that this exam appears in.
     * @return the filename.
     */
    public String getExamFileName()
    {
        return examFile.getFile().getName();
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
     * Returns a Base64 encoded PNG of the input file. Adapted from
     * http://stackoverflow.com/a/7179113
     *
     * @return a String that is the Base64 encoded page image.
     */
    public String getExamImageB64()
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        OutputStream b64 = new Base64OutputStream(os);
        String result = "";
        try
        {
            ImageIO.write(getExamFile().getBufferedImage(), "png", b64);
            result = os.toString("UTF-8");
        }
        catch (IOException ex)
        {
            Logger.getLogger(Exam.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Returns a Base64 encoded PNG of the name box from the input file. Adapted
     * from http://stackoverflow.com/a/7179113
     *
     * @return a String that is the Base64 encoded name field image.
     */
    public String getNameImageB64()
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        OutputStream b64 = new Base64OutputStream(os);
        String result = "";
        try
        {
            ImageIO.write(getExamFile().getMetaBufferedImage(), "png", b64);
            result = os.toString("UTF-8");
        }
        catch (IOException ex)
        {
            Logger.getLogger(Exam.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Returns the answer for the given question number.
     *
     * @param question A number representing which numbered question to get the
     * answer for.
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

        Answer answer = answerMap.get(question);
        // make sure we got an answer then return its bubbles.
        if (answer == null)
        {
            return new Answer(new ArrayList<Bubble>(), question);
        }
        return answer;
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
     * correct, or null if the question was not graded.
     */
    public Boolean isQuestionCorrect(int question)
    {
        // IF correctQuestions contains question
        // RETURN true
        // ELSE
        // RETURN false
        // ENDIF

        return correctQuestions.contains(question);
    }

    /**
     * Get a list of all responses to every question.
     * @return the set of all responses mapping question number to response.
     */
    public Set<Map.Entry<String, String>> getAllResponses()
    {
        Map<String, String> feedback = new LinkedHashMap<String, String>();
        //List<String> feedback = new ArrayList<String>();

        
        ArrayList<Integer> questions = new ArrayList<Integer>(correctAnswerMap.keySet());
        Collections.sort(questions);
        // go through every correct question and generate feedback for it.
        for (Integer onQ : questions)
        {
            StringBuilder fbStr = new StringBuilder();
            
            Answer thisAnswer = (Answer)answerMap.get(onQ);
            // GET THE ANSWER FROM THE CORRECT ANSWER MAP
            if (thisAnswer != null) 
            {
                fbStr.append(thisAnswer.toString());
            }
            
            // check if the question is correct and add correctness if it
            // was set in the preferences.
            if (!isQuestionCorrect(onQ) && 
                (boolean)PreferencesManager.getInstance().get("show-correct-answers"))
            {
                Answer correctAnswer = (Answer)correctAnswerMap.get(onQ);
                fbStr.append(" (");
                fbStr.append(correctAnswer.toString());
                fbStr.append(")");
            }
            
            feedback.put(onQ.toString(), fbStr.toString());
        }

        return feedback.entrySet();
    }

    /**
     * Returns a list of the correct answers for the incorrect responses.
     *
     * @return a list of strings containing the question number and the correct
     * answer to that question.
     */
    public Set<Map.Entry<String, String>> getIncorrectFeedback()
    {
        Map<String, String> feedback = new LinkedHashMap<String, String>();
        //List<String> feedback = new ArrayList<String>();

        // go through every correct question and generate feedback for it.
        for (Integer onQ : getIncorrectQuestions())
        {
            StringBuilder fbStr = new StringBuilder();

            // GET THE ANSWER FROM THE CORRECT ANSWER MAP
            Answer correctAnswer = (Answer) correctAnswerMap.get(onQ);
            fbStr.append(correctAnswer.toString());

            feedback.put("" + onQ, fbStr.toString());
        }

        return feedback.entrySet();
    }

    /**
     * Returns a list of the correct answers for the incorrect responses.
     *
     * @return a list of strings containing the question number and the correct
     * answer to that question.
     */
    public Set<Map.Entry<String, String>> getCorrectFeedback()
    {
        Map<String, String> feedback = new LinkedHashMap<String, String>();

        // go through every correct question and generate feedback for it.
        for (Integer onQ : getCorrectQuestions())
        {
            StringBuilder fbStr = new StringBuilder();

            fbStr.append(getAnswer(onQ).toString());

            feedback.put("" + onQ, fbStr.toString());
        }

        return feedback.entrySet();
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
     * Returns all the questions.
     *
     * @return a set of all the questions.
     */
    public Set<Integer> getQuestions()
    {
        // RETURN keys of the answerMap
        return answerMap.keySet();
    }

    /**
     * Returns the set of incorrect answers from grading.
     *
     * @return The set of incorrect answers from grading.
     */
    public Set<Integer> getIncorrectQuestions()
    {
        return incorrectQuestions;
    }

    /**
     * Returns the set of incorrect responses as an array
     *
     * @return an array of the incorrect responses.
     */
    public Object[] getIncorrectResponses()
    {
        return getIncorrectQuestions().toArray();
    }

    /**
     * Returns the set of correct responses as an array
     *
     * @return an array of the correct responses.
     */
    public Object[] getCorrectResponses()
    {
        return getCorrectQuestions().toArray();
    }

    /**
     * Returns the count of questions in the exam.
     *
     * @return A integer representing the number of questions in the exam.
     */
    public int getQuestionCount()
    {
        return answerMap.size();
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
    public double getPercentScore()
    {
        // CALL getRawScore RETURNING rawScore
        // CALL size on answerMap RETURNING questionCount
        // 
        // SET percentScore to rawScore divided by questionCount
        //
        // RETURN percentScore
        return 1.0 * correctQuestions.size() / (correctQuestions.size()
            + incorrectQuestions.size());
    }

    /**
     * Returns the max possible score. IE how many questions were bubbled on the
     * key, the grading basis, etc.
     *
     * @return the maximum score
     */
    public int getMaxScore()
    {
        return correctQuestions.size() + incorrectQuestions.size();
    }

    /**
     * Returns a formatted string of the score percentage.
     *
     * @return A string formatted to two decimal places representing the
     * percentage score.
     */
    public String getFormattedPercentScore()
    {
        DecimalFormat percent = new DecimalFormat("#.00");
        return percent.format(getPercentScore() * 100.00).toString();
    }

    /**
     * Grades an exam by comparing it with an answer key
     *
     * @param answerKey the exam to grade against.
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

        this.correctAnswerMap = answerKey.answerMap;

        correctQuestions = new TreeSet<Integer>();
        incorrectQuestions = new TreeSet<Integer>();

        // go through every question in our set and check if its correct.
        for (Map.Entry pair : correctAnswerMap.entrySet())
        {
            int questionNumber = (int) pair.getKey();
            Answer thisAnswer = answerMap.get(questionNumber);
            Answer correctAnswer = (Answer) pair.getValue();

            // check for correctness of question
            if (thisAnswer != null && thisAnswer.equals(correctAnswer))
            {
                correctQuestions.add(questionNumber);
            }
            else
            {
                incorrectQuestions.add(questionNumber);
            }
        }
    }

    /**
     * Get the string representation for this exam.
     *
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
        for (Integer qNum : this.answerMap.keySet())
        {
            sb.append("\t");
            sb.append(this.answerMap.get(qNum).toString());
            
            // check that we have questions to display
            if (this.correctQuestions != null)
            {
                // check that the question is correct
                if(this.isQuestionCorrect(qNum))
                {
                    sb.append(" correct");
                }
                else
                {
                    sb.append(" inorrect");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
