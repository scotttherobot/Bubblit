package com.universalquantification.examgrader.models;

/**
 * Student represents the information for a given student. It can have a 
 * first name, last name, and identification number. A student record can also
 * be printed for convenience.
 * 
 * @author Universal Quantification
 */
public class Student 
{
    private String firstName;
    private String lastName;
    private String id; // Has to be a string in case of leading 0s.
    
    /**
     * Construct an empty student record.
     */
    public Student() {}
    
    /**
     * Construct a student record given a first name, last name, and 
     * identification number.
     * 
     * @param firstName A string representing the first name of the student.
     * @param lastName A string representing the last name of the student.
     * @param id A string representing the identification number of the student.
     */
    public Student(String firstName, String lastName, String id) 
    {
    }
    
    /**
     * Return the first name in the student record.
     * 
     * @return The first name in the student record.
     */
    public String getFirstName() 
    {
        return null;    
    }
    
    /**
     * Return the last name in the student record.
     * 
     * @return The last name in the student record.
     */
    public String getLastName() 
    {
        return null;
    }
    
    /**
     * Return the identification number in the student record.
     * 
     * @return The identification number in the student record.
     */
    public String getId() {
        return null;
    }
    
    /**
     * Set the first name in the student record.
     * 
     * @param firstName The first name in the student record.
     */
    public void setFirstName(String firstName) { 
    }
    
    /**
     * Set the last name in the student record.
     * 
     * @param lastName The last name in the student record.
     */
    public void setlastName(String lastName) { 
    }
    
    /**
     * Set the identification number in the student record.
     * 
     * @param id The identification number in the student record.
     */
    public void setId(String id) { 
    }
}