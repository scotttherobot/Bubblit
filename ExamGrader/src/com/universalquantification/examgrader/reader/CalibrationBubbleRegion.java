/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reader;

/**
 *
 * @author luis
 */
public class CalibrationBubbleRegion
{
    public final double minFillRatio;
    public final double avgFillRatio;
    
    public CalibrationBubbleRegion(double min, double avg)
    {
        this.minFillRatio = min;
        this.avgFillRatio = avg;
    }
}
