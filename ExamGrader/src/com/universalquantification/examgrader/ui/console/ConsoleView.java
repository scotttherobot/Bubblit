package com.universalquantification.examgrader.ui.console;

import com.universalquantification.examgrader.controller.Controller;
import com.universalquantification.examgrader.controller.ControllerFactory;
import com.universalquantification.examgrader.grader.Grader;
import com.universalquantification.examgrader.grader.RosterEntry;
import com.universalquantification.examgrader.models.GradedExamCollection;
import com.universalquantification.examgrader.ui.AppView;
import com.universalquantification.examgrader.utils.PreferencesManager;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Represents the console version view of the app.
 * @author jenwang
 */
public class ConsoleView implements AppView, Observer
{
    private Writer outWriter;
    private String nameAndVersion;
    private ControllerFactory controllerFactory;
    private String outputDir;
    private String rosterPath;
    private String[] inputPaths;
    private Controller controller;

    /**
     * Create a new console view.
     * @param nameAndVersion The name and version
     * @param rosterPath The path to the roster file
     * @param inputPaths the paths to input files
     * @param outputDir the path for outputting results
     * @param outWriter the write that writes results
     * @param controllerFactory the factory that generates {@link Controller}s
     */
    public ConsoleView(String nameAndVersion, String rosterPath,
        String[] inputPaths, String outputDir, Writer outWriter,
        ControllerFactory controllerFactory)
    {
        this.outWriter = outWriter;
        this.outputDir = outputDir;
        this.rosterPath = rosterPath;
        this.nameAndVersion = nameAndVersion;
        this.inputPaths = inputPaths;
        this.controllerFactory = controllerFactory;
    }
    
    /**
     * Runs the application.
     */
    public void run()
    {
        if (controller == null)
        {
            controller = controllerFactory.buildController(this);
        }

        // make sure the output directory exists
        if (outputDir != null)
        {
            PreferencesManager.getInstance().set(PreferencesManager.kOverrideDir,
                    new File(outputDir));
        }

        write(nameAndVersion + "\n");

        controller.changeRosterFile(new File(rosterPath));

        write(rosterPath + " validated\n");

        File[] files = new File[inputPaths.length];
        
        // add all the files to our list of files
        for (int onFile = 0; onFile < inputPaths.length; onFile++)
        {
            files[onFile] = new File(inputPaths[onFile]);
        }

        // grade each file
        for (File file : files)
        {
            write("Grading " + file.getName() + "\n");
            controller.addInputFile(file);
            controller.grade();
        }
        
    }

    /**
     * Show an error
     * @param error the error to display
     */
    @Override
    public final void showError(String error)
    {
        write(error);
        System.exit(1);
    }

    private void write(String msg)
    {
        try
        {
            outWriter.write(msg);
            outWriter.flush();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check that students were matched correctly. The console view has no way to
     * do this so just write the results.
     * @param results the graded results
     * @param roster the roster to read student information from
     * @pre Must be called after run()
     */
    @Override
    public void checkRoster(Map<File, GradedExamCollection> results,
        List<RosterEntry> roster)
    {
        controller.writeReports(results);
    }

    /**
     * Handle updates from objects
     * @param o the object that was updated
     * @param arg the payload
     */
    @Override
    public void update(Observable o, Object arg)
    {
        // check if we've received an update from a Grader instance
        if (o instanceof Grader)
        {
            Grader grader = (Grader) o;
            double percent = 
                100 * 1.0 * grader.getPagesGraded() / grader.getTotalPagesToGrade();
            write((int) percent + "%");
            // check if we're done grading
            if (percent == 100)
            {
                write("\nDone!\n");
            }
            else
            {
                write("... ");
            }

        }
    }

}
