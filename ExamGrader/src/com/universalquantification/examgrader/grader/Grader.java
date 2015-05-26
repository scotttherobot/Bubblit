package com.universalquantification.examgrader.grader;

import com.universalquantification.examgrader.grader.ExamRosterMatcher.MatchResult;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.GradedExamCollection;
import com.universalquantification.examgrader.models.InputFile;
import com.universalquantification.examgrader.models.InputFileList;
import com.universalquantification.examgrader.models.InputPage;
import com.universalquantification.examgrader.reader.ExamReader;
import com.universalquantification.examgrader.reader.InvalidExamException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * A Grader grades exams found in a list of input files and tracks its own
 * progress.
 *
 * @version 2.0
 * @author lcuellar
 */
public class Grader extends Observable
{

    /**
     * An executor service for multithreading.
     */
    private ExecutorService executorService;

    /**
     * List of input files to be read.
     */
    private final InputFileList inputFiles;
    /**
     * Reader to read the list of files.
     */
    private final ExamReader examReader;

    /**
     * Pages that have been graded so far.
     */
    private int pagesGraded;

    /**
     * Total number of pages this grader must grade.
     */
    private final int pagesToGrade;

    /**
     * Files that have been graded so far.
     */
    private int filesGraded;

    /**
     * Total number of files this grader must grade.
     */
    private final int filesToGrade;

    /**
     * The path to the roster file
     */
    private final String rosterFile;

    private boolean doGrade;

    /**
     * Constructs a new Grader instance. Reads files off of the inputFileList,
     * reads them using the examReader, and sets their names using the student
     * name mapper.
     *
     * @param inputFileList list of files to grade.
     * @param examReader reader to be used for reading the inputFiles.
     * @param rosterFile the path to the TSV file username
     * @pre inputFiles have been read in and confirmed to be of the correct
     * format
     */
    public Grader(InputFileList inputFileList, ExamReader examReader,
            String rosterFile)
    {
        // SET this.inputFiles to inputFileList
        // SET this.examReader to examReader
        // SET this.studentNameMapper to mapper
        // SET this.pagesGrades to 0
        // SET this.pagesToGrade to number of pages in inputFileList
        pagesToGrade = inputFileList.getTotalPages();
        this.inputFiles = inputFileList;
        this.examReader = examReader;
        this.rosterFile = rosterFile;
        doGrade = true;
        this.filesToGrade = this.inputFiles.getInputFiles().size();
    }

    /**
     * Cancels execution of current grading and notifies observers.
     *
     * @throws IllegalStateException Execution must be in progress.
     */
    public void cancel()
    {
        doGrade = false;
        setChanged();
        notifyObservers();
    }

    /**
     * Determines whether this instance is still in the process of grading.
     *
     * @return whether this instance has canceled grading
     */
    public boolean isCancelled()
    {
        return !doGrade;
    }

    /**
     * Grades exams, notifying any observers of exams that have been graded.
     *
     * @return a Map mapping an input file to a {@link GradedExamCollection}
     * @throws GradingException
     * @throws java.io.FileNotFoundException
     * @throws
     * com.universalquantification.examgrader.reader.InvalidExamException
     * @pre has been constructed.
     */
    public Map<File, GradedExamCollection> grade()
        throws GradingException, FileNotFoundException, IOException,
        InvalidExamException
    {
        // SET fileExamsMap to new Map<File, GradedExamCollection>

        // FOR each file in this.inputFiles
        // SET answerKeyPage to file.getAnswerKey()
        // CALL this.examReader.getExam WITH answerKeyPage, this.mapper RETURNIN examKey
        // SET graded to new GradedExamCollection
        // SET graded.examKey to examKey
        // FOR each page in file.getStudentExamPages()
        // SET exam to examReader.getExam(page, this.mapper)
        // CALL exam.grade with examKey
        // ADD exam to graded
        // CALL setChanged
        // CALL notifyObservers
        // ENDFOR
        // MAP file to graded in fileExamsMap
        // ENDFOR
        // RETURN fileExamMap
        ExamRosterMatcher matcher = new ExamRosterMatcher();
        ArrayList<Exam> exams = new ArrayList<Exam>();
        List<InputFile> files = this.inputFiles.getInputFiles();
        LinkedHashMap<File, GradedExamCollection> gradedFiles
                = new LinkedHashMap<File, GradedExamCollection>();
        FileReader rosterFileReader = new FileReader(this.rosterFile);
        Roster studentReader = new Roster(rosterFileReader);
        List<RosterEntry> rosters = RosterParser.parseRoster(studentReader);

        Iterator<InputFile> filesIterator = files.iterator();
        // iterate over the files to grade them
        while (filesIterator.hasNext() && this.doGrade)
        {
            InputFile file = filesIterator.next();
            InputPage answerPage = file.getAnswerKeyPage();
            Exam answerKey = examReader.getExam(answerPage);
            List<InputPage> examPages = file.getStudentExamPages();

            Iterator<InputPage> pageIterator = examPages.iterator();
            // iterate over the pages to grade them
            while (pageIterator.hasNext() && this.doGrade)
            {
                InputPage examPage = pageIterator.next();
                Exam exam = examReader.getExam(examPage);
                exam.grade(answerKey);
                exams.add(exam);
                this.pagesGraded++;
                setChanged();
                notifyObservers();
            }

            // do roster matching
            final List<MatchResult> matches = ExamRosterMatcher.match(exams,
                    rosters);
            Set<RosterEntry> matchedEntries = new HashSet<RosterEntry>();

            // Sort by confidence level (ascending).
            Collections.sort(matches, new Comparator<MatchResult>()
            {
                @Override
                public int compare(MatchResult o1, MatchResult o2)
                {
                    return Double.compare(o1.confidence, o2.confidence);
                }
            });
            
            // notify the obvservers that the grading process for this file has
            // ended and that these are the roster matches.
            setChanged();
            notifyObservers(matches);

            // add the graded exam collection mapping
            GradedExamCollection gradedCollection = new GradedExamCollection(
                answerKey, exams);
            gradedFiles.put(file.getFileName(), gradedCollection);
            filesGraded++;
        }

        return gradedFiles;
    }

    /**
     * Updates the roster file associated with the StudentNameMapper.
     *
     * @param file to update with
     * @throws IOException - the exception if the file is in an invalid roster
     * format
     */
    public void updateRoster(File file) throws IOException
    {
        // CALL this.mapper.updateRoster WITH file
    }

    /**
     * Get the number of pages graded so far. Used by observers.
     *
     * @return the number of pages graded so far.
     */
    public int getPagesGraded()
    {
        return pagesGraded;
    }

    /**
     * Get the total number of pages to grade. Used by observers.
     *
     * @return the number of pages to grade.
     */
    public int getTotalPagesToGrade()
    {
        return pagesToGrade;
    }

    /**
     * Get the total number of files that this instance needs to grade.
     * @return the number of files to grade
     */
    public int getTotalFilesToGrade()
    {
        return filesToGrade;
    }

    /**
     * Get the total number of files that have been graded so far.
     * @return 
     */
    public int getFilesGraded()
    {
        return filesGraded;
    }
}
