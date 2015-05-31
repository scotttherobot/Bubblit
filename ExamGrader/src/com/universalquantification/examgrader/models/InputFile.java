package com.universalquantification.examgrader.models;

import com.sun.pdfview.PDFFile;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * An InputFile represents a set of exams in which the first page is an answer
 * key and the following pages are student exams.
 *
 * @author Jenny Wang
 * @version 2.0
 */
public class InputFile
{

    /**
     * File name
     */
    private File file;

    /**
     * List of InputPages created from loading the reader
     */
    private List<InputPage> pages;
    
    /**
     * Whether the pages have been populated yet.
     */
    private boolean pagesGenerated;

    /**
     * The PDF file to ge pages from.
     */
    private PDFFile pdf; 
    
    /**
     * Loads a file into the system. The file must be a PDF in which each page
     * is a bubble form.
     *
     * Each page must use the template from <a href=
     * "https://wiki.csc.calpoly.edu/jdSpr15team1/wiki/bubblitform"> here</a>.
     *
     * @param file path to use
     * @throws IOException if the file is invalid
     */
    public InputFile(File file) throws IOException
    {
        // SET file field to file
        this.file = file;

        // READ file INTO pdf as a PDF      
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel channel = raf.getChannel();
        MappedByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                channel.size());
        pdf = new PDFFile(buf);    
        
    }

    private void addPages()
    {
        // FOR each page in pdf
        pages = new ArrayList<InputPage>();
        
        // go through every page in the PDF and create an InputPage from it.
        for (int onPage = 1; onPage < pdf.getNumPages() + 1; onPage++)
        {
            // INIT inputPage as InputPage with page    
            InputPage inputPage = new InputPage(file, pdf.getPage(onPage));
            // ADD inputPage to pages
            pages.add(inputPage);
        }
        // END FOR */
        pagesGenerated = true;
        pdf = null;
    }

    /**
     * Returns the name of the file.
     *
     * @return File object for the file
     */
    public File getFileName()
    {
        return file;
    }

    /**
     * Returns the answer key page
     *
     * @return the answer key pages
     */
    public InputPage getAnswerKeyPage()
    {
        // get pages if the haven't been added yet
        if(!pagesGenerated)
        {
            addPages();
        }
        
        // RETURN pages[0]
        return pages.get(0);
    }

    /**
     * Returns all pages that represent a student exam.
     *
     * @return all student exam pages
     */
    public List<InputPage> getStudentExamPages()
    {
        // get pages if the haven't been added yet
        if(!pagesGenerated)
        {
            addPages();
        }
        
        // RETURN pages[1:]
        return pages.subList(1, pages.size());
    }

    /**
     * Returns the total number of pages in the exam that are for a student
     * exam.
     *
     * @return number of pages in the exam
     */
    public int getNumPages()
    {
        // get pages if the haven't been added yet
        if(!pagesGenerated)
        {
            addPages();
        }
        
        // RETURN length of pages
        return pages.size();
    }

    /**
     * Returns a string representation of the InputFile
     *
     * @return a string representation of the InputFile
     */
    @Override
    public String toString()
    {
        // RETURN "InputFile {" concatenated  with file concatenated with "}"
        return "InputFile {" + file + "}";
    }

    /**
     * Checks that another object is equal to this InputFile
     *
     * @param obj other object
     * @return true if the objects are equal, false if not
     */
    @Override
    public boolean equals(Object obj)
    {
        // IF other is null
        // RETURN false
        // ELSE IF other is not a InputFile
        // RETURN FALSE
        
        // if the object is null or isn't an InputFile, it is not equal.
        if (obj == null || !(obj instanceof InputFile))
        {
            return false;
        }
        // ELSE
        // RETURN true iff this object's file is equal to that object's file
        // END IF
        InputFile other = (InputFile) obj;

        return other.file == this.file;
    }

    /**
     * Returns a hash code for the InputFile
     *
     * @return a hash code for the InputFile
     */
    @Override
    public int hashCode()
    {
        // RETURN CALL hashCode file
        return file.hashCode();
    }
}