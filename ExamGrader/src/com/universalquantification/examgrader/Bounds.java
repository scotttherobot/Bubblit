/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

/**
 * Describes bounds for an exam in an image.
 *
 */
class Bounds
{
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    public int getMinX()
    {
        return minX;
    }

    public void setMinX(int minX)
    {
        this.minX = minX;
    }

    public int getMaxX()
    {
        return maxX;
    }

    public void setMaxX(int maxX)
    {
        this.maxX = maxX;
    }

    public int getMinY()
    {
        return minY;
    }

    public void setMinY(int minY)
    {
        this.minY = minY;
    }
    public int getMaxY()
    {
        return maxY;
    }

    public void setMaxY(int maxY)
    {
        this.maxY = maxY;
    }
    public String toString()
    {
        return "Bounds: [minX " + this.minX + "]"
            + "[maxX " + this.maxX + "]"
            + "[minY " + this.minY + "]"
            + "[maxY " + this.maxY + "]";
            
    }
}
