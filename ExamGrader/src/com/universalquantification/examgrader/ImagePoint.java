/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

/**
 * Describes a point on an image.
 *
 */
public class ImagePoint
{
    private int pX;
    private int pY;

    /**
     * Get x
     * @return x
     */
    public int getpX()
    {
        return pX;
    }
    
    /**
     * Set x
     * @param px value of x
     */
    public void setpX(int px)
    {
        this.pX = px;
    }

    /**
     * Get y
     * @return y
     */
    public int getpY()
    {
        return pY;
    }

    /**
     * Set y
     * @param py value of y
     */
    public void setpY(int py)
    {
        this.pY = py;
    }
    
    /**
     * Image point to string
     * @return string representation
     */
    public String toString()
    {
        return "point [x: " + this.pX + ",y: " + this.pY+ "] ";
    }
    
}
