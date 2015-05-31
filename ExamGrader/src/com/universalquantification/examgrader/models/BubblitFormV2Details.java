package com.universalquantification.examgrader.models;

import com.universalquantification.examgrader.reader.ImagePointOffset;
import com.universalquantification.examgrader.reader.Bounds;

/**
 * Represents a BubblitV2 form. It contains the magic numbers and methods to
 * retrieve sub bounds of questions and other aspects of the form.
 *
 * @author luis
 */
public class BubblitFormV2Details
{
    private final Bounds bounds;
    private final double width;
    private final double height;
    private final double questionHeightRatio = 0.0248711340206;
    private final double questionWidthRatio = 0.148683092608;

    private final double questionHeight;
    private final double questionWidth;

    private final ImagePointOffset calibrationBubbles = new ImagePointOffset(
        0.425658453696, 0.245201030928);

    private final ImagePointOffset firstName = new ImagePointOffset(
        0.535559898046, 0.0776417525773);
    private final double firstNameHeightRatio = 0.0257731958763;
    private final double firstNameWidthRatio = 0.363636363636;
    private final double firstNameHeight;
    private final double firstNameWidth;

    private final ImagePointOffset lastName = new ImagePointOffset(
        0.535559898046, 0.11243556701);
    private double lastNameHeightRatio = 0.0257731958763;
    private double lastNameWidthRatio = 0.363636363636;
    private final double lastNameHeight;
    private final double lastNameWidth;

    /**
     * The number of letters in the first name field.
     */
    public static final int kNumFirstNameLetters = 12;

    /**
     * The number of letters in the last name field.
     */
    public static final int kNumLastNameLetters = 12;

    /**
     * The vertical ratio of the corner in which a donut exists.
     */
    public static final float kCornerVerticalRatio = 0.11f;

    /**
     * The horizontal ratio of the corner in which a donut exists.
     */
    public static final float kCornerHorizontalRatio = 0.25f;

    /**
     * The slope between the two donuts. Used for rotation detection
     */
    
    public static final double kDonutSlope = (3202.0 - 98.0) / (2452.0 - 98.0);
    
