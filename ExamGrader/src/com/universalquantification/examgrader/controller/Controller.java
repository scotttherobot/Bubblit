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
        
    }
    
    /**
     * Initializes a controller with an InputFileList
     * @param inputFileList InputFileList: an inputFileList to use
     * @param grader a grader to use
     * @pre the grader must be tied to the InputFileList
     */
    Controller(AppView view, InputFileList inputFileList, Grader grader)
    {
        
    }
    
    /**
     * Changes the roster file associated with the program. This sets an
     * error on the UI if changing the roster file fails.
     * 
     * @param rosterFile new roster file to use
     */
    
    public void changeRosterFile(File rosterFile)
    {
        
    }
    
    /**
     * Sets the grader to grade the roster files and then generates reports for
     * the results. This sets an error on the UI
     * if grading fails.
     */
    public void grade()
    {
        
    }
    
    /**
     * Stops any grading tasks that are currently in progress.
     * @pre There is a grading task in progress.
     */
    public void cancel()
    {
        
    }
    
    /**
     * Adds an input file to the input files to be processed. This sets an error
     * if the file can't be read or is malformed.
     * @param inputFile input file to be processed
     */
    public void addInputFile(File inputFile)
    {
        
    }
    
    /**
     * Deletes an input file from the list of input files to be processed.
     * This sets an error if the file to be deleted is not found.
     * @param ndx index of the input file in the current list of input files to
     * be processed
     */
    public void deleteInputFile(int ndx)
    {
        
    }
            
    
}
