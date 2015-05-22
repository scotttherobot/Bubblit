package com.universalquantification.examgrader.models;

import com.sun.pdfview.PDFPage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * An InputPage represents a single page in an input file. Each page contains an
 * Exam that can be graded.
 *
 * @author Jenny Wang
 * @version 2.0
 */
public class InputPage
{

    private BufferedImage image;
    private File file;
    private static final int kPageHeight = 1000;
    private static final int kPageWidth = (int)((8.5/11.0) * kPageHeight);

    /**
     * Constructs an InputPage from a PDFPage. The PDFPage must have the bubble
     * form loaded from <a href=
     * "https://wiki.csc.calpoly.edu/jdSpr15team1/wiki/bubblitform">
     * here</a>.
     *
     * @param file file path associated with the input page
     * @param page PDF bubble form
     */
    public InputPage(File file, PDFPage page)
    {

        this.file = file;
        Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(),
                (int) page.getBBox().getHeight());
        System.out.println("start");
        Image pageImage = page.getImage(kPageWidth, kPageHeight, rect, null,
                true, true);
        System.out.println("end");
        BufferedImage bImage = new BufferedImage(kPageWidth, kPageHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D bufImageGraphics = bImage.createGraphics();
        bufImageGraphics.drawImage(pageImage, null, null);
        bufImageGraphics.dispose();

        this.image = bImage;

    }

    /**
     * Gets the BufferedImage associated with a page.
     *
     * @return image the page was converted into
     */
    public BufferedImage getBufferedImage()
    {
        return this.image;
    }

}
