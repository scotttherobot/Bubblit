/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reader;

/**
 * A relative offset from the top and left for a point on an image
 * @author luis
 */
public class ImagePointOffset
{
    public final double x;
    public final  double y;

    public ImagePointOffset(double px, double py)
    {
        this.x = px;
        this.y = py;
    }
    public String toString()
    {
        return "ImagePointOffset: (" + this.x + ", " + this.y + ")";
            
    }
}
