package com.universalquantification.examgrader.reader;

import boofcv.alg.feature.detect.template.TemplateMatching;
import boofcv.alg.filter.basic.GrayImageOps;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.ConvertBufferedImage;
import boofcv.factory.feature.detect.template.FactoryTemplateMatching;
import boofcv.factory.feature.detect.template.TemplateScoreType;
import boofcv.gui.binary.VisualizeBinaryData;
import boofcv.gui.image.ShowImages;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.feature.Match;
import boofcv.struct.image.ImageFloat32;
import boofcv.struct.image.ImageInt8;
import boofcv.struct.image.ImageSInt8;
import boofcv.struct.image.ImageUInt8;
import com.universalquantification.examgrader.models.Answer;
import com.universalquantification.examgrader.models.Bubble;
import com.universalquantification.examgrader.models.BubblitFormV2Details;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.InputPage;
import com.universalquantification.examgrader.models.Student;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Detects and parses exams.
 *
 * @version 2.0
 * @author lcuellar
 */
public class ExamReader
{
    /**
     * The gateway that we send images to for OCR detection.
     */
    private NameRecognitionGateway nameRecognitionGateway;

    /**
     * Initializes an ExamReader with a name recognition gateway.
     *
     * @param gateway gateway to use
     */
    public ExamReader(NameRecognitionGateway gateway)
    {
        this.nameRecognitionGateway = gateway;
    }

