package com.universalquantification.examgrader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.universalquantification.examgrader.StatsReporter.Column;
import junit.framework.*;

import static com.universalquantification.examgrader.StatsReporter.Column.*;

/**
 *
 * @author jenwang
 */
public class StatsReporterTest extends TestCase {

    public StatsReporterTest(String testName) {
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

    public void testQuestionWithMultipleMostCommonFailedResponse() throws Exception {
        ArrayList<Question> answers = buildQuestions(Arrays.asList("A"));

        List<Exam> students = Arrays.asList(
                buildExam(1, Arrays.asList("B"), answers),
                buildExam(2, Arrays.asList("C"), answers),
                buildExam(3, Arrays.asList(""), answers));

        Report report = new StatsReporter().getReport(students, answers);

        String[] question = report.rows.get(1);

        System.out.println(Arrays.toString(question));
        assertEquals(question[MOST_COMMON_FAILED_RESPONSE.ordinal()], "BLANK B C");
    }

    public void testQuestionWithNoFailedResponse() throws Exception {
        ArrayList<Question> answers = buildQuestions(Arrays.asList("A"));

        List<Exam> students = Arrays.asList(
                buildExam(1, Arrays.asList("A"), answers));

        Report report = new StatsReporter().getReport(students, answers);

        String[] question = report.rows.get(1);

        ArrayList<String> expectedColumns = new ArrayList<>();

        for (Column c : Column.values()) {
            expectedColumns.add(c.toString());
        }

        assertEquals(expectedColumns, Arrays.asList(report.headers));
        assertEquals(question[MOST_COMMON_FAILED_RESPONSE.ordinal()], "-");
        assertEquals(question[FREQUENCY_OF_MISSED_QUESTION.ordinal()], "0");
    }

    public void testQuestionReport() throws Exception {
        ArrayList<Question> answers = buildQuestions(Arrays.asList("A"));

        List<Exam> students = Arrays.asList(
                buildExam(1, Arrays.asList("C"), answers),
                buildExam(2, Arrays.asList("B"), answers),
                buildExam(3, Arrays.asList("AB"), answers),
                buildExam(3, Arrays.asList("AB"), answers),
                buildExam(4, Arrays.asList(""), answers));

        Report report = new StatsReporter().getReport(students, answers);

        String[] question = report.rows.get(1);

        ArrayList<String> expectedColumns = new ArrayList<>();

        for (Column c : Column.values()) {
            expectedColumns.add(c.toString());
        }

        expectedColumns.add(String.format(StatsReporter.NUMBER_OF_FAILED_RESPONSES_FORMAT, "BLANK"));
        expectedColumns.add(String.format(StatsReporter.NUMBER_OF_FAILED_RESPONSES_FORMAT, "B"));
        expectedColumns.add(String.format(StatsReporter.NUMBER_OF_FAILED_RESPONSES_FORMAT, "C"));
        expectedColumns.add(String.format(StatsReporter.NUMBER_OF_FAILED_RESPONSES_FORMAT, "AB"));

        assertEquals(expectedColumns, Arrays.asList(report.headers));
        assertEquals(question[MOST_COMMON_FAILED_RESPONSE.ordinal()], "AB");
        assertEquals(question[question.length - 1], "2");
        assertEquals(question[question.length - 2], "1");
        assertEquals(question[question.length - 3], "1");
        assertEquals(question[question.length - 4], "1");

        assertEquals(question[FREQUENCY_OF_MISSED_QUESTION.ordinal()], "5");

    }

    // public void test report for question - use a multianswer
    public void testWithAnswerSheetWithTooFewQuestionsAnswered() throws Exception {
        ArrayList<Question> answers = buildQuestions(Arrays.asList("A"));

        List<Exam> students = Arrays.asList(
                buildExam(1, new ArrayList<String>(), answers));

        Report report = new StatsReporter().getReport(students, answers);

        String[] aggregate = report.rows.get(0);

        assertEquals(Double.parseDouble(aggregate[AVERAGE.ordinal()]), 0, 0.001);
        assertEquals(Double.parseDouble(aggregate[STANDARD_DEVIATION.ordinal()]), 0, 0.001);
        assertEquals(aggregate[MAXIMUM.ordinal()], "0");
        assertEquals(aggregate[MINIMUM.ordinal()], "0");

        assertEquals(report.rows.size(), 2);
    }

    public void testWithAnswerSheetsWithTooManyQuestionsAnswered() throws Exception {
        ArrayList<Question> answers = buildQuestions(Arrays.asList("A"));

        List<Exam> students = Arrays.asList(
                buildExam(1, Arrays.asList("A", "B", "C", "D"), answers));

        Report report = new StatsReporter().getReport(students, answers);

        String[] aggregate = report.rows.get(0);

        assertEquals(Double.parseDouble(aggregate[AVERAGE.ordinal()]), 1, 0.001);
        assertEquals(Double.parseDouble(aggregate[STANDARD_DEVIATION.ordinal()]), 0, 0.001);
        assertEquals(aggregate[MAXIMUM.ordinal()], "1");
        assertEquals(aggregate[MINIMUM.ordinal()], "1");

        assertEquals(report.rows.size(), 2);


    }

    public void testStatReportAggregate() throws Exception {
        ArrayList<Question> answers = buildQuestions(Arrays.asList("A", "A", "A", "A", "A"));

        List<Exam> students = Arrays.asList(
                buildExam(1, Arrays.asList("A", "B", "C", "D", "E"), answers),
                buildExam(2, Arrays.asList("B", "A", "A", "A", "A"), answers));

        Report report = new StatsReporter().getReport(students, answers);

        String[] aggregate = report.rows.get(0);

        assertEquals(Double.parseDouble(aggregate[AVERAGE.ordinal()]), 2.5, 0.001);
        assertEquals(Double.parseDouble(aggregate[STANDARD_DEVIATION.ordinal()]), 1.5, 0.001);
        assertEquals(aggregate[MAXIMUM.ordinal()], "4");
        assertEquals(aggregate[MINIMUM.ordinal()], "1");
    }

    private ArrayList<Question> buildQuestions(List<String> answers) {
        ArrayList<Question> questions = new ArrayList<>();

        for (int i = 0; i < answers.size(); i++) {
            Question question = new Question();
            question.setqNum(i);
            for (String choice : answers.get(i).split("")) {
                if (!choice.isEmpty()) {
                    question.addChoice(choice);
                }
            }

            questions.add(question);
        }

        return questions;
    }

    private Exam buildExam(Integer studentId, List<String> answers,
            ArrayList<Question> answerKey) {
        Exam exam = new Exam(buildQuestions(answers));
        exam.setStudentID(studentId);

        if (answerKey != null) {
            exam.grade(answerKey);

        }
        return exam;
    }
}
