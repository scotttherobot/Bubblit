package com.universalquantification.examgrader.models;

import java.io.File;
import java.util.Observable;

import java.util.List;

/**
 * An InputFileList represents a list of InputFiles.
 * @author Jenny Wang
 * @version 2.0
 */

public class InputFileList extends Observable {
    
    private List<InputFile> files;
    
    /**
     * Returns the paths of all the files in the list
     * @return paths of all the files in the list
     */
    public List<File> getFiles()
    {
        return null;
    }
    
    /**
     * Returns the sum of all the pages in the InputFiles
     * @return total number of pages
     */
    public int getTotalPages()
    {
        return 0;
    }
    
    /**
     * Adds an input file to the list and notifies observers of changes.
     * @param file File path to add
     * @throws IOException if the file could not be read
     * @throws FileFormatException if the file was of the wrong format
     */
    public void addInputFile(File file)
    {
    }
    
    /**
     * Deletes the input file at place ndx from the list
     * of input files for processing.
     * @param ndx int index of the input file
     * @throws IndexOutOfBounds if there is no file at ndx
     */
    public void deleteInputFile(int ndx)
    {
        
    }
    
}
