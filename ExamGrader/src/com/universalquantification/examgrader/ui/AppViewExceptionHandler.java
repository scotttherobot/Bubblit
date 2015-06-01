/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.ui;

/**
 * Handles an exception from an {@link AppView}
 * @author jenwang
 */
public class AppViewExceptionHandler implements Thread.UncaughtExceptionHandler
{

    private AppView appView;

    /**
     * Create a new exception handler for the given {@link AppView}
     * @param appView the AppView to handle exceptions for
     */
    public AppViewExceptionHandler(AppView appView)
    {
        this.appView = appView;
    }

    /**
     * Notify the user that an unhandled exception was thrown
     * @param thread the thread it occurred on
     * @param thrwbl what was thrown
     */
    @Override
    public void uncaughtException(Thread thread, Throwable thrwbl)
    {
        thrwbl.printStackTrace();
        appView.showError("We received an unexpected error. Please restart and"
            + "try again.");
    }

}
