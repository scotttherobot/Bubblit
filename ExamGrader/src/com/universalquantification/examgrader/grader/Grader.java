package com.universalquantification.examgrader.grader;

import com.sun.pdfview.PDFFile;
import com.universalquantification.examgrader.grader.ExamRosterMatcher.MatchResult;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.GradedExamCollection;
import com.universalquantification.examgrader.models.InputFileList;
import com.universalquantification.examgrader.models.InputPage;
import com.universalquantification.examgrader.reader.ExamReader;
import com.universalquantification.examgrader.reader.InvalidExamException;
import com.universalquantification.examgrader.reader.StudentNameMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
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
    private InputFileList inputFiles;
    /**
     * Reader to read the list of files.
     */
    private ExamReader examReader;

    /**
     * Pages that have been graded so far.
     */
    private int pagesGraded;

    /**
     * Total number of pages this grader must grade.
     */
    private final int pagesToGrade;

    /**
     * Student mapper to be used
     */
    private StudentNameMapper studentNameMapper;

    /**
     * Constructs a new Grader instance. Reads files off of the inputFileList,
     * reads them using the examReader, and sets their names using the student
     * name mapper.
     *
     * @param inputFileList list of files to grade.
     * @param examReader reader to be used for reading the inputFiles.
     * @param mapper the student name mapper that gives us a name from a
     * username
     * @pre inputFiles have been read in and confirmed to be of the correct
     * format
     */
    public Grader(InputFileList inputFileList, ExamReader examReader,
            StudentNameMapper mapper)
    {
        // SET this.inputFiles to inputFileList
        // SET this.examReader to examReader
        // SET this.studentNameMapper to mapper
        // SET this.pagesGrades to 0
        // SET this.pagesToGrade to number of pages in inputFileList
        pagesToGrade = inputFileList.getTotalPages();
        this.inputFiles = inputFileList;
        this.examReader = examReader;
        this.studentNameMapper = mapper;
    }

    /**
     * Cancels execution of current grading and notifies observers.
     *
     * @throws IllegalStateException Execution must be in progress.
     */
    public void cancel()
    {
    }

    /**
     * Grades exams, notifying any observers of exams that have been graded.
     *
     * @return a Map mapping an input file to a {@link GradedExamCollection}
     * @throws GradingException
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
        List<File> files = this.inputFiles.getFiles();
        File pdfFile = files.get(0);

        RandomAccessFile raf = new RandomAccessFile(pdfFile, "r");
        FileChannel channel = raf.getChannel();
        MappedByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                channel.size());
        PDFFile pdf = new PDFFile(buf);

        for (int i = 1; i < pdf.getNumPages() + 1; i++)
        {
           // System.out.println("===Exam " + i + "======");
            InputPage file = new InputPage(pdfFile, pdf.getPage(i));
            Exam result = examReader.getExam(file, null);
            exams.add(result);
        }

        FileReader rosterFile = new FileReader("students.tsv");
        Roster studentReader = new Roster(rosterFile);

        List<RosterEntry> rosters = RosterParser.parseRoster(studentReader);
        // Go do the matching
        final List<MatchResult> matches = ExamRosterMatcher.
                match(exams, rosters);

        Set<RosterEntry> matchedEntries = new HashSet<>();

        // Sort by confidence level (ascending).
        Collections.sort(matches, new Comparator<MatchResult>()
        {
            @Override
            public int compare(MatchResult o1, MatchResult o2)
            {
                return Double.compare(o1.confidence, o2.confidence);
            }
        });

        // Process and print each match result.
        for (MatchResult result : matches)
        {
            matchedEntries.add(result.match);
            System.out.format("[%.3f] %s %s ==> %s %s\n", result.confidence,
                    result.form.getStudentRecord().getStochasticFirst().
                    toString(),
                    result.form.getStudentRecord().getStochasticLast().
                    toString(),
                    result.match.getFirst(),
                    result.match.getLast());
        }

        int unmatched = matchedEntries.size() - rosters.size();

        // Alert if there are any unmatched entries.
        if (unmatched == 0)
        {
            System.out.println("No entries unmatched (perfect permutation)");
        }
        else
        {
            System.out.println("Unmatched roster entries: " + unmatched);
        }

        return null;
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

}
