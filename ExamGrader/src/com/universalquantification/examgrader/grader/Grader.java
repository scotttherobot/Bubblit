package com.universalquantification.examgrader.grader;

import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.GradedExamCollection;
import com.universalquantification.examgrader.models.InputFile;
import com.universalquantification.examgrader.models.InputFileList;
import com.universalquantification.examgrader.models.InputPage;
import com.universalquantification.examgrader.reader.ExamReader;
import com.universalquantification.examgrader.reader.InvalidExamException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
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
    private final List<RosterEntry> rosterEntries;

    /**
     * Whether the task was cancelled
     */
    private boolean doGrade;

    /**
     * Constructs a new Grader instance. Reads files off of the inputFileList,
     * reads them using the examReader, and sets their names using the student
     * name mapper.
     *
     * @param inputFileList list of files to grade.
     * @param examReader reader to be used for reading the inputFiles.
     * @param rosterEntries the entries in the roster
     * @throws com.universalquantification.examgrader.reader.InvalidExamException
     * @pre inputFiles have been read in and confirmed to be of the correct
     * format
     */
    public Grader(InputFileList inputFileList, ExamReader examReader,
        List<RosterEntry> rosterEntries) throws InvalidExamException
    {
        // SET this.inputFiles to inputFileList
        // SET this.examReader to examReader
        // SET this.studentNameMapper to mapper
        // SET this.pagesGrades to 0
        // SET this.pagesToGrade to number of pages in inputFileList
        pagesToGrade = inputFileList.getTotalPages();
        this.inputFiles = inputFileList;
        this.examReader = examReader;
        this.rosterEntries = rosterEntries;
        doGrade = true;
        this.filesToGrade = this.inputFiles.getInputFiles().size();
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
        setChanged();
        notifyObservers();
        ExamRosterMatcher matcher = new ExamRosterMatcher();
        List<InputFile> files = this.inputFiles.getInputFiles();
        Map<File, GradedExamCollection> fileExamsMap
            = new LinkedHashMap<File, GradedExamCollection>();

        Iterator<InputFile> filesIterator = files.iterator();
        // iterate over the files to grade them
        while (filesIterator.hasNext() && this.doGrade)
        {
            List<Exam> exams = new ArrayList<Exam>();
            InputFile file = filesIterator.next();
            InputPage answerPage = file.getAnswerKeyPage();
            Exam answerKey = examReader.getExam(answerPage);

            this.pagesGraded++;
            setChanged();
            notifyObservers();

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
                //System.out.println("grade " + exam.toString());
                setChanged();
                notifyObservers();
            }

            // do roster matching
            ExamRosterMatcher.match(exams, rosterEntries);

            // INIT collection as GradedExamCollection WITH answerKey, exams
            GradedExamCollection current = new GradedExamCollection(answerKey,
                    exams);
            fileExamsMap.put(file.getFileName(), current);

            filesGraded++;
        }

        return fileExamsMap;
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
;
}
