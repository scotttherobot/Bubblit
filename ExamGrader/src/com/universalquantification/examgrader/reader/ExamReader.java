package com.universalquantification.examgrader.reader;

import boofcv.struct.image.ImageFloat32;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.InputPage;
import java.awt.image.BufferedImage;

/**
 * Detects and parses exams.
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
     * The mapper to query when we get results from the gateway
     */
    private StudentNameMapper mapper;
    
    /**
     * Initializes an ExamReader with a student name mapper.
     * @param mapper mapper to use
     */
    public ExamReader(StudentNameMapper mapper)
    {
        // SET this.mapper TO mapper
    }
    
    /**
     * Initializes an ExamReader with a student name mapper.
     * @param mapper mapper to use
     * @param gateway gateway to use
     */
    public ExamReader(StudentNameMapper mapper, NameRecognitionGateway gateway)
    {
        // SET this.mapper TO mapper
        // SET this.gateway TO gateway
    }
    
    /**
     * Detect and parse an exam from a given input file.
     * @param file The input page to scan
     * @param mapper the StudentNameMapper to query when determining student
     * names.
     * @return an Exam with questions and student name filled in.
     * @throws {@link InvalidExamException} when a file cannot be detected.
     */
    public Exam getExam(InputPage file, StudentNameMapper mapper) throws InvalidExamException {
        // SET exam to empty Exam
        // SET answerLIST to empty list
        // SET student to empty student
        // SET fileImage to file.getBufferedImage()
        // SET newAnchorWidth to the width of the donut anchor relative to the page
        
        // SET leftAnchorImage to the left donut image from resources
        // SET rightAnchorImage to the right donut image from resources
        
        // CALL getFormBounds(fileImage, leftAnchorImage, rightAnchorImage)
        // SET bounds to the result of getFormBounds
        // SET examImage to the subImage fromFileImage in bounds

        // SET examOffsets to new BubblitFormV2Details object to give us offsets
        // at which we may find the various form aspects with bounds as argument
        
        // SET bubbleImages to the subImage of fileImage in bounds
        //   examOffsets.getCalibrationBubbleBounds()
        // SET fillRatio to 0
        
        // FOR each bubble in bubbleImages
           // SET bubbleFillRatio to CALL getBlackRatio(bubble)
           // ADD bubbleFillRatio to fillRatio
        // ENDFOR
        
        // SET fillRatio = fillRatio/number of calibration bubbles

        // FOR onColumn = 0 TO 4
          // FOR onRow = 0 TO 25
            // SET qNum = onColumn * 25 + onRow
            // SET answer to new Answer
            // SET bubbleImages = subImage of fileImage in bounds examOffsets.getQuestionBounds(qNum)
            // FOR EACH bubble in bubbleImages
               // SET choiceBubble to new Bubble
               // SET ratio to CALL getBlackRatio(bubble)
               // IF ratio > fillRation THEN
                 // SET bubble.filledIn = TRUE
               // ELSE
                 // SET bubble.filledIn = FALSE
               // ENDIF
               // ADD bubble to answer
            // ENDFOR
            // ADD answer to answerList
        // ENDFOR
        
        // SET firstNameLetters to subImage from fileImage with bounds
        // examBounds.getFirstNameBounds()
        
        // SET lastNameLetters to subImage from fileImage with bounds
        // examBounds.getLastNameBounds()
        
        // SET usernameLetters to subImage from fileImage with bounds
        // examBounds.getUsernameBounds()
        
        // SET username to mapper.detectName(usernameLetters)
        
        // SET exam.username to username
        
        // SET exam.answers to answerList
        
        // RETURN exam
        return null;
    }
    
    /**
     * Get the bounds in which an exam appears in an image
     *
     * @param image Image of the exam to grade
     * @param leftAnchorImage Image of the left anchor
     * @param rightAnchorImage Image of the right anchor
     * @return a bounds object with the bounds
     */
    private static Bounds getFormBounds(BufferedImage image,
            BufferedImage leftAnchorImage, BufferedImage rightAnchorImage)
    {
        // SET topLeftCorner to CALL getTopLeftCorner(image)
        // SET bottomRightCorner to CALL getBottomRightCorner(image)
        
        // SET leftAnchorLoc to CALL getTemplateLocation(topLeftCorner, leftAnchorImage)
        // SET rightAnchorLoc to CALL getTemplateLocation(bottomRightCorner, rightAnchorImage)
        
        // SET bounds to new Bounds with
        //   minimum X as leftAnchorLoc.x
        //   maximum X as image.width + bottomRightCorner.width + rightAnchorLoc.x
        //   minimum Y as leftAnchorLoc.y
        //   maximum Y as image.height + bottomRightCorner.height + rightAnchorLoc.y
        
        // RETURN bounds
        
        return null;
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
        // SET matches to BoofCV.findTemplate(image, template)
        // SET match to first match in matches
        // SET imagepoint to new ImagePoint
        // SET imagepoint.x to match.x
        // SET imagepoint.y to match.y
        // RETURN imagepoint
        return null;
    }
    
    /**
     * Returns the ratio of black pixels to white pixels in an image
     * @param image the input image
     * @return the ratio of black pixels
     */
    private static float getBlackRatio(BufferedImage image)
    {
        // SET numBlacks to 0
        // SET pixels to image.getRGB(0,0,image.height, image.width)
        // FOR every pixel in pixels
          // IF pixel is black THEN
            // INCREMENT numBlacks
          // ENDIF
        // ENDFOR
        
        // RETURN numBlacks / pixels.size()
        
        return 0;
    }
}
