
package com.universalquantification.examgrader.models;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * An InputFile represents a set of exams in which the first page is an answer
 * key and the following pages are student exams.
 * 
 * @author Jenny Wang
 * @version 2.0
 */

public class InputFile {
    
    /**
     * File name
     */
    private File name;
    
    /**
     * List of InputPages created from loading the reader
     */
    private List<InputPage> pages;
    
    /**
     * Loads a file into the system. The file must be a PDF in which each page
     * is a bubble form.
     * 
     * Each page must use the template from <a href=
     * "https://wiki.csc.calpoly.edu/jdSpr15team1/wiki/bubblitform"> here</a>.
     * @param file path to use
     * @throws IOException if the file is invalid
     * @throws FileFormatException if the file cannot be converted into a list
     * of InputPages.
     */
    public InputFile(File file) throws IOException
    {   
    }
    
    /**
     * Returns the answer key page
     * @return the answer key pages
     */
    public InputPage getAnswerKeyPage()
    {
        return null;
    }
    
    
    /**
     * Returns all pages that represent a student exam.
     * @return all student exam pages
     */
    public List<InputPage> getStudentExamPages()
    {
        return null;
    }
    
    /**
     * Returns the total number of pages in the exam.
     * @return number of pages in the exam
     */
    public int getNumPages()
    {
        return 0;
    }
    
    /**
     * Returns a string representation of the InputFile
     * @return a string representation of the InputFile
     */
    @Override
    public String toString()
    {
        return "";
    }
    
    /**
     * Checks that another object is equal to this InputFile
     * @return true if the objects are equal, false if not
     */
    @Override
    public boolean equals(Object o)
    {
        return false;
    }
    
    /**
     * Returns a hash code for the InputFile
     * @return a hash code for the InputFile
     */
    @Override
    public int hashCode()
    {
        return 0;
    }
}
