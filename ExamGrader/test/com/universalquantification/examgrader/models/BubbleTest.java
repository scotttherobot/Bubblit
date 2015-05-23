package com.universalquantification.examgrader.models;

import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 *
 * @author C.Y. Tan
 */
public class BubbleTest extends TestCase {
    public BubbleTest(String testName) {
        super(testName);
    }
    
    public void testIsFilled() {
        Bubble a = new Bubble(true, "A");
        Bubble b = new Bubble(false, "B");
        
        assertTrue(a.isFilled());
//        assertTrue(!b.isFilled());
    }
    
    public void testEquals() {
        Bubble a = new Bubble(true, "A");
        Bubble b = new Bubble(true, "A");
        Bubble c = new Bubble(true, "B");
        Bubble d = new Bubble(false, "B");
        
        assertTrue(a.equals(b));
        assertTrue(!b.equals(c));
        assertTrue(!c.equals(d));
    }
    
    public void testHashCode() {
        Bubble a = new Bubble(true, "A");
        
        assertEquals(a.hashCode(), a.hashCode());
    }
}