    /**
     * Detect and parse an exam from a given input file.
     *
     * @param file The input page to scan names.
     * @return an Exam with questions and student name filled in.
     * @throws
     * com.universalquantification.examgrader.reader.InvalidExamException
     */
    public Exam getExam(InputPage file) throws
            InvalidExamException
    {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        BufferedImage fileImage = file.getBufferedImage();
        fileImage = getBinaryImage(fileImage);
        //ShowImages.showDialog(fileImage);
        invertBW(fileImage);
        //ShowImages.showDialog(fileImage);

        int newAnchorWidth = BubblitFormV2Details.getAnchorWidth(fileImage.
                getWidth());
        //int newAnchorWidth = (int) (fileImage.getWidth() * 0.06);

        // load the anchors
        String rightAnchorPath = "resources/donut_right_anchor.jpg";
        URL ra = this.getClass().getResource(rightAnchorPath);
        BufferedImage rightAnchor = loadScaledImage(ra, newAnchorWidth);
        //System.out.println("This is the right anchor");
        //ShowImages.showWindow(rightAnchor, "right anchor");

        String lefttAnchorPath = "resources/donut_left_anchor.jpg";
        URL la = this.getClass().getResource(lefttAnchorPath);
        BufferedImage leftAnchor = loadScaledImage(la, newAnchorWidth);
        //System.out.println("This is the left anchor");
        //ShowImages.showWindow(leftAnchor, "left anchor");

        Bounds bounds = getFormBounds(fileImage, leftAnchor, rightAnchor);
        
        // If no anchors were found then this page is not an exam.
        if(bounds == null)
        {
            throw new InvalidExamException();
        }
        
        // If the bounds are invalid, this page is not an exam.
        if(bounds.minX >= bounds.maxX || bounds.minY >= bounds.maxY)
        {
            throw new InvalidExamException();
        }
        
//       Graphics2D graphics = fileImage.createGraphics();
//       graphics.setColor(Color.red);
//       graphics.drawLine(bounds.minX, bounds.minY, bounds.maxX, bounds.minY);
//        graphics.drawLine(bounds.minX, bounds.minY, bounds.minX, bounds.maxY);
//        graphics.drawLine(bounds.maxX, bounds.minY, bounds.maxX, bounds.maxY);
//       graphics.drawLine(bounds.minX, bounds.maxY, bounds.maxX, bounds.maxY);
        //ShowImages.showDialog(fileImage);
        BubblitFormV2Details formDetails = new BubblitFormV2Details(bounds);

        Bounds cbBounds = formDetails.getBoundsForCalibrationBubbles();

        BufferedImage calibrationRegion = fileImage.getSubimage(
                cbBounds.minX, cbBounds.minY,
                cbBounds.maxX - cbBounds.minX,
                cbBounds.maxY - cbBounds.minY);
        //ShowImages.showDialog(calibrationRegion);
        BufferedImage calibrationBubbles[] = splitImageHorizontally(
                calibrationRegion, 5);

        double fillRatio = 0;
        for (int onCb = 0; onCb < calibrationBubbles.length; onCb++)
        {
            double ratio = getBlackRatio(calibrationBubbles[onCb]);
            fillRatio = ratio > fillRatio ? ratio : fillRatio;
        }
        fillRatio *= 1.5;
        //System.out.println("fill ratio is " + fillRatio);

        char choices[] =
        {
            'A', 'B', 'C', 'D', 'E'
        };

        for (int qNum = 1; qNum <= 100; qNum++)
        {
            ArrayList<Bubble> questionBubbles = new ArrayList<Bubble>();

            Bounds qb = formDetails.getBoundsForQuestion(qNum);
            BufferedImage q = fileImage.getSubimage(
                    qb.minX, qb.minY,
                    qb.maxX - qb.minX, qb.maxY - qb.minY);

            BufferedImage bubbles[] = splitImageHorizontally(q, 5);

            for (int b = 0; b < 5; b++)
            {
                Bubble bubble;

                if (getBlackRatio(bubbles[b]) > fillRatio)
                {
                    bubble = new Bubble(true, String.valueOf(choices[b]));
                }
                else
                {
                    bubble = new Bubble(false, String.valueOf(choices[b]));
                }

                questionBubbles.add(bubble);
            }

            answers.add(new Answer(questionBubbles, qNum));
        }

        Bounds firstNameBounds = formDetails.getBoundsForFirstName();
        BufferedImage firstNameField = fileImage.getSubimage(
                firstNameBounds.minX, firstNameBounds.minY,
                firstNameBounds.maxX - firstNameBounds.minX,
                firstNameBounds.maxY - firstNameBounds.minY
        );
        //ShowImages.showDialog(firstNameField);
        Bounds lastNameBounds = formDetails.getBoundsForLastName();
        BufferedImage lastNameField = fileImage.getSubimage(
                lastNameBounds.minX, lastNameBounds.minY,
                lastNameBounds.maxX - lastNameBounds.minX,
                lastNameBounds.maxY - lastNameBounds.minY
        );
        //ShowImages.showDialog(lastNameField);
        StochasticString firstNamePossibilities = extractStochasticNameField(
                firstNameField, BubblitFormV2Details.kNumFirstNameLetters);

        StochasticString lastNamePossibilities = extractStochasticNameField(
                lastNameField, BubblitFormV2Details.kNumLastNameLetters);

        Student student = new Student(
                firstNamePossibilities, lastNamePossibilities,
                firstNameField, lastNameField
        );

        Exam exam = new Exam(answers, student, file);

        return exam;
    }

    /**
     * Given an image of a name field, extract the characters using OCR.
     *
     * @param img the image of the field
     * @param numLetters the number of letters in this field
     * @return a {@link StochasticString} representing possible names
     */
    private StochasticString extractStochasticNameField(BufferedImage img,
            int numLetters)
    {
        StochasticString str = new StochasticString();

        BufferedImage nameCharacters[] = splitImageHorizontally(img,
                numLetters, 0.08, 0.02);

        for (int i = 0; i < nameCharacters.length; i++)
        {
            //ShowImages.showDialog(nameCharacters[i]);
            removeBorders(nameCharacters[i]);
            //ShowImages.showDialog(nameCharacters[i]);
            if (getBlackRatio(nameCharacters[i]) > 0.02)
            {
                StochasticCharacter chr = this.nameRecognitionGateway.
                        detectStochasticCharacter(nameCharacters[i]);
                if(chr != null)
                {
                    str.append(chr);
                }
            }

        }

        return str;
    }

