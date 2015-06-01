package com.universalquantification.examgrader.ui;

import com.universalquantification.examgrader.controller.Controller;
import com.universalquantification.examgrader.grader.Grader;
import com.universalquantification.examgrader.grader.RosterEntry;
import com.universalquantification.examgrader.models.GradedExamCollection;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author jenwang
 */
public class ConsoleView implements AppView, Observer
{
    private Writer outWriter;
    private Controller controller;
    
    public ConsoleView(String nameAndVersion, String rosterPath, String[] inputPaths,
            String outputDir, Writer outWriter)
    {
        this.outWriter = outWriter;
        
        this.controller = new Controller(this);
        
        write(nameAndVersion + "\n");
        
        boolean success = this.controller.changeRosterFile(new File(rosterPath));
        if (!success)
        {
            System.exit(1);
        }
        
        write(rosterPath + " validated\n");
        
        File[] files = new File[inputPaths.length];
        for (int i = 0; i < inputPaths.length; i++)
        {
            files[i] = new File(inputPaths[i]);
        }
        
        for (File file: files)
        {
            write("Grading " + file.getName() + "\n");
            this.controller.addInputFile(file);
            this.controller.grade();
        }   
    }

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

    @Override
    public void checkRoster(Map<File, GradedExamCollection> results, List<RosterEntry> roster)
    {
        controller.writeReports(results);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof Grader)
        {
            Grader grader = (Grader) o;
            double percent = 100 * 1.0 * grader.getPagesGraded() / grader.getTotalPagesToGrade();
            write((int) percent + "%");
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
