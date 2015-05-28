/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.ui;

import com.universalquantification.examgrader.grader.RosterEntry;
import com.universalquantification.examgrader.models.GradedExamCollection;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author admin
 */
public class ConsoleView implements AppView, Observer {

    //systeem.error.printline
    @Override
    public void showError(String error) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

        //okay to keep unsupported
    @Override
    public void checkRoster(Map<File, GradedExamCollection> results, List<RosterEntry> roster) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
