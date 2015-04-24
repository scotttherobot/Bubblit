/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

import java.util.ArrayList;

/**
 * Main Application
 *
 * @author Luis
 */
public class MainApplication
{
    /**
     * Runs the application
     *
     * @param args command line arguments
     */
    public static void main(String[] args)
    {
        ArrayList<String> paths = new ArrayList<String>();
        paths.add("Exams.pdf");

        GradeExams.grade(paths);
    }
}
