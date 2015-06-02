package com.universalquantification.examgrader.models;

import com.universalquantification.examgrader.reader.InvalidExamException;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.io.IOException;

import java.util.List;

/**
 * An InputFileList represents a list of InputFiles.
 *
 * @author Jenny Wang
 * @version 2.0
 */
public class InputFileList extends Observable
{

    private List<InputFile> files;

    /**
     * Construct a new InputFileList
     */
    public InputFileList()
    {
        files = new ArrayList<InputFile>();
    }

    /**
     * Returns the paths of all the files in the list
     *
     * @return paths of all the files in the list
     */
    public List<File> getFiles()
    {
        // RETURN files
        List<File> fileNames = new ArrayList<File>();
        // go through every file and add it to the list
        for (InputFile file : files)
        {
            fileNames.add(file.getFileName());
        }
        return fileNames;
    }

    /**
     * Returns all the input files in the list.
     *
     * @return all the input files in the list
     */
    public List<InputFile> getInputFiles()
    {
        return files;
    }

    /**
     * Clears the input file list
     */
    public void clear()
    {
        // CALL clear on files
        files.clear();
        // CALL setChanged
        setChanged();
        // CALL notifyObservers
        notifyObservers();
    }

    /**
     * Returns the sum of all the pages in the InputFiles
     *
     * @return total number of pages
     * @throws com.universalquantification.examgrader.reader.InvalidExamException
     */
    public int getTotalPages() throws InvalidExamException
    {
        int total = 0;
        // RETURN length of files
        for (InputFile file : files)
        {
            total += file.getNumPages();
        }

        return total;
    }

    /**
     * Adds an input file to the list and notifies observers of changes.
     *
     * @param file File path to add
     * @throws IOException if the file could not be read
     */
    public void addInputFile(File file) throws IOException
    {
        // INIT inputFile as InputFile with file
        InputFile inputFile = new InputFile(file);
        // Add files to inputFiles
        files.add(inputFile);

        // CALL setChanged
        setChanged();
        // CALL notifyObservers
        notifyObservers();
    }

    /**
     * Deletes the input file at place ndx from the list of input files for
     * processing.
     *
     * @param ndx int index of the input file
     */
    public void deleteInputFile(int ndx)
    {
        // Delete ndx'th element in inputFiles

        files.remove(ndx);

        // CALL setChanged
        // CALL notifyObservers
        setChanged();
        notifyObservers();

    }

}
