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
     * @param firstNameImg the image of the first name
     * @param lastNameImg the image of the last name
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

    /**
     * Construct a student given stochastic names and images of first/last
     * names.
     *
     * @param first a {@link StochasticString} retrieved from the OCR process
     * @param last a {@link StochasticString} retrieved from the OCR process
     * @param firstNameImg a {@link BufferedImage} extracted from the exam
     * @param lastNameImg a {@link BufferedImage} extracted from the exam
     */
    public Student(StochasticString first, StochasticString last,
        BufferedImage firstNameImg, BufferedImage lastNameImg)
    {
        super();
        this.stochasticFirst = first;
        this.stochasticLast = last;
        this.firstNameImage = firstNameImg;
        this.lastNameImage = lastNameImg;
    }

    /**
     * Update the {@link RosterEntry} that this student maps to.
     *
     * @param entry the entry to map to
     * @param conf the confidence that this is the entry the student maps to.
     */
    public void setRosterEntry(RosterEntry entry, Double conf)
    {
        this.firstName = entry.getFirst();
        this.lastName = entry.getLast();
        this.id = entry.getId();
        this.confidence = conf;
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
     * @param name The last name in the student record.
     */
    public void setlastName(String name)
    {
        // SET the lastName field to lastName

        this.lastName = name;
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

    /**
     * Get the {@link StochasticString} for the first name.
     *
     * @return the first name.
     */
    public StochasticString getStochasticFirst()
    {
        return this.stochasticFirst;
    }

    /**
     * Get the {@link StochasticString} for the last name.
     *
     * @return the last name.
     */
    public StochasticString getStochasticLast()
    {
        return this.stochasticLast;
    }

    /**
     * Get the image of the first name field that was written on the exam.
     *
     * @return the region of the exam that contains the first name.
     */
    public BufferedImage getFirstNameImage()
    {
        return this.firstNameImage;
    }

    /**
     * Get the image of the last name field that was written on the exam.
     *
     * @return the region of the exam that contains the last name.
     */
    public BufferedImage getLastNameImage()
    {
        return this.lastNameImage;
    }

    /**
     * Get the confidence of the match between this student and a {@link RosterEntry}
     * @return the confidence
     */
    public double getConfidence()
    {
        return confidence;
    }

    /**
     * Set the confidence of the match between this student and a {@link RosterEntry}
     * @param confidence the confidence
     */
    public void setConfidence(Double confidence)
    {
        this.confidence = confidence;
    }
}
