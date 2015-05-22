package com.universalquantification.examgrader.models;

import java.util.HashMap;
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
     * in the list has been scored by the answer key.
     * 
     * @param answerKey Exam the Exam to score other Exams with
     * @param exams List a list of Exams to be scored and saved in the
     * collection
     * @throws IllegalArgumentError if exams is empty
     * @pre exams have been graded
     */
    public GradedExamCollection(Exam answerKey, List<Exam> exams)
    {
        // IF exams is empty
            // RAISE IllegalArgumentException
        if (exams.isEmpty())
        {
            throw new IllegalArgumentException("No exams passed in.");
        }
        
        this.answerKey = answerKey;
        this.studentExams = exams;
    }

    /**
     * This returns an Exam containing correct answers for the GradedExamCollection.
     * @return an Exam containing correct answers for the GradedExamCollection
     */
    public Exam getAnswerKey()
    {
        // RETURN answerKey
        return answerKey;
    }
    
    /**
     * Returns the nth Exam in the collection. A GradedExam will have the 
     * same position as the Exam it was scored from in the list that was passed
     * in the constructor.
     * @param n 0-indexed integer representing the position of the GradedExam
     * @return the nth Exam
     * @throws IndexOutOfBoundsException if there is no nth GradedExam
     */
    public Exam get(int n)
    {
        // CALL get studentExams WITH n
        return studentExams.get(n);
    }

    /**
     * Returns all question numbers that appear in the answer key.
     * @return set of all question numbers appearing in the answer key
     */
    public Set<Integer> getQuestions()
    {
        // CALL getQuestions answerKey
        return answerKey.getQuestions();
    }

    /**
     * Returns the number of exams in the collection.
     * @return the number of exams graded
     */
    public int getNumExams()
    {
        // CALL size studentExams
        return studentExams.size();
    }

    /**
     * Returns the average score of exams in the collection.
     * @return average score of exams in the collection
     */
    public double getAverageScore()
    {
        int sum = 0;
        // SET sum to 0
        // FOR exam in studentExams
        for (Exam exam: studentExams)
        {
            // CALL getRawScore exam RETURNING examScore
            // ADD examScore TO sum
            sum += exam.getRawScore();
        }
        // END FOR
        
        // RETURN sum divided by number of exams
        return 1.0 * sum / getNumExams();
    }

    /**
     * Returns the standard deviation of scores of exams in the collection.
     * @return average standard deviation of exam scores
     */
    public double getStdDeviation()
    {
        // CALL getAverageScore RETURNING averageScore 
        double averageScore = getAverageScore();
        // SET sumOfSquares to 0
        double sumOfSquares = 0;
        
        // FOR exam in exams
        for (Exam exam: studentExams)
        {
            // CALL getRawScore exam RETURNING examScore
            int examScore = exam.getRawScore();
            
            // SET difference to the difference between examScore and
                // averageScore
            double difference = examScore - averageScore;
            
            // ADD difference squared to sumOfSquares     
            sumOfSquares += Math.pow(difference, 2);   
        }
            // END FOR
        
        // RETURN the square root of sumOfSquares divided by one less than
        // the number of exams 
        
        return Math.sqrt(sumOfSquares / (getNumExams() - 1));
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
        
        // INIT frequencies map
        Map<Answer, Integer> frequencies = new HashMap<>();
        
        // FOR exam in studentExams
        for (Exam exam: studentExams)
        {
            // CALL getAnswer exam WITH question RETURNING answer
            Answer answer = exam.getAnswer(question);
            
            // IF frequencies does not contain a key for answer
            if (!frequencies.containsKey(answer))
            {
                // SET frequencies[answer] to 1
                frequencies.put(answer, 1);
            }
            // ELSE
            else
            {
                // SET frequencies[answer] to frequencies[answer] + 1
                frequencies.put(answer, frequencies.get(answer) + 1);
            }
            // END IF
        // END FOR
        }
        // RETURN frequencies
        return frequencies;
    }

    /**
     * Returns a hash code for the GradedExamCollection
     * @return a hash code for the GradedExamCollection
     */
    @Override
    public int hashCode()
    {
        return studentExams.hashCode() + 31 * answerKey.hashCode();
    }
    
    /**
     * Checks that the other object is equal to this.
     * @param o object to compare against
     */
    @Override
    public boolean equals(Object o)
    {
        // IF other is null
        if (o == null)
        {
            // RETURN false
            return false;
        }
        // ELSE IF other is not a GradedExamCollection
        else if (!(o instanceof GradedExamCollection))
        {
            // RETURN FALSE
            return false;
        }
        // ELSE
        else
        {
            // RETURN true iff this object's answerKey is equal to other's
                // AND this object's studentExams are equal to other's
            GradedExamCollection c = (GradedExamCollection) o;
            return answerKey.equals(c.answerKey) &&
                    studentExams.equals(c.studentExams) ;
        }
    }
    
    /**
    * Returns a string representation of a GradedExamCollection
    * @return string representation of a GradedExamCollection
    */
    @Override
    public String toString()
    {
        // RETURN 'GradedExamCollection(' concatenated with answerKeyOutput
            // concatenated with studentExams concatenated with ')'
        return "GradedExamCollection(" + answerKey + ", " + studentExams + ")";
    }
    
}
