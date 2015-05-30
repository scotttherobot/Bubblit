/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.ui;

/**
 *
 * @author jenwang
 */
public class AppViewExceptionHandler implements Thread.UncaughtExceptionHandler{

    private AppView appView;
    
    public AppViewExceptionHandler(AppView appView) {
        this.appView = appView;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable thrwbl) {
        thrwbl.printStackTrace();
        appView.showError("We received an unexpected error. Please restart and"
                + "try again.");
    }
    
}
