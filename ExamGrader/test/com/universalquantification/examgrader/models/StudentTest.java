package com.universalquantification.examgrader.models;

import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

import static com.universalquantification.examgrader.helpers.ExamHelper.createStudent;

/**
 *
 * @author C.Y. Tan
 */
public class StudentTest extends TestCase {
    public StudentTest(String testName) {
        super(testName);
    }
    
    public void testGetFirstName() {
        Student s = createStudent("Al", "B. Baque");
    
        assertTrue(s.getFirstName().equals("Al"));
    }
    
    public void testGetLastName() {
        Student s = createStudent("Wyso", "Searius");
    
        assertTrue(s.getLastName().equals("Searius"));
    }
    
    public void testGetId() {
        Student s = createStudent("Mipe", "Reshus");
    
        assertTrue(s.getId().equals("id Reshus"));
    }
    
    public void testSetId() {
        Student s = createStudent("Heeres", "Johny");
        
        s.setId("Axe");
        
        assertTrue(s.getId().equals("Axe"));
    }
}