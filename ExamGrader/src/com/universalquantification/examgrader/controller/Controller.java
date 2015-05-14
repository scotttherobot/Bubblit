package com.universalquantification.examgrader.controller;

import com.universalquantification.examgrader.grader.Grader;
import com.universalquantification.examgrader.models.InputFileList;
import com.universalquantification.examgrader.ui.AppView;
import java.io.File;

/**
 * The Controller mediates between a view of the app, handling input from the 
 * user and calls out to the view for logic that does not seem to fit in a model
 * (e.g. error handling)
 * 
 * @author Jenny Wang
 * @version 2.0
 */
public class Controller {
    
    /**
     * The InputFileList tied to the Controller.
     */
    private InputFileList inputFileList;

    private Grader grader;
    
    private AppView appView;
    
    /**
     * Initializes a controller with a new InputFileList and Grader.
     */
    public Controller(AppView view)
    {
        // SET the view field to view
        // INIT a new StudentNameMapper studentNameMapper
        // INIT a new ExamReader examReader with studentNameMapper as reader
        // INIT a new InputFileList as inputFileList field
        // INIT a new Grader with inputFileList, reader, mapper as grader field
    }
    
    /**
     * Initializes a controller with an InputFileList
     * @param inputFileList InputFileList: an inputFileList to use
     * @param grader a grader to use
     * @pre the grader must be tied to the InputFileList
     */
    Controller(AppView view, InputFileList inputFileList, Grader grader)
    {
        // SET the view field to view
        // SET the inputFileList field to inputFileList
        // SET the grader field to grader
    }
    
    /**
     * Changes the roster file associated with the program. This sets an
     * error on the UI if changing the roster file fails.
     * 
     * @param rosterFile new roster file to use
     */
    
    public void changeRosterFile(File rosterFile)
    {
        // BEGIN
            // CALL updateRosterFile grader WITH rosterFile
        // EXCEPTION IOException
            // CALL setError With "Failed to read the roster file"
        // EXCEPTION
            // CALL setError With "Unknown exception reading the roster file"
        // END
    }
    
    /**
     * Sets the grader to grade the roster files and then generates reports for
     * the results. This sets an error on the UI
     * if grading fails.
     */
    public void grade()
    {
        // BEGIN
            // CALL grade grader RETURNING gradingResults
        // EXCEPTION GradingException
            // CALL setError view "Grading failed. Please check that the
            // format is correct"
        // END
        
        // INIT reportWriter gradingResults
        // CALL writeReports reportWriter
    }
    
    /**
     * Stops any grading tasks that are currently in progress.
     * @pre There is a grading task in progress.
     */
    public void cancel()
    {
        // CALL cancel grader
        
    }
    
    /**
     * Adds an input file to the input files to be processed. This sets an error
     * if the file can't be read or is malformed.
     * @param inputFile input file to be processed
     */
    public void addInputFile(File inputFile)
    {
        // BEGIN
            // CALL addInputFile inputFileList WITH inputFile
        // EXCEPTION IOException
            // CALL setError view WITH "We could not read the file."
        // END
    }
    
    /**
     * Deletes an input file from the list of input files to be processed.
     * @param ndx index of the input file in the current list of input files to
     * be processed
     */
    public void deleteInputFile(int ndx)
    {
        // CALL deleteInputFile inputFileList WITH ndx
    }
            
    
}
