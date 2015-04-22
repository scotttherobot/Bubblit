package com.universalquantification.examgrader;

import boofcv.alg.feature.detect.template.TemplateMatching;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.core.image.ConvertBufferedImage;
import boofcv.factory.feature.detect.template.FactoryTemplateMatching;
import boofcv.factory.feature.detect.template.TemplateScoreType;
import boofcv.gui.binary.VisualizeBinaryData;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.feature.Match;
import boofcv.struct.image.ImageFloat32;
import boofcv.struct.image.ImageUInt8;
import boofcv.gui.image.ShowImages;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Class to grade exams.
 *
 * @author luis
 */
public class GradeExams 
{

    /**
     * Returns an image for a pdf page.
     *
     * @param page a PDFPage
     * @return a BufferedImage
     */
    public static BufferedImage createImage(PDFPage page) 
    {
        // Size of the image to process. Smaller means faster, but less precise.
        int width = 1000;
        int height = 1294;

        // create the bounding rectangle and get the image
        Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(),
                (int) page.getBBox().getHeight());
        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Image image = page.getImage(width, height, rect, null, true, true);
        Graphics2D bufImageGraphics = bufferedImage.createGraphics();
        bufImageGraphics.drawImage(image, 0, 0, null);

        // display the image from the pdf to the screen
        //ShowImages.showWindow(bufferedImage, "PDF");
        return bufferedImage;
    }

    /**
     * Get the bounds in which an exam appears in an image
     *
     * @param image Image of the exam to grade
     * @param leftAnchor Image of the left anchor
     * @param rightAnchor Image of the right anchor
     * @return a bounds object with the bounds
     */
    private static Bounds getFormHorizontalBounds(ImageFloat32 image,
            ImageFloat32 leftAnchor, ImageFloat32 rightAnchor) 
    {
        ImagePoint leftAnchorLoc = getTemplateLocation(image, leftAnchor, 1);
        ImagePoint rightAnchorLoc
            = getTemplateLocation(image, rightAnchor, 1);

        Bounds bounds = new Bounds();
        bounds.setMinX(leftAnchorLoc.getpX());
        bounds.setMaxX(rightAnchorLoc.getpX() + rightAnchor.width);
        bounds.setMinY(rightAnchorLoc.getpY() + leftAnchor.height);

        return bounds;
    }

    /**
     * Get the bounds in which an exam appears in an image
     *
     * @param image Image of the exam to grade
     * @param leftAnchor Image of the left anchor
     * @param rightAnchor Image of the right anchor
     * @return a bounds object with the bounds
     */
    private static Bounds getFormHorizontalBounds(BufferedImage image,
            BufferedImage leftAnchor, BufferedImage rightAnchor) 
    {
        // convert to usable format
        ImageFloat32 src = new ImageFloat32(image.getWidth(), image.getHeight());
        ImageFloat32 left = new ImageFloat32(leftAnchor.getWidth(),
                leftAnchor.getHeight());
        ImageFloat32 right = new ImageFloat32(rightAnchor.getWidth(),
                rightAnchor.getHeight());

        ConvertBufferedImage.convertFrom(image, src);
        ConvertBufferedImage.convertFrom(leftAnchor, left);
        ConvertBufferedImage.convertFrom(rightAnchor, right);

        return getFormHorizontalBounds(src, left, right);
    }

    /**
     * Get the location of an anchor in an image
     *
     * @param image the exam to scan
     * @param template the anchor to scan for
     * @param expectedMatches the number of expected matches in the image
     * @return
     */
    private static ImagePoint getTemplateLocation(ImageFloat32 image,
            ImageFloat32 template,
            int expectedMatches) 
    {

        // initiate the template matcher
        TemplateMatching<ImageFloat32> matcher
            = FactoryTemplateMatching.createMatcher(
                    TemplateScoreType.SUM_DIFF_SQ,
                    ImageFloat32.class
            );

        // Find the points which match the template the best
        matcher.setTemplate(template, expectedMatches);
        matcher.process(image);

        List<Match> found = matcher.getResults().toList();

        ImagePoint point = new ImagePoint();
        Match match = found.get(0);
        point.setpX(match.x);
        point.setpY(match.y);

        return point;
    }

    /**
     * Get a black-and-white version of an image
     *
     * @param image input image
     * @return the resulting black-and-white image
     */
    private static BufferedImage getBinaryImage(BufferedImage image) 
    {
        // convert into a usable format
        ImageFloat32 input = ConvertBufferedImage.convertFromSingle(image, null,
                ImageFloat32.class);
        ImageUInt8 binary = new ImageUInt8(input.width, input.height);

        // Select a global threshold using Otsu's method.
        double threshold = GThresholdImageOps.computeOtsu(input, 0, 256);

        // Apply the threshold to create a binary image
        ThresholdImageOps.threshold(input, binary, (float) threshold, true);

        // remove small blobs through erosion and dilation
        ImageUInt8 filtered = BinaryImageOps.erode8(binary, 1, null);
        filtered = BinaryImageOps.dilate8(filtered, 1, null);

        BufferedImage visualFiltered = VisualizeBinaryData.renderBinary(
                filtered, null);

        return visualFiltered;
    }

    /**
     * Get filled-in questions for an exam
     *
     * @param img the image of the exam
     * @param bounds the bounds in which the exam appears in the image
     * @return a list of questions.
     */
    private static ArrayList<Question> getQuestions(BufferedImage img,
            Bounds bounds) 
    {
        ArrayList<Question> questions = new ArrayList<Question>();
        String[] letters
                = {
                    "A", "B", "C", "D", "E"
                };

        int numRows = 25;
        int numColumns = 24;
        float cellWidth = (bounds.getMaxX() - bounds.getMinX()) / (float) numColumns;
        bounds.setMinY(bounds.getMinY() + (int) cellWidth);

        // color that is definitely blank
        int black = img.getRGB(3, 3);

        // draw the bounds on the image
        //Graphics2D g = img.createGraphics();
        //g.setBackground(Color.yellow);
        //g.setStroke(new BasicStroke(1));
        //g.drawLine(bounds.max_x, 50, bounds.max_x, 150);
        //g.drawLine(bounds.min_x, 50, bounds.min_x, 150);
        //g.drawLine(20, bounds.min_y, 250, bounds.min_y);
        // go through every cell and determine if it has a bubble in it.
        for (int onColumn = 0; onColumn < numColumns; onColumn++) 
        {
            // go through every row
            for (int onRow = 0; onRow < numRows; onRow++) 
            {
                int x0 = (int) (bounds.getMinX() + onColumn * cellWidth);
                int x1 = x0 + (int) cellWidth;
                int y0 = (int) (bounds.getMinY() + onRow * cellWidth);
                int y1 = y0 + (int) cellWidth;

                int[] colors = img.getRGB(x0, y0,
                        (int) cellWidth, (int) cellWidth, null, 0,
                        (int) (cellWidth * cellWidth));

                // draw the cell grid on the image
                //g.drawLine(x0, y0, x1, y0);
                //g.drawLine(x1, y0, x1, y1);
                //g.drawLine(x1, y1, x0, y1);
                //g.drawLine(x0, y1, x0, y0);   
                double numBlacks = 0;
                // count the number of black pixels in the image
                for (int color : colors) 
                {
                    // is the color black
                    if (color == black) 
                    {
                        numBlacks++;
                    }
                }

                // if the number of black pixels is over a threshold, then it
                // is filled in.
                if (numBlacks / (cellWidth * cellWidth) < .80) 
                {
                    int colNum = onColumn + 1;
                    int rowNum = onRow + 1;

                    int startQ = 0;
                    int choice = 0;

                    // first column of questions
                    if (colNum >= 2 && colNum <= 6) 
                    {
                        startQ = 0;
                        choice = colNum - 2;
                    } // second column of questions
                    else if (colNum >= 8 && colNum <= 12) 
                    {
                        startQ = 25;
                        choice = colNum - 8;
                    } // third column of questions
                    else if (colNum >= 14 && colNum <= 18) 
                    {
                        startQ = 50;
                        choice = colNum - 14;
                    } // fourth column of questions
                    else if (colNum >= 20 && colNum <= 24) 
                    {
                        startQ = 75;
                        choice = colNum - 20;
                    }

                    // create the question
                    Question question = new Question();
                    question.setqNum(startQ + rowNum);
                    question.addChoice(letters[choice]);
                    questions.add(question);

                }
            }
        }

        Collections.sort(questions);
        return questions;
    }

    /**
     * Main function to run the grader.
     *
     * @param args command line arguments
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void main(String[] args) throws IOException 
    {
        File pdfFile = new File("Exams.pdf");
        RandomAccessFile raf = new RandomAccessFile(pdfFile, "r");
        FileChannel channel = raf.getChannel();
        MappedByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                channel.size());
        PDFFile pdf = new PDFFile(buf);
        PDFPage firstPage = pdf.getPage(0);

        BufferedImage firstPageImage = createImage(firstPage);

        // width/8.5 --> pixels per inch. PPI/4 --> .25". Anchor is 2 * .25
        int newAnchorWidth = (int) (2 * ((firstPageImage.getWidth() / 8.5) / 4));

        // Load the left anchor and scale it to match the page's dimensions.
        BufferedImage leftAnchorImage = UtilImageIO.loadImage("left_anchor.jpg");
        BufferedImage leftAnchor = new BufferedImage(newAnchorWidth,
                newAnchorWidth, BufferedImage.TYPE_BYTE_BINARY);
        Graphics graphics = leftAnchor.createGraphics();
        graphics.drawImage(
                leftAnchorImage, 0, 0, newAnchorWidth, newAnchorWidth, null);
        graphics.dispose();

        // Flip the left image to get the right anchor
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-leftAnchor.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage rightAnchor = op.filter(leftAnchor, null);

        ArrayList<Exam> exams = new ArrayList<Exam>();
        Exam key = null;

        // go through every page and grade it
        for (int onPage = 1; onPage < pdf.getNumPages() + 1; onPage++) 
        {
            PDFPage page = pdf.getPage(onPage);
            BufferedImage pageImage = createImage(page);

            // get the bounds for this page
            Bounds bounds = getFormHorizontalBounds(pageImage, leftAnchor,
                    rightAnchor);

            // get the questions
            ArrayList<Question> questions = getQuestions(getBinaryImage(
                    pageImage),
                    bounds);

            // if this is the first exam, set it as the key.
            if (onPage == 1) 
            {
                key = new Exam(questions);
                System.out.println("Answer Key");
                System.out.println("Question #\tAnswer");

                // print out every question
                for (Question question : questions)
                {
                    System.out.println(question.getqNum() + "\t\t"
                            + question.getChoices());
                }
            } 
            else 
            {
                exams.add(new Exam(questions));
            }
        }

        ArrayList<Question> answerKey = key.getAnswers();
        int onExam = 1;

        // grade every exam according to the key.
        for (Exam exam : exams) {
            System.out.println();
            System.out.println("Results for Exam" + onExam++);
            System.out.println("Question #\tAnswer\tScore");
            exam.grade(answerKey);
            ArrayList<Question> answers = exam.getAnswers();
            Collections.sort(answers);

            // print all the answers and score.
            for (Question question : answers) 
            {
                System.out.print(question.getqNum() + "\t\t");
                System.out.print(question.getChoices().isEmpty() ? "[blank]\t"
                        : question.getChoices() + "\t");
                System.out.println(question.isCorrect() ? "Correct" : "Wrong");
            }
        }
    }

}