    // These are all relative to where the donuts appear
    private final ImagePointOffset[] questionOffsets =
    {
        null,
        new ImagePointOffset(0.114698385726, 0.283182989691),
        new ImagePointOffset(0.114698385726, 0.30831185567),
        new ImagePointOffset(0.114698385726, 0.333440721649),
        new ImagePointOffset(0.114698385726, 0.358569587629),
        new ImagePointOffset(0.114698385726, 0.383698453608),
        new ImagePointOffset(0.114698385726, 0.408827319588),
        new ImagePointOffset(0.114698385726, 0.433956185567),
        new ImagePointOffset(0.114698385726, 0.459085051546),
        new ImagePointOffset(0.114698385726, 0.484213917526),
        new ImagePointOffset(0.114698385726, 0.509342783505),
        new ImagePointOffset(0.114698385726, 0.534471649485),
        new ImagePointOffset(0.114698385726, 0.559600515464),
        new ImagePointOffset(0.114698385726, 0.584729381443),
        new ImagePointOffset(0.114698385726, 0.609858247423),
        new ImagePointOffset(0.114698385726, 0.634987113402),
        new ImagePointOffset(0.114698385726, 0.660115979381),
        new ImagePointOffset(0.114698385726, 0.685244845361),
        new ImagePointOffset(0.114698385726, 0.71037371134),
        new ImagePointOffset(0.114698385726, 0.73550257732),
        new ImagePointOffset(0.114698385726, 0.760631443299),
        new ImagePointOffset(0.114698385726, 0.785760309278),
        new ImagePointOffset(0.114698385726, 0.810889175258),
        new ImagePointOffset(0.114698385726, 0.836018041237),
        new ImagePointOffset(0.114698385726, 0.861146907216),
        new ImagePointOffset(0.114698385726, 0.886275773196),
        new ImagePointOffset(0.332625318607, 0.283182989691),
        new ImagePointOffset(0.332625318607, 0.30831185567),
        new ImagePointOffset(0.332625318607, 0.333440721649),
        new ImagePointOffset(0.332625318607, 0.358569587629),
        new ImagePointOffset(0.332625318607, 0.383698453608),
        new ImagePointOffset(0.332625318607, 0.408827319588),
        new ImagePointOffset(0.332625318607, 0.433956185567),
        new ImagePointOffset(0.332625318607, 0.459085051546),
        new ImagePointOffset(0.332625318607, 0.484213917526),
        new ImagePointOffset(0.332625318607, 0.509342783505),
        new ImagePointOffset(0.332625318607, 0.534471649485),
        new ImagePointOffset(0.332625318607, 0.559600515464),
        new ImagePointOffset(0.332625318607, 0.584729381443),
        new ImagePointOffset(0.332625318607, 0.609858247423),
        new ImagePointOffset(0.332625318607, 0.634987113402),
        new ImagePointOffset(0.332625318607, 0.660115979381),
        new ImagePointOffset(0.332625318607, 0.685244845361),
        new ImagePointOffset(0.332625318607, 0.71037371134),
        new ImagePointOffset(0.332625318607, 0.73550257732),
        new ImagePointOffset(0.332625318607, 0.760631443299),
        new ImagePointOffset(0.332625318607, 0.785760309278),
        new ImagePointOffset(0.332625318607, 0.810889175258),
        new ImagePointOffset(0.332625318607, 0.836018041237),
        new ImagePointOffset(0.332625318607, 0.861146907216),
        new ImagePointOffset(0.332625318607, 0.886275773196),
        new ImagePointOffset(0.550552251487, 0.283182989691),
        new ImagePointOffset(0.550552251487, 0.30831185567),
        new ImagePointOffset(0.550552251487, 0.333440721649),
        new ImagePointOffset(0.550552251487, 0.358569587629),
        new ImagePointOffset(0.550552251487, 0.383698453608),
        new ImagePointOffset(0.550552251487, 0.408827319588),
        new ImagePointOffset(0.550552251487, 0.433956185567),
        new ImagePointOffset(0.550552251487, 0.459085051546),
        new ImagePointOffset(0.550552251487, 0.484213917526),
        new ImagePointOffset(0.550552251487, 0.509342783505),
        new ImagePointOffset(0.550552251487, 0.534471649485),
        new ImagePointOffset(0.550552251487, 0.559600515464),
        new ImagePointOffset(0.550552251487, 0.584729381443),
        new ImagePointOffset(0.550552251487, 0.609858247423),
        new ImagePointOffset(0.550552251487, 0.634987113402),
        new ImagePointOffset(0.550552251487, 0.660115979381),
        new ImagePointOffset(0.550552251487, 0.685244845361),
        new ImagePointOffset(0.550552251487, 0.71037371134),
        new ImagePointOffset(0.550552251487, 0.73550257732),
        new ImagePointOffset(0.550552251487, 0.760631443299),
        new ImagePointOffset(0.550552251487, 0.785760309278),
        new ImagePointOffset(0.550552251487, 0.810889175258),
        new ImagePointOffset(0.550552251487, 0.836018041237),
        new ImagePointOffset(0.550552251487, 0.861146907216),
        new ImagePointOffset(0.550552251487, 0.886275773196),
        new ImagePointOffset(0.768479184367, 0.283182989691),
        new ImagePointOffset(0.768479184367, 0.30831185567),
        new ImagePointOffset(0.768479184367, 0.333440721649),
        new ImagePointOffset(0.768479184367, 0.358569587629),
        new ImagePointOffset(0.768479184367, 0.383698453608),
        new ImagePointOffset(0.768479184367, 0.408827319588),
        new ImagePointOffset(0.768479184367, 0.433956185567),
        new ImagePointOffset(0.768479184367, 0.459085051546),
        new ImagePointOffset(0.768479184367, 0.484213917526),
        new ImagePointOffset(0.768479184367, 0.509342783505),
        new ImagePointOffset(0.768479184367, 0.534471649485),
        new ImagePointOffset(0.768479184367, 0.559600515464),
        new ImagePointOffset(0.768479184367, 0.584729381443),
        new ImagePointOffset(0.768479184367, 0.609858247423),
        new ImagePointOffset(0.768479184367, 0.634987113402),
        new ImagePointOffset(0.768479184367, 0.660115979381),
        new ImagePointOffset(0.768479184367, 0.685244845361),
        new ImagePointOffset(0.768479184367, 0.71037371134),
        new ImagePointOffset(0.768479184367, 0.73550257732),
        new ImagePointOffset(0.768479184367, 0.760631443299),
        new ImagePointOffset(0.768479184367, 0.785760309278),
        new ImagePointOffset(0.768479184367, 0.810889175258),
        new ImagePointOffset(0.768479184367, 0.836018041237),
        new ImagePointOffset(0.768479184367, 0.861146907216),
        new ImagePointOffset(0.768479184367, 0.886275773196)

    };

