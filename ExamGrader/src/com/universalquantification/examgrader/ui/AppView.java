package com.universalquantification.examgrader.ui;

import com.universalquantification.examgrader.grader.RosterEntry;
import com.universalquantification.examgrader.models.GradedExamCollection;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Observer;

/**
 * An interface representing a view for the grader application.
 * 
 * @author Jenny Wang
 * @version 2.0
 */
public interface AppView extends Observer {
    
    /**
     * Shows an error appropriate to the type of the view.
     * @param error error message to show.
     */
    public void showError(String error);
    
    /**
     * Verify that the exams are labeled with the correct matches.
     * @param results results to check
     * @param roster roster to use
     */
    public void checkRoster(Map<File, GradedExamCollection> results, List<RosterEntry> roster);
    
}
