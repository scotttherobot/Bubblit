package com.universalquantification.examgrader.grader;

import com.universalquantification.examgrader.models.GradedExamCollection;
import com.universalquantification.examgrader.models.InputFileList;
import com.universalquantification.examgrader.reader.ExamReader;
import com.universalquantification.examgrader.reader.StudentNameMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ExecutorService;

/**
 * A Grader grades exams found in a list of input files and tracks its own
 * progress.
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
     * reads them using the examReader, and sets their names using the
     * student name mapper.
     * @param inputFileList list of files to grade.
     * @param examReader reader to be used for reading the inputFiles.
     * @param mapper the student name mapper that gives us a name from a username
     * @pre inputFiles have been read in and confirmed to be of the correct format
     */
    public Grader(InputFileList inputFileList, ExamReader examReader, StudentNameMapper mapper)
    {
        // SET this.inputFiles to inputFileList
        // SET this.examReader to examReader
        // SET this.studentNameMapper to mapper
        // SET this.pagesGrades to 0
        // SET this.pagesToGrade to number of pages in inputFileList
        pagesToGrade = 0;
    }
    
    
    /**
     * Cancels execution of current grading and notifies observers.
     * @throws IllegalStateException Execution must be in progress.
     */
    public void cancel()
    {
    }

    /**
     * Grades exams, notifying any observers of exams that have been graded.
     * @return a Map mapping an input file to a {@link GradedExamCollection}
     * @throws GradingException
     * @pre has been constructed.
     */
    public Map<File, GradedExamCollection> grade()
            throws GradingException
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
        return null;
    }
    
    /**
     * Updates the roster file associated with the StudentNameMapper.
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
     * @return the number of pages graded so far.
     */
    public int getPagesGraded()
    {
        return pagesGraded;
    }
    
    /**
     * Get the total number of pages to grade. Used by observers.
     * @return the number of pages to grade.
     */
    public int getTotalPagesToGrade()
    {
        return pagesToGrade;
    }

}