    /**
     * Construct with a new Bounds in which form is found on a page.
     *
     * @param bounds bounds for the form within the donuts.
     */
    public BubblitFormV2Details(Bounds bounds)
    {
        this.width = bounds.maxX - bounds.minX;
        this.height = bounds.maxY - bounds.minY;
        this.bounds = bounds;
        this.questionHeight = this.height * this.questionHeightRatio;
        this.questionWidth = this.width * this.questionWidthRatio;
        this.firstNameHeight = this.height * this.firstNameHeightRatio;
        this.firstNameWidth = this.width * this.firstNameWidthRatio;
        this.lastNameHeight = this.height * this.lastNameHeightRatio;
        this.lastNameWidth = this.width * this.lastNameWidthRatio;
    }

    /**
     * Get the bounds in which a given question appears on a form
     *
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

        // SET minx to this.bounds.minx + magic number for the x start of 
        // question block + colNum * magic number for question block width
        // SET maxx to minx + magic number for question block width
        // SET miny to this.bounds.miny + magic number for the y start of 
        // question block + rowNum * magic number for question block height
        // SET maxy to miny + magic number for question block height
        // SET bounds to new Bounds WITH minx, max, miny, maxy
        // RETURN bounds
        int minx = (int) (this.bounds.minX + (this.width
            * this.questionOffsets[qNum].x));
        int maxx = (int) (minx + this.questionWidth);
        int miny = (int) (this.bounds.minY + (this.height
            * this.questionOffsets[qNum].y));
        int maxy = (int) (miny + this.questionHeight);

        return new Bounds(
            minx, maxx, miny, maxy
        );
    }

    /**
     * Get the bounds for the first name box
     *
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
        int minx = (int) (this.bounds.minX + (this.width
            * this.firstName.x));
        int maxx = (int) (minx + this.firstNameWidth);
        int miny = (int) (this.bounds.minY + (this.height
            * this.firstName.y));
        int maxy = (int) (miny + this.firstNameHeight);

        return new Bounds(
            minx, maxx, miny, maxy
        );
    }

    /**
     * Get the bounds for the last name box
     *
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
        int minx = (int) (this.bounds.minX + (this.width
            * this.lastName.x));
        int maxx = (int) (minx + this.lastNameWidth);
        int miny = (int) (this.bounds.minY + (this.height
            * this.lastName.y));
        int maxy = (int) (miny + this.lastNameHeight);

        return new Bounds(
            minx, maxx, miny, maxy
        );
    }

    /**
     * Get the bounds for the calibration bubbles
     *
     * @return bounds for the calibration bubbles
     */
    public Bounds getBoundsForCalibrationBubbles()
    {
        // SET minx to this.bounds.minx + magic number for xstart of calibration 
        // bubbles block
        // SET maxx to minx + magic number for width of calibration bubbles block
        // SET miny to this.bounds.miny + magic number for ystart of calibration 
        // bubbles  block
        // SET maxy to miny + magic number for height of calibration bubbles block
        // SET bounds to new Bounds WITH minx, maxx, miny, maxy
        // RETURN bounds
        int minx = (int) (this.bounds.minX + (this.width
            * this.calibrationBubbles.x));
        int maxx = (int) (minx + this.questionWidth);
        int miny = (int) (this.bounds.minY + (this.height
            * this.calibrationBubbles.y));
        int maxy = (int) (miny + this.questionHeight);

        return new Bounds(
            minx, maxx, miny, maxy
        );
    }

    /**
     * Get the width of a donut relative to the width of the page.
     *
     * @param width the width of the document
     * @return the width of the donut
     */
    public static int getAnchorWidth(int width)
    {
        return (int) (width * 0.061176);
    }
}
