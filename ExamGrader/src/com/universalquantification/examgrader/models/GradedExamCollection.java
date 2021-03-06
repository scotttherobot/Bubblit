package com.universalquantification.examgrader.models;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * A GradedExamCollection is a collection of exams graded by the same answer
 * key. Each GradedExamCollection contains both the answer key that was used to
 * grade the exam and the graded exams themselves. There must always be at least
 * one exam in a GradedExamCollection.
 *
 * The GradedExamCollection can report the standard deviation and average of
 * graded scores and the frequency of questions. In addition, it is possible to
 * fetch an individual graded exam from the GradedExamCollection.
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
     * Contains a list of Exams that were graded. The original order of the
     * exams passed in is preserved.
     */
    private List<Exam> studentExams;

    /**
     * Creates a GradedExamCollection in which each exam in the list has been
     * scored by the answer key.
     *
     * @param answerKey Exam the Exam to score other Exams with
     * @param exams List a list of Exams to be scored and saved in the
     * collection
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
     * Get a list of all the {@link Student}s in this exam collection.
     * @return list of students.
     */
    public List<Student> getAllStudents()
    {
        List<Student> students = new ArrayList<Student>();
        // iterate over exam to get the students
        for (Exam exam : studentExams)
        {
            students.add(exam.getStudentRecord());
        }

        return students;
    }

    /**
     * This returns an Exam containing correct answers for the
     * GradedExamCollection.
     *
     * @return an Exam containing correct answers for the GradedExamCollection
     */
    public Exam getAnswerKey()
    {
        // RETURN answerKey
        return answerKey;
    }

    /**
     * Returns the nth Exam in the collection. A GradedExam will have the same
     * position as the Exam it was scored from in the list that was passed in
     * the constructor.
     *
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
     *
     * @return set of all question numbers appearing in the answer key
     */
    public Set<Integer> getQuestions()
    {
        // CALL getQuestions answerKey
        return answerKey.getQuestions();
    }

    /**
     * Returns all graded exams.
     *
     * @return set of all exams in the collection.
     */
    public List<Exam> getGradedExams()
    {
        return studentExams;
    }

    /**
     * Returns the number of exams in the collection.
     *
     * @return the number of exams graded
     */
    public int getNumExams()
    {
        // CALL size studentExams
        return studentExams.size();
    }

    /**
     * Returns the average score of exams in the collection.
     *
     * @return average score of exams in the collection
     */
    public double getAverageScore()
    {
        int sum = 0;
        // SET sum to 0
        // FOR exam in studentExams
        for (Exam exam : studentExams)
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
     * Returns the average percentage score of exams in the collection.
     *
     * @return average percentage score of exams in the collection
     */
    public double getAveragePercentage()
    {
        return getAverageScore() / answerKey.getQuestionCount();
    }

    /**
     * Returns a formatted string from the passed double.
     *
     * @return A string formatted to two decimal places
     */
    private String formatDecimal(double dec)
    {
        DecimalFormat percent = new DecimalFormat("#.00");
        return percent.format(dec).toString();
    }

    /**
     * Returns a formatted string from the average percentage
     *
     * @return a string formatted to two decimals containing the average
     * percentage
     */
    public String getAvgPercentString()
    {
        return formatDecimal(getAveragePercentage() * 100.00);
    }

    /**
     * Returns a formatted string from the average raw score
     *
     * @return a string formatted to two decimals containing the average raw
     * score
     */
    public String getAvgRawString()
    {
        return formatDecimal(getAverageScore());
    }

    /**
     * Returns a formatted string of the standard deviation
     *
     * @return a string formatted to two decimals containing the std dev
     */
    public String getStdDevString()
    {
        return formatDecimal(getStdDeviation());
    }

    /**
     * Returns the standard deviation of scores of exams in the collection.
     *
     * @return average standard deviation of exam scores
     */
    public double getStdDeviation()
    {
        // CALL getAverageScore RETURNING averageScore 
        double averageScore = getAverageScore();
        // SET sumOfSquares to 0
        double sumOfSquares = 0;

        // FOR exam in exams
        for (Exam exam : studentExams)
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
     * Get the number of times a given question was missed.
     * @param question the question number
     * @return the number of times the question was missed.
     */
    private int getMisses(int question)
    {
        int missed = 0;

        // iterate over every exam and check the question.
        for (Exam exam : studentExams)
        {
            // if the question was missed, add to the count.
            if (!exam.isQuestionCorrect(question))
            {
                missed++;
            }
        }
        return missed;
    }

    /**
     * Get the statistics of missed questions.
     * @return A map from questions to the number of misses.
     */
    public Set<Map.Entry<String, String>> getQuestionMissCounts()
    {
        Map<String, String> feedback = new LinkedHashMap<String, String>();

        Exam key = getAnswerKey();
        Set<Integer> questions = new TreeSet<Integer>(key.getQuestions());

        // iterate over question and check the number of misses.
        for (Integer question : questions)
        {
            feedback.put(String.valueOf(question), String.valueOf(getMisses(
                question)));
        }

        return feedback.entrySet();
    }

    /**
     * Returns a hash code for the GradedExamCollection
     *
     * @return a hash code for the GradedExamCollection
     */
    @Override
    public int hashCode()
    {
        return studentExams.hashCode() + 31 * answerKey.hashCode();
    }

    /**
     * Checks that the other object is equal to this.
     *
     * @param o object to compare against
     * @return whether they're equal or not.
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
            GradedExamCollection coll = (GradedExamCollection) o;
            return answerKey.equals(coll.answerKey) && studentExams.equals(
                coll.studentExams);
        }
    }

    /**
     * Returns a string representation of a GradedExamCollection
     *
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
