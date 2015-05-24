package com.universalquantification.examgrader.ui;

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
    
}
