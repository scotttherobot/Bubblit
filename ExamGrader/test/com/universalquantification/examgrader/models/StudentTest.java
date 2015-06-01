package com.universalquantification.examgrader.models;

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
        s.setFirstName("Yule");
        assertEquals(s.getFirstName(), "Yule");
    }
    
    public void testGetLastName() {
        Student s = createStudent("Wyso", "Searius");
    
        assertTrue(s.getLastName().equals("Searius"));
        s.setlastName("Sirius");
        assertEquals(s.getLastName(), "Sirius");
    }
    
    public void testGetId() {
        Student s = createStudent("Mipe", "Reshus");
    
        assertTrue(s.getId().equals("id Reshus"));;
    }
    
    public void testSetId() {
        Student s = createStudent("Heeres", "Johny");
        
        s.setId("Axe");
        
        assertTrue(s.getId().equals("Axe"));
    }
    
    public void testSetConfidence() {
        Student s = createStudent("asdf", "asdf");
        s.setConfidence(1.99);
        assertEquals(s.getConfidence(), 1.99, 0.01);
    }
    
    public void testImages() {
        Student s = createStudent("asdf", "asdf");
        assertNotNull(s.getFirstNameImage());
        assertNotNull(s.getLastNameImage());
    }
}