    /**
     * Get the top left corner of the exam according the the form details
     *
     * @param original the image of the entire exam
     * @return the sub image of the top left corner
     */
    private static BufferedImage getTopLeftCorner(BufferedImage original)
    {
        int height = (int) (original.getHeight()
                * BubblitFormV2Details.kCornerVerticalRatio);
        int width = (int) (original.getWidth()
                * BubblitFormV2Details.kCornerHorizontalRatio);

        BufferedImage subImage = original.getSubimage(
                0,
                0,
                width,
                height
        );
        return subImage;
    }

    /**
     * Get the bottom right corner of the exam according the the form details
     *
     * @param original the image of the entire exam
     * @return the sub image of the bottom right corner
     */
    private static BufferedImage getBottomRightCorner(BufferedImage original)
    {
        int startHorizontal = (int) (original.getWidth() * (1.0f
                - BubblitFormV2Details.kCornerHorizontalRatio));
        int startVertical
                = (int) (original.getHeight() * (1.0f
                - BubblitFormV2Details.kCornerVerticalRatio));
        int height = (int) (original.getHeight()
                * BubblitFormV2Details.kCornerVerticalRatio);
        int width = (int) (original.getWidth()
                * BubblitFormV2Details.kCornerHorizontalRatio);

        BufferedImage subImage = original.getSubimage(
                startHorizontal,
                startVertical,
                width,
                height
        );

        return subImage;
    }

    /**
     * Get the bounds in which an exam appears in an image
     *
     * @param image Image of the exam to grade
     * @param leftAnchorImage Image of the left anchor
     * @param rightAnchorImage Image of the right anchor
     * @return a bounds object with the bounds or null if the markers weren't found.
     */
    private static Bounds getFormBounds(BufferedImage image,
            BufferedImage leftAnchorImage, BufferedImage rightAnchorImage)
    {
        image = getBinaryImage(image);
        invertBW(image);
        //leftAnchorImage = getBinaryImage(leftAnchorImage);
        //rightAnchorImage = getBinaryImage(rightAnchorImage);

        // no need to search the whole document, only the corner regions.
        BufferedImage topLeftCorner = getTopLeftCorner(image);
        BufferedImage bottomRightCorner = getBottomRightCorner(image);

        // convert to usable format
        ImageUInt8 leftAnchor = new ImageUInt8(
                leftAnchorImage.getWidth(),
                leftAnchorImage.getHeight()
        );

        ImageUInt8 rightAnchor = new ImageUInt8(
                rightAnchorImage.getWidth(),
                rightAnchorImage.getHeight()
        );

        ImageUInt8 leftCorner = new ImageUInt8(
                topLeftCorner.getWidth(),
                topLeftCorner.getHeight()
        );

        ImageUInt8 rightCorner = new ImageUInt8(
                bottomRightCorner.getWidth(),
                bottomRightCorner.getHeight()
        );

        ConvertBufferedImage.convertFrom(leftAnchorImage, leftAnchor);
        ConvertBufferedImage.convertFrom(rightAnchorImage, rightAnchor);
        ConvertBufferedImage.convertFrom(topLeftCorner, leftCorner);
        ConvertBufferedImage.convertFrom(bottomRightCorner, rightCorner);

        ImagePoint leftAnchorLoc
                = getTemplateLocation(leftCorner, leftAnchor, 1);
        if(leftAnchorLoc == null)
        {
            return null;
        }
        ImagePoint rightAnchorLoc
                = getTemplateLocation(rightCorner, rightAnchor, 1);
        if(rightAnchorLoc == null)
        {
            return null;
        }
        // draw box over donut
//        Graphics2D graphics = bottomRightCorner.createGraphics();
//        graphics.setColor(Color.red);
//        graphics.drawLine(
//                rightAnchorLoc.getpX(), 
//                rightAnchorLoc.getpY(),
//                rightAnchorLoc.getpX() + 10,
//                rightAnchorLoc.getpY() + 10);
//        System.out.println("This is the bottom right cornder");
//        ShowImages.showDialog(bottomRightCorner);
        // draw box over donut
//        Graphics2D graphics2 = topLeftCorner.createGraphics();
//        graphics2.setColor(Color.red);
//        graphics2.drawLine(
//                leftAnchorLoc.getpX(), 
//                leftAnchorLoc.getpY(),
//                leftAnchorLoc.getpX() + 10,
//                leftAnchorLoc.getpY() + 10);
//        System.out.println("This is the top left corner");
//        ShowImages.showDialog(topLeftCorner);
        Bounds bounds = new Bounds(
                leftAnchorLoc.getpX(), // minx
                image.getWidth() - rightCorner.width + rightAnchorLoc.getpX()
                + rightAnchor.width, // maxx
                leftAnchorLoc.getpY(), // miny
                image.getHeight() - rightCorner.height + rightAnchorLoc.getpY()
                + rightAnchor.height// maxy
        );
        return bounds;
    }

