/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.controller;

import com.universalquantification.examgrader.ui.AppView;

/**
 * Create a new controller
 *
 * @author jenny
 */
public class ControllerFactory
{

    /**
     * Create a new controller with a given {@link AppView}
     *
     * @param view the {@link AppView} to use
     * @return the controller
     */
    public Controller buildController(AppView view)
    {
        return new Controller(view);
    }

}
