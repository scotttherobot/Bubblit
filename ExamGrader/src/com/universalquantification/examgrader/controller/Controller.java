package com.universalquantification.examgrader.controller;

import com.universalquantification.examgrader.grader.Grader;
import com.universalquantification.examgrader.grader.MatchResult;
import com.universalquantification.examgrader.grader.Roster;
import com.universalquantification.examgrader.grader.RosterEntry;
import com.universalquantification.examgrader.grader.RosterParser;
import com.universalquantification.examgrader.models.InputFileList;
import com.universalquantification.examgrader.reader.ExamReader;
import com.universalquantification.examgrader.ui.AppView;
import java.io.File;
import java.io.IOException;
import com.universalquantification.examgrader.reader.NameRecognitionGateway;
import com.universalquantification.examgrader.reporter.ReportWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

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

    private AppView appView;
    
    private List<RosterEntry> rosterEntries;
    
    /**
     * Initializes a controller with a new InputFileList and Grader.
     */
    public Controller(AppView view) throws IOException
    {
        
        // SET the view field to view
        this.appView = view;
        // INIT a new InputFileList as inputFileList field
        this.inputFileList = new InputFileList();
        
        // INIT a new StudentNameMapper studentNameMapper
        // INIT a new ExamReader examReader with studentNameMapper as reader
        // INIT a new Grader with inputFileList, reader, mapper as grader field
        this.inputFileList.addObserver(appView);
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
        this.appView = view;
        this.inputFileList = inputFileList;
    }
    
    public void writeReports(Map<File, List<MatchResult>> results)
    {
            for (MatchResult result : results.values().iterator().next())
            {
                System.out.format("%s %s", 
                        result.form.getStudentRecord().getFirstName(),
                        result.form.getStudentRecord().getLastName());
            }

        
    }
    
    /**
     * Changes the roster file associated with the program. This sets an
     * error on the UI if changing the roster file fails.
     * 
     * @param rosterFile new roster file to use
     */
    
    public void changeRosterFile(File rosterFile) throws FileNotFoundException
    {
        this.rosterEntries =
                RosterParser.parseRoster(new Roster(new FileReader(rosterFile)));
    }
    
    /**
     * Sets the grader to grade the roster files and then generates reports for
     * the results. This sets an error on the UI
     * if grading fails.
     */
    public void grade()
    {
        // BEGIN
        try
        {
            if (inputFileList.getInputFiles().isEmpty())
            {
                appView.showError("You must add an input file.");
                return;
            }
            
            if (rosterEntries == null)
            {
                appView.showError("You must add a roster file.");
                return;
            }
            // INIT grader
            Grader grader = new Grader(inputFileList,
                new ExamReader(new NameRecognitionGateway()), rosterEntries);
            grader.addObserver(appView);
            // CALL grade grader RETURNING gradingResults
            Map<File, List<MatchResult>> results = grader.grade();
            
            // CALL clear on InputFileList
            inputFileList.clear();
            
            appView.checkRoster(results, rosterEntries);
            
            // INIT reportWriter gradingResults
        
            // CALL writeReports reportWriter
//            new ReportWriter(null).writeReports(results);
        }
        // EXCEPTION GradingException
        catch (Exception e)
        {
            e.printStackTrace();
            appView.showError("An error occured with message: " + e.getMessage());
            // CALL setError view "Grading failed. Please check that the
            // format is correct"
        }
        // END
        
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
        // BEGIN
        try
        {
            // CALL addInputFile inputFileList WITH inputFile
            inputFileList.addInputFile(inputFile);
        }
        // EXCEPTION IOException
        catch (IOException e)
        {
            // CALL setError view WITH "We could not read the file."
            appView.showError("We could not read the file.");
        }
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
        inputFileList.deleteInputFile(ndx);
    }
            
    
}
