/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author jenwang
 */
public class StatsReporter {

    public static final String NUMBER_OF_FAILED_RESPONSES_FORMAT =
            "Number of Failed Responses '%s'";

    protected enum Column {

        QUESTION,
        MAXIMUM,
        MINIMUM,
        AVERAGE,
        STANDARD_DEVIATION,
        FREQUENCY_OF_MISSED_QUESTION,
        MOST_COMMON_FAILED_RESPONSE;

        public String toString() {
            String[] words = name().split("_");

            StringBuilder builder = new StringBuilder();
            for (String word : words) {
                builder.append(word.charAt(0));
                builder.append(word.substring(1).toLowerCase());
                builder.append(" ");
            }

            return builder.toString().trim();
        }
    }

    private static class AnswerComparator implements Comparator<Set<String>> {

        public int compare(Set<String> a, Set<String> b) {
            if (a.size() != b.size()) {
                return a.size() - b.size();
            }
            return answerToString(a).compareTo(answerToString(b));
        }
    }

    private String[] getColumnHeaders(Set<Set<String>> answers) {
        String[] cols = new String[Column.values().length + answers.size()];
        for (Column c : Column.values()) {
            cols[c.ordinal()] = c.toString();
        }

        int i = Column.values().length;
        for (Set<String> answer : answers) {
            cols[i] = String.format(NUMBER_OF_FAILED_RESPONSES_FORMAT,
                    answerToString(answer));
            i++;
        }

        return cols;
    }

    public Report getReport(List<Exam> exams, List<Question> answerKey) {
        if (exams.isEmpty()) {
            throw new IllegalArgumentException("Exams must not be empty!");
        }

        if (answerKey.isEmpty()) {
            throw new IllegalArgumentException(
                    "There must be at least one question in the answer key.");
        }

        Map<Integer, Map<Set<String>, Integer>> failedAnswersPerQuestion =
                new HashMap<>();
        SortedSet<Set<String>> seenAnswers = new TreeSet(new AnswerComparator());
        List<Integer> scores = new ArrayList<>();

        for (Question question : answerKey) {
            failedAnswersPerQuestion.put(question.getqNum(),
                    new HashMap<Set<String>, Integer>());
        }

        for (Exam exam : exams) {
            scores.add(exam.rawScore());

            for (Question question : exam.getAnswers()) {
                if (failedAnswersPerQuestion.containsKey(question.getqNum())
                        && !question.isCorrect()) {
                    Set<String> choice = question.getChoices();

                    seenAnswers.add(choice);

                    Map<Set<String>, Integer> failedAnswers =
                            failedAnswersPerQuestion.get(question.getqNum());

                    Integer count = failedAnswers.containsKey(choice)
                            ? failedAnswers.get(choice) + 1 : 1;
                    failedAnswersPerQuestion.get(question.getqNum()).put(
                            choice, count);
                }
            }
        }

        List<String[]> rows = new ArrayList<>();

        rows.add(getAggregateRow(scores));

        for (Integer question : new TreeSet<>(
                failedAnswersPerQuestion.keySet())) {
            String[] row = getRowForQuestion(question, 
                    failedAnswersPerQuestion.get(question), seenAnswers);
            rows.add(row);
        }

        return new Report(getColumnHeaders(seenAnswers), rows);
    }

    private String[] getAggregateRow(List<Integer> scores) {
        int max = 0;
        int min = Integer.MAX_VALUE;
        double avg = 0;

        for (Integer score : scores) {
            min = Math.min(min, score);
            max = Math.max(max, score);
            avg += score;
        }

        avg /= scores.size();

        double stdev = getStdev(scores, avg);

        String[] row = new String[Column.values().length];
        Arrays.fill(row, "-");

        row[Column.QUESTION.ordinal()] = "AGGREGATE";
        row[Column.STANDARD_DEVIATION.ordinal()] = String.valueOf(stdev);
        row[Column.MINIMUM.ordinal()] = String.valueOf(min);
        row[Column.MAXIMUM.ordinal()] = String.valueOf(max);
        row[Column.AVERAGE.ordinal()] = String.valueOf(avg);

        return row;
    }

    private double getStdev(List<Integer> scores, double avg) {
        double sum = 0;
        for (Integer score : scores) {
            double diff = (score - avg);
            sum += diff * diff;
        }

        return Math.sqrt(sum / (scores.size()));

    }

    private String getMostCommonFailedResponse(
            Map<Set<String>, Integer> allIncorrectAnswers) {
        if (allIncorrectAnswers.isEmpty()) {
            return "-";
        }

        int maxAnswers = Collections.max(allIncorrectAnswers.values());

        StringBuilder output = new StringBuilder();

        SortedSet<Set<String>> sortedResponses =
                new TreeSet<Set<String>>(new AnswerComparator());

        sortedResponses.addAll(allIncorrectAnswers.keySet());

        for (Set<String> incorrectAnswer : sortedResponses) {
            if (allIncorrectAnswers.get(incorrectAnswer) == maxAnswers) {
                if (output.length() != 0) {
                    output.append(" ");
                }
                output.append(answerToString(incorrectAnswer));
            }

        }

        return output.toString();
    }

    private String[] getRowForQuestion(Integer question,
            Map<Set<String>, Integer> allIncorrectAnswers, 
            SortedSet<Set<String>> answerColumns) {

        int frequency = 0;

        String[] row = new String[Column.values().length + answerColumns.size()];
        Arrays.fill(row, "-");

        for (Map.Entry<Set<String>, Integer> answerEntry :
                allIncorrectAnswers.entrySet()) {
            frequency += answerEntry.getValue();
        }

        row[Column.QUESTION.ordinal()] = question.toString();
        row[Column.FREQUENCY_OF_MISSED_QUESTION.ordinal()] =
                String.valueOf(frequency);
        row[Column.MOST_COMMON_FAILED_RESPONSE.ordinal()] =
                getMostCommonFailedResponse(allIncorrectAnswers);


        int i = Column.values().length;
        for (Set<String> answer : answerColumns) {
            if (allIncorrectAnswers.containsKey(answer)) {
                row[i] = String.valueOf(allIncorrectAnswers.get(answer));
            }
            i++;
        }

        return row;
    }

    private static String answerToString(Set<String> answer) {
        if (answer.isEmpty()) {
            return "BLANK";
        }

        StringBuilder builder = new StringBuilder();
        for (String a : new TreeSet<>(answer)) {
            builder.append(a);
        }

        return builder.toString();
    }
}
