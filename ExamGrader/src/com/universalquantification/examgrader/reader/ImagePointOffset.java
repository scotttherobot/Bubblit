/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reader;

/**
 * A relative offset from the top and left for a point on an image
 *
 * @author luis
 */
public class ImagePointOffset
{
    public final double pX;
    public final double pY;

    /**
     * Create a new image point offset
     * @param px the x coordinate
     * @param py the y coordinate
     */
    public ImagePointOffset(double px, double py)
    {
        this.pX = px;
        this.pY = py;
    }

    /**
     * Get the string representation of this ImagePointOffset
     * @return the string
     */
    public String toString()
    {
        return "ImagePointOffset: (" + this.pX + ", " + this.pY + ")";

    }
}
