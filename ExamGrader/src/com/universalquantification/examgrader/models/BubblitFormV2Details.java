package com.universalquantification.examgrader.models;
import com.universalquantification.examgrader.reader.Bounds;

/**
 * Represents a BubblitV2 form. It contains the magic numbers and methods to
 * retrieve sub bounds of questions and other aspects of the form.
 * @author luis
 */
public class BubblitFormV2Details
{
    private Bounds bounds;
    private double width;
    private double height;
    
    /**
     * Construct with a new Bounds in which  form is found on a page.
     * @param formBounds bounds for the form within the donuts.
     */
    public BubblitFormV2Details(Bounds formBounds)
    {
        // SET this.bounds to formBounds
        // SET this.width to formBounds.maxx - formBounds.minx
        // SET this.height to formBounds.maxy - formBounds.miny
        this.bounds = formBounds;
        this.width = formBounds.getMaxX() - formBounds.getMinX();
        this.height = formBounds.getMaxY() - formBounds.getMinY();
    }
    
    /**
     * Get the bounds in which a given question appears on a form
     * @param qNum the question number
     * @return the bounds for the given question
     */
    public Bounds getBoundsForQuestion(int qNum)
    {
        // IF qNum is between 1 and 25 THEN
          // SET colNum to 0
          // SET rowNum to qNum
        // ELSEIF qNum is between 26 and 50 THEN
          // SET colNum to 1
          // SET rowNum to qNum - 25
        // ELSEIF qNum is between 51 and 75 THEN
          // SET colNum to 2
          // SET rowNum to qNum - 50
        // ELSE 
          // set colNum to 3
          // SET rowNum to qNum - 75
        // ENDIF
        
        // SET minx to this.bounds.minx + magic number for the x start of question block + colNum * magic number for question block width
        // SET maxx to minx + magic number for question block width
        // SET miny to this.bounds.miny + magic number for the y start of question block + rowNum * magic number for question block height
        // SET maxy to miny + magic number for question block height
        
        // SET bounds to new Bounds WITH minx, max, miny, maxy
        // RETURN bounds
        return null;
    }
    
    /**
     * Get the bounds for the first name box
     * @return bounds for the first name box
     */
    public Bounds getBoundsForFirstName()
    {
        // SET minx to this.bounds.minx + magic number for xstart of firstname block
        // SET maxx to minx + magic number for width of firstname block
        // SET miny to this.bounds.miny + magic number for ystart of firstname block
        // SET maxy to miny + magic number for height of firstname block
        // SET bounds to new Bounds WITH minx, maxx, miny, maxy
        // RETURN bounds
        return null;
    }
    
     /**
     * Get the bounds for the last name box
     * @return bounds for the last name box
     */
    public Bounds getBoundsForLastName()
    {
        // SET minx to this.bounds.minx + magic number for xstart of lastname block
        // SET maxx to minx + magic number for width of firstname block
        // SET miny to this.bounds.miny + magic number for ystart of lastname block
        // SET maxy to miny + magic number for height of lastname block
        // SET bounds to new Bounds WITH minx, maxx, miny, maxy
        // RETURN bounds
        return null;
    }
    
    /**
     * Get the bounds for the calibration bubbles
     * @return bounds for the calibration bubbles
     */
    public Bounds getBoundsForCalibrationBubbles()
    {
        // SET minx to this.bounds.minx + magic number for xstart of calibration bubbles block
        // SET maxx to minx + magic number for width of calibration bubbles block
        // SET miny to this.bounds.miny + magic number for ystart of calibration bubbles  block
        // SET maxy to miny + magic number for height of calibration bubbles block
        // SET bounds to new Bounds WITH minx, maxx, miny, maxy
        // RETURN bounds
        return null;
    }
}
