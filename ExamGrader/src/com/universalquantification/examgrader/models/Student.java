package com.universalquantification.examgrader.models;

/**
 * Student represents the information for a given student. It can have a 
 * first name, last name, and identification number. A student record can also
 * be printed for convenience.
 * 
 * @author C.Y. Tan
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
        // SET the firstName field to firstName
        // SET the lastName field to lastName
        // SET the id field to id
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }
    
    /**
     * Return the first name in the student record.
     * 
     * @return The first name in the student record.
     */
    public String getFirstName() 
    {
        // RETURN firstName  
        
        return firstName;
    }
    
    /**
     * Return the last name in the student record.
     * 
     * @return The last name in the student record.
     */
    public String getLastName() 
    {
        // RETURN lastName  
        
        return lastName;
    }
    
    /**
     * Return the identification number in the student record.
     * 
     * @return The identification number in the student record.
     */
    public String getId() {
        // RETURN id  
        
        return id;
    }
    
    /**
     * Set the first name in the student record.
     * 
     * @param firstName The first name in the student record.
     */
    public void setFirstName(String firstName) { 
        // SET the firstName field to firstName
        
        this.firstName = firstName;
    }
    
    /**
     * Set the last name in the student record.
     * 
     * @param lastName The last name in the student record.
     */
    public void setlastName(String lastName) { 
        // SET the lastName field to lastName
        
        this.lastName = lastName;
    }
    
    /**
     * Set the identification number in the student record.
     * 
     * @param id The identification number in the student record.
     */
    public void setId(String id) {
        // SET the lastName id to id
        
        this.id = id;
    }
}