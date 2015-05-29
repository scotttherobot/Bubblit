package com.universalquantification.examgrader.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author C.Y. Tan
 */
public class AnswerTest extends TestCase {
    public AnswerTest(String testName) {
        super(testName);
    }
    
    public void testAnswerCopy() {
        List<Bubble> bubbleList = Arrays.asList(
                new Bubble(true, "A"),
                new Bubble(true, "B"),
                new Bubble(true, "C"),
                new Bubble(true, "D"),
                new Bubble(true, "E"));
        
        Answer a = new Answer(bubbleList, 16);
        Answer b = new Answer(a);
        
        assertTrue(a.equals(b));
    }
    
    public void testGetNumber() {
        List<Bubble> bubbleList = Arrays.asList(
                new Bubble(true, "A"),
                new Bubble(true, "B"),
                new Bubble(true, "C"),
                new Bubble(true, "D"),
                new Bubble(true, "E"));
        
        Answer a = new Answer(bubbleList, 16);
        
        assertEquals(a.getNumber(), 16);
    }
    
    public void testIsEmpty() {
        List<Bubble> bubbleList = new ArrayList<>();
        
        Answer a = new Answer(bubbleList, 1);
        
        assertTrue(a.isEmpty());
    }
    
    public void testCompareTo() {
        List<Bubble> bubbleListA = Arrays.asList(
                new Bubble(true, "A"),
                new Bubble(false, "B"),
                new Bubble(false, "C"),
                new Bubble(false, "D"),
                new Bubble(true, "E"));
        
        List<Bubble> bubbleListB = Arrays.asList(
                new Bubble(false, "A"),
                new Bubble(true, "B"),
                new Bubble(false, "C"),
                new Bubble(false, "D"),
                new Bubble(false, "E"));
        
        Answer a = new Answer(bubbleListA, 16);
        Answer b = new Answer(bubbleListA, 16);
        Answer c = new Answer(bubbleListA, 14);
        Answer d = new Answer(bubbleListB, 14);
        
        assertEquals(a.compareTo(b), 0);
        assertEquals(b.compareTo(c), 1);
        assertEquals(c.compareTo(b), -1);
        assertEquals(d.compareTo(c), -1);
    }
    
    public void testHashCode() {
         List<Bubble> bubbleList = Arrays.asList(
                new Bubble(true, "A"),
                new Bubble(true, "B"),
                new Bubble(true, "C"),
                new Bubble(true, "D"),
                new Bubble(true, "E"));
        
        Answer a = new Answer(bubbleList, 16);
        
        assertEquals(a.hashCode(), a.hashCode());
    }
    
    public void testEquals() {
         List<Bubble> bubbleListA = Arrays.asList(
                new Bubble(true, "A"),
                new Bubble(false, "B"),
                new Bubble(false, "C"),
                new Bubble(false, "D"),
                new Bubble(true, "E"));
        
        List<Bubble> bubbleListB = Arrays.asList(
                new Bubble(false, "A"),
                new Bubble(true, "B"),
                new Bubble(false, "C"),
                new Bubble(false, "D"),
                new Bubble(false, "E"));
        
        Answer a = new Answer(bubbleListA, 16);
        Answer b = new Answer(bubbleListA, 16);
        Answer c = new Answer(bubbleListA, 14);
        Answer d = new Answer(bubbleListB, 14);
        
        assertTrue(a.equals(b));
        assertTrue(!b.equals(c));
        assertTrue(!c.equals(b));
        assertTrue(!d.equals(c));
    }
    
     public void testToString() {
         List<Bubble> bubbleListA = Arrays.asList(
                new Bubble(true, "A"),
                new Bubble(false, "B"),
                new Bubble(false, "C"),
                new Bubble(false, "D"),
                new Bubble(true, "E"));
        
        List<Bubble> bubbleListB = Arrays.asList(
                new Bubble(false, "A"),
                new Bubble(true, "B"),
                new Bubble(false, "C"),
                new Bubble(false, "D"),
                new Bubble(false, "E"));
        
        List<Bubble> bubbleListC = Arrays.asList(
                new Bubble(true, "A"),
                new Bubble(true, "B"),
                new Bubble(true, "C"),
                new Bubble(true, "D"),
                new Bubble(true, "E"));
        
        List<Bubble> bubbleListD = Arrays.asList(
                new Bubble(false, "A"),
                new Bubble(false, "B"),
                new Bubble(false, "C"),
                new Bubble(false, "D"),
                new Bubble(false, "E"));
        
        Answer a = new Answer(bubbleListA, 10);
        Answer b = new Answer(bubbleListB, 11);
        Answer c = new Answer(bubbleListC, 12);
        Answer d = new Answer(bubbleListD, 13);

        assertEquals(a.toString(), "AE");
        assertEquals(b.toString(), "B");
        assertEquals(c.toString(), "ABCDE");
        assertEquals(d.toString(), "");
    }
}