    /**
     * Get the location of an anchor in an image
     *
     * @param image the exam to scan
     * @param template the anchor to scan for
     * @param expectedMatches the number of expected matches in the image
     * @return the ImagePoint at which the template was found or null if none were found.
     */
    private static ImagePoint getTemplateLocation(ImageUInt8 image,
            ImageUInt8 template,
            int expectedMatches)
    {

        // initiate the template matcher
        TemplateMatching<ImageUInt8> matcher
                = FactoryTemplateMatching.createMatcher(
                        TemplateScoreType.SUM_DIFF_SQ,
                        //TemplateScoreType.NCC,
                        ImageUInt8.class
                );

        // Find the points which match the template the best
        matcher.setTemplate(template, expectedMatches);
        matcher.process(image);
        //System.out.println(matcher.getResults().size);
        if(matcher.getResults().size == 0)
        {
            return null;
        }
        List<Match> found = matcher.getResults().toList();

        ImagePoint point = new ImagePoint();
        Match match = found.get(0);
        point.setpX(match.x);
        point.setpY(match.y);

        return point;
    }

    /**
     * Load a square buffered image from a URL with the given width
     *
     * @param path the URL of the file
     * @param width the width for the image
     * @return
     */
    private static BufferedImage loadScaledImage(URL path, int width)
    {
        BufferedImage image = UtilImageIO.loadImage(path);
        BufferedImage scaled = new BufferedImage(width, width,
                BufferedImage.TYPE_BYTE_BINARY);
        Graphics graphics = scaled.createGraphics();
        graphics.drawImage(image, 0, 0, width, width, null);
        graphics.dispose();
        return scaled;
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
        //double threshold = GThresholdImageOps.computeEntropy(input, 0, 256);
        //double threshold = ImageStatistics.mean(input);

        // Apply the threshold to create a binary image
        ThresholdImageOps.threshold(input, binary, (float) threshold, true);

        // remove small blobs through erosion and dilation
        //ImageUInt8 filtered = BinaryImageOps.erode8(binary, 1, null);
        //filtered = BinaryImageOps.dilate8(filtered, 1, null);
        //GrayImageOps.invert(filtered, 0, null);
        BufferedImage visualFiltered = VisualizeBinaryData.renderBinary(
                binary, null);

        return visualFiltered;
    }

