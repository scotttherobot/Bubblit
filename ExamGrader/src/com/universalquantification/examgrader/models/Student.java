package com.universalquantification.examgrader.models;

import com.universalquantification.examgrader.grader.RosterEntry;
import com.universalquantification.examgrader.reader.StochasticString;
import java.awt.image.BufferedImage;

/**
 * Student represents the information for a given student. It can have a first
 * name, last name, and identification number. A student record can also be
 * printed for convenience.
 *
 * @author C.Y. Tan
 */
public class Student
{
    private String firstName;
    private String lastName;
    private String id; // Has to be a string in case of leading 0s.

    /**
     * The student's first name, as processed by the OCR software.
     */
    private final StochasticString stochasticFirst;

    /**
     * The student's last name, as processed by the OCR software.
     */
    private final StochasticString stochasticLast;

    private BufferedImage firstNameImage;
    private BufferedImage lastNameImage;
    
    private Double confidence;

    /**
     * Construct a student record given a first name, last name, and
     * identification number.
     *
     * @param firstName A string representing the first name of the student.
     * @param lastName A string representing the last name of the student.
     * @param id A string representing the identification number of the student.
     */
    public Student(String firstName, String lastName, String id,
            BufferedImage firstNameImg, BufferedImage lastNameImg)
    {
        // SET the firstName field to firstName
        // SET the lastName field to lastName
        // SET the id field to id

        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.stochasticFirst = null;
        this.stochasticLast = null;
        this.firstNameImage = firstNameImg;
        this.lastNameImage = lastNameImg;
        this.confidence = 1.0;
    }

    public Student(StochasticString first, StochasticString last,
            BufferedImage firstNameImg, BufferedImage lastNameImg)
    {
        super();
        this.stochasticFirst = first;
        this.stochasticLast = last;
        this.firstNameImage = firstNameImg;
        this.lastNameImage = lastNameImg;
    }
    
    public void setRosterEntry(RosterEntry entry, Double confidence)
    {
        this.firstName = entry.getFirst();
        this.lastName = entry.getLast();
        this.confidence = confidence;
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
    public String getId()
    {
        // RETURN id  

        return id;
    }

    /**
     * Set the first name in the student record.
     *
     * @param firstName The first name in the student record.
     */
    public void setFirstName(String firstName)
    {
        // SET the firstName field to firstName

        this.firstName = firstName;
    }

    /**
     * Set the last name in the student record.
     *
     * @param lastName The last name in the student record.
     */
    public void setlastName(String lastName)
    {
        // SET the lastName field to lastName

        this.lastName = lastName;
    }

    /**
     * Set the identification number in the student record.
     *
     * @param id The identification number in the student record.
     */
    public void setId(String id)
    {
        // SET the lastName id to id

        this.id = id;
    }

    public StochasticString getStochasticFirst()
    {
        return this.stochasticFirst;
    }

    public StochasticString getStochasticLast()
    {
        return this.stochasticLast;
    }
    
    public BufferedImage getFirstNameImage()
    {
        return this.firstNameImage;
    }
    
    public BufferedImage getLastNameImage()
    {
        return this.lastNameImage;
    }
    
    public double getConfidence()
    {
        return confidence;
    }
    
    public void setConfidence(Double confidence)
    {
        this.confidence = confidence;
    }
}
