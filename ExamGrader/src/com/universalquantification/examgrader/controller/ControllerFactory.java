/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.controller;

import com.universalquantification.examgrader.ui.AppView;

/**
 *
 * @author jenny
 */
public class ControllerFactory {
    
    public Controller buildController(AppView view)
    {
        return new Controller(view);
    }
    
}