    /**
     * invert a black and white image
     *
     * @param img
     */
    public static void invertBW(BufferedImage img)
    {
        int w = img.getWidth();
        int h = img.getHeight();
        int black = -16777216;
        int white = -1;

        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                if (img.getRGB(x, y) == white)
                {
                    img.setRGB(x, y, black);
                }
                else
                {
                    img.setRGB(x, y, white);
                }
            }
        }
    }

    /**
     * Split a buffered image into a given number of sub images. Removes a given amount of x and y padding.
     * @param img the image to split
     * @param numSections the number of horizontal sections to split it into
     * @param xPadding the percentage of left/right padding to remove
     * @param yPadding the percentage of top/bottom padding to remove
     * @return an array of sub image
     */
    private BufferedImage[] splitImageHorizontally(BufferedImage img,
            int numSections, double xPadding, double yPadding)
    {
        BufferedImage images[] = new BufferedImage[numSections];
        double cellWidth = img.getWidth() / (double) numSections;
        double cellHeight = img.getHeight();
        double toRemoveX = cellWidth * xPadding;
        double toRemoveY = cellWidth * yPadding;

        for (int onSection = 0; onSection < numSections; onSection++)
        {
            // remove the padding & crop
            int x0 = (int) (onSection * cellWidth + toRemoveX);
            int x1 = (int) ((onSection + 1) * cellWidth - toRemoveX);
            int y0 = (int) (0 + toRemoveY);
            int y1 = (int) (cellHeight - toRemoveX);

            images[onSection] = img.getSubimage(x0, y0, x1 - x0, y1 - y0);
        }
        return images;
    }

    /**
     * Split an image into a number of sections without removing padding
     * @param img the image to split
     * @param numSections the number of sections to split into
     * @return an array of sub images
     */
    private BufferedImage[] splitImageHorizontally(BufferedImage img,
            int numSections)
    {
        return splitImageHorizontally(img, numSections, 0, 0);
    }

    /**
     * Get the ratio of black pixels to total pixels in a buffered image
     * @param img the image to process
     * @return the ratio of black pixels
     */
    private double getBlackRatio(BufferedImage img)
    {
        double numPixels = img.getHeight() * img.getWidth();
        double numBlack = 0;
        int black = -16777216;

        for (int xp = 0; xp < img.getWidth(); xp++)
        {
            for (int yp = 0; yp < img.getHeight(); yp++)
            {
                if (img.getRGB(xp, yp) == black)
                {
                    numBlack++;
                }
            }
        }

        return numBlack / numPixels;
    }

    /**
     * Remove top and left black borders from a buffered image.
     * @param img the image to remove borders from
     */
    private void removeBorders(BufferedImage img)
    {
        int black = -16777216;
        int white = -1;
        int height = img.getHeight();
        int width = img.getWidth();
        int numBlacks = 0;
        int[] whiteRow = new int[width + height];
        int colsToClear = (int) (0.15 * width);
        int rowsToClear = (int) (0.15 * height);

        
        for (int i = 0; i < width + height; i++)
        {
            whiteRow[i] = white;
        }
        // remove the top border
        for (int onRow = 0; onRow < rowsToClear; onRow++)
        {
            numBlacks = 0;
            for (int onCol = 0; onCol < width; onCol++)
            {
                //System.out.println("num black is " + numBlacks + " and width is " + width);
                if (img.getRGB(onCol, onRow) == black)
                {
                    numBlacks++;
                }
            }

            if (numBlacks > .25 * width)
            {
                //setRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize)
                //rgbArray[offset + (y-startY)*scansize + (x-startX)];
                img.setRGB(
                        0, // startx
                        onRow, // starty
                        width, // w
                        1, // h
                        whiteRow, // rgbarray
                        0, //offset
                        1 // scansize
                );
            }
        }

        // remove the left border
        for (int onCol = 0; onCol < colsToClear; onCol++)
        {
            numBlacks = 0;
            for (int onRow = 0; onRow < height; onRow++)
            {
                if (img.getRGB(onCol, onRow) == black)
                {
                    numBlacks++;
                }
            }

            if (numBlacks > .25 * height)
            {
                //setRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize)
                //rgbArray[offset + (y-startY)*scansize + (x-startX)];
                img.setRGB(
                        onCol, // startx
                        0, // starty
                        1, // w
                        height, // h
                        whiteRow, // rgbarray
                        0, //offset
                        1 // scansize
                );
            }
        }
    }
}
