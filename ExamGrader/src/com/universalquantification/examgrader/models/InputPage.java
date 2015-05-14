package com.universalquantification.examgrader.models;

import com.sun.pdfview.PDFPage;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * An InputPage represents a single page in an input file. Each page contains
 * an Exam that can be graded.
 * @author Jenny Wang
 * @version 2.0
 */
public class InputPage {
    
    private BufferedImage image;
    private File file;
    
    /**
     * Constructs an InputPage from a PDFPage. The PDFPage must have the
     * bubble form loaded from <a href=
     * "https://wiki.csc.calpoly.edu/jdSpr15team1/wiki/bubblitform">
     * here</a>.
     * 
     * @param file file path associated with the input page
     * @param page PDF bubble form
     */
    public InputPage(File file, PDFPage page)
    {
        // SET file field to file
        // SET image to image created page
    }
    
    /**
     * Gets the BufferedImage associated with a page.
     * @return image the page was converted into
     */
    public BufferedImage getBufferedImage()
    {
        // RETURN image
        return null;
    }
    
    
}
