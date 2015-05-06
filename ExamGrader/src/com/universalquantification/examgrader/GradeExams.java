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
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * Class to grade exams.
 *
 * @author luis
 */
public class GradeExams extends SwingWorker<Void, Void>
{
    private ArrayList<String> paths;
    private String csvPath;

    public GradeExams(ArrayList<String> filePaths, String csvPath)
    {
        this.paths = filePaths;
        if (csvPath != null)
        {
            this.csvPath = csvPath;
        }
    }

    /*
     * Executed in event dispatching thread
     */
    @Override
    public void done()
    {
        String completeMessage
                = "Grading complete! You can find the score files"
                + "in the original directories of your graded PDF files."
                + "\n\n The directories are:\n\n";

        for (String s : paths)
        {
            completeMessage += ("\n" + s);
        }

        JOptionPane.showMessageDialog(null, completeMessage);
    }

    /**
     * Returns an image for a pdf page.
     *
     * @param page a PDFPage
     * @return a BufferedImage
     */
    public static BufferedImage createImageFromPDF(PDFPage page)
    {
        // Size of the image to process. Smaller means faster, but less precise.
        int width = 1003;
        int height = 1298;

        // create the bounding rectangle and get the image
        Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(),
                (int) page.getBBox().getHeight());
        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Image image = page.getImage(width, height, rect, null, true, true);
        Graphics2D bufImageGraphics = bufferedImage.createGraphics();
        bufImageGraphics.drawImage(image, 0, 0, null);
        bufImageGraphics.dispose();

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
    private static Bounds getFormBounds(ImageFloat32 image,
            ImageFloat32 leftAnchor, ImageFloat32 rightAnchor)
    {
        ImagePoint leftAnchorLoc = getTemplateLocation(image, leftAnchor, 1);
        ImagePoint rightAnchorLoc = getTemplateLocation(image, rightAnchor, 1);

        Bounds bounds = new Bounds();
        bounds.setMinX(leftAnchorLoc.getpX());
        bounds.setMaxX(rightAnchorLoc.getpX() + rightAnchor.width);
        bounds.setMaxY(leftAnchorLoc.getpY() + leftAnchor.height);
        bounds.setMinY((int) (rightAnchorLoc.getpY() + 1.5 * leftAnchor.height));

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
    private static Bounds getFormBounds(BufferedImage image,
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

        return getFormBounds(src, left, right);
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
    private static Exam detectExam(BufferedImage img,
            Bounds bounds)
    {
        ArrayList<Question> questions;
        HashMap<Integer, Question> qmap = new HashMap<Integer, Question>();
        int[] studentID = new int[9];
        String[] letters
                =
                {
                    "A", "B", "C", "D", "E"
                };

        int numRows = 35;
        int numColumns = 27;
        float cellWidth = (bounds.getMaxX() - bounds.getMinX())
                / (float) numColumns;
        float cellHeight = (bounds.getMaxY() - bounds.getMinY())
                / (float) numRows;

        // color that is definitely blank
        int black = img.getRGB(3, 3);

        // go through every cell and determine if it has a bubble in it.
        for (double onColumn = 0; onColumn < numColumns; onColumn++)
        {
            // go through every row
            for (double onRow = 0; onRow < numRows; onRow++)
            {
                int x0 = (int) (bounds.getMinX() + onColumn * cellWidth);
                int x1 = x0 + (int) cellWidth;
                int y0 = (int) (bounds.getMinY() + onRow * cellHeight);
                int y1 = y0 + (int) cellHeight;

                int[] colors = img.getRGB(x0, y0,
                        (int) cellWidth, (int) cellHeight, null, 0,
                        (int) (cellWidth));

                // un comment draw the cell grid on the image
                //Graphics2D graphics = img.createGraphics();
                //graphics.drawLine(x0, y0, x1, y0);
                //graphics.drawLine(x1, y0, x1, y1);
                //graphics.drawLine(x1, y1, x0, y1);
                //graphics.drawLine(x0, y1, x0, y0);
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
                if (numBlacks / (cellWidth * cellWidth) < .85)
                {
                    int colNum = (int) onColumn + 1;
                    int rowNum = (int) onRow + 1;

                    int startQ = 0;
                    int choice = 0;

                    boolean valid = false;

                    // first column of questions
                    if (colNum >= 11 && colNum <= 15)
                    {
                        startQ = 0;
                        choice = colNum - 11;
                        valid = true;
                    } // second column of questions
                    else if (colNum >= 17 && colNum <= 21)
                    {
                        startQ = 35;
                        choice = colNum - 17;
                        valid = true;
                    } // third column of questions
                    else if (colNum >= 23 && colNum <= 27)
                    {
                        startQ = 70;
                        choice = colNum - 23;
                        valid = true;
                    } // the user id field
                    else if (colNum >= 1 && colNum <= 9 && rowNum >= 11
                            && rowNum <= 20)
                    {
                        studentID[colNum - 1] = rowNum - 11;
                    }

                    // create the question
                    int qNum = startQ + rowNum;
                    if (valid)
                    {
                        Question question;
                        if (qmap.containsKey(qNum))
                        {
                            question = qmap.get(qNum);
                        }
                        else
                        {
                            question = new Question();
                        }
                        question.setqNum(startQ + rowNum);
                        question.addChoice(letters[choice]);
                        qmap.put(qNum, question);
                    }

                    // uncomment below to draw a red line through
                    // candidate blobs
//                    Graphics2D graphics = img.createGraphics();
//                    graphics.setColor(Color.red);
//                    graphics.drawLine(x0, y0, x1, y1);
                }
            }
        }
        questions = new ArrayList<Question>(qmap.values());
        Collections.sort(questions);
        Exam exam = new Exam(questions);
        exam.setStudentID(studentID);

        // uncomment below to write out the b&w image with grid lines/blobs
//        File outputfile = new File("image" + exam.getStudentID() + ".jpg");
//        try
//        {
//            ImageIO.write(img, "jpg", outputfile);
//        }
//        catch (IOException ex)
//        {
//            Logger.getLogger(GradeExams.class.getName()).log(Level.SEVERE, null,
//                    ex);
//        }
        return exam;
    }

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

    private static PDFFile loadPDF(String path) throws FileNotFoundException,
            IOException
    {
        File pdfFile = new File(path);
        RandomAccessFile raf = new RandomAccessFile(pdfFile, "r");
        FileChannel channel = raf.getChannel();
        MappedByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                channel.size());
        return new PDFFile(buf);
    }

    /**
     * Grade every PDF in paths.
     *
     * @param paths list of paths to PDFs
     */
    @Override
    public Void doInBackground()
    {
        int progress = 2;
        setProgress(progress);
        int fileDeltaProgress = 98 / paths.size();
        CSVReader studentReader = null;

        if (this.csvPath != null)
        {
            try
            {
                studentReader = new CSVReader(this.csvPath);
                //studentReader.
            }
            catch (Exception ex)
            {
                System.out.println("Error loading CSV file" + ex.getMessage());
            }
        }

        for (String path : paths)
        {
            System.out.println("Process " + path);
            Path pathInfo = Paths.get(path);
            Path parentPath = pathInfo.getParent();
            String sep = pathInfo.getFileSystem().getSeparator();
            String outPath = parentPath.toAbsolutePath() + sep + pathInfo.
                    getFileName().toString().split("\\.")[0];
            System.out.println("outpat hsi " + outPath);

            PDFFile file = null;

            // load the pdf file
            try
            {
                file = loadPDF(path);
            }
            catch (IOException ex)
            {
                Logger.getLogger(GradeExams.class.getName()).log(Level.SEVERE,
                        "File " + path + " Not Found", ex);
                System.exit(1);
            }

            int pageDeltaProgress = fileDeltaProgress / (file.getNumPages() * 2);

            // get the first page to set dimensions.
            BufferedImage firstPageImage = createImageFromPDF(file.getPage(0));
            int newAnchorWidth = (int) (2 * ((firstPageImage.getWidth() / 8.5)
                    / 4));

            // load the anchors
            String rightAnchorPath = "resources/donut_right_anchor.jpg";
            URL ra = this.getClass().getResource(rightAnchorPath);
            BufferedImage rightAnchor = loadScaledImage(ra, newAnchorWidth);

            String lefttAnchorPath = "resources/donut_left_anchor.jpg";
            URL la = this.getClass().getResource(lefttAnchorPath);
            BufferedImage leftAnchor = loadScaledImage(la, newAnchorWidth);

            // the list of graded exams and the answer key
            ArrayList<Exam> exams = new ArrayList<Exam>();
            Exam key = null;

            // go through every page in the pdf and grade the exam on it.
            int numPages = file.getNumPages() + 1;
            for (int onPage = 1; onPage < numPages; onPage++)
            {
                PDFPage page = file.getPage(onPage);
                BufferedImage pageImage = createImageFromPDF(page);

                // get the bounds for this page
                Bounds bounds = getFormBounds(pageImage, leftAnchor,
                        rightAnchor);

                // get the questions and student ID
                Exam exam = detectExam(getBinaryImage(pageImage), bounds);
                // if this is the first exam, set it as the key.
                if (onPage == 1)
                {
                    key = exam;
                    System.out.println("Answer Key: " + exam.toString());
                }
                else
                {
                    exams.add(exam);
                }

                progress += pageDeltaProgress;
                setProgress(progress);
            }

            // set up the answer key for grading
            ArrayList<Question> answerKey = key.getAnswers();

            String[] scoreCols =
            {
                "ID", "Name", "Correct"
            };

            List<String[]> results = new ArrayList<>();

            // grade every exam according to the key.
            for (Exam exam : exams)
            {
                exam.grade(answerKey);
                System.out.println("Exam: " + exam.toString(true));
                // CSV score reporting
                Integer raw = exam.rawScore();
                String name;
                if (studentReader != null)
                {
                    HashMap<String, String> studentRecord = studentReader.
                            getRecordByColumnValue("ID", Integer.toString(
                                            exam.getStudentID()));
                    name = studentRecord.get("Name");
                }
                else
                {
                    name = "Student " + exam.getStudentID();
                }

                String[] result =
                {
                    String.valueOf(exam.getStudentID()), name, raw.toString()
                };
                results.add(result);

                progress += pageDeltaProgress;
                setProgress(progress);
            }

            writeCSV(outPath + "_scores_by_scan_order.csv",
                    new Report(scoreCols, results));

            Collections.sort(results, new Comparator<String[]>()
            {
                public int compare(String[] a, String[] b)
                {
                    return Integer.parseInt(a[0]) - Integer.parseInt(b[0]);
                }
            });

            writeCSV(outPath + "_scores_by_id.csv",
                    new Report(scoreCols, results));

            Report statsReport = new StatsReporter().getReport(
                    exams, answerKey);
            writeCSV(outPath + "_stats.csv", statsReport);

        }
        setProgress(100);
        return null;
    }

    private void writeCSV(String filename, Report report)
    {
        CSVWriter writer = new CSVWriter(filename);
        writer.setColumns(report.headers);
        for (String[] row : report.rows)
        {
            writer.addLine(row);
        }
        writer.writeFile();
    }
}
