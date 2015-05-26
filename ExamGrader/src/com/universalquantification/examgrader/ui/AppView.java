package com.universalquantification.examgrader.ui;

import com.universalquantification.examgrader.grader.MatchResult;
import com.universalquantification.examgrader.grader.RosterEntry;
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
    
    public void checkRoster(Map<File, List<MatchResult>> results, List<RosterEntry> roster);
    
}
