package com.universalquantification.examgrader.reader;

import com.universalquantification.examgrader.models.Student;
import java.io.IOException;
import java.util.Map;
import java.io.Reader;

/**
 * The StudentNameMapper class represents a lookup comparison 
 * that accepts a 2D char array from OCR output, and finds closest match 
 * for a student username in the Cal Poly roster file (see format
 * <a href="http://pastebin.com/raw.php?i=QuutPx6h">here</a>.
 * @version 2.0
 * @author Santi Pierini
 */
public class StudentNameMapper
{

    private Map<Integer, String> idToNames;
    
    /**
     * Creates an instance of StudentNameMapper with a CSVReader
     * to reference the roster file from
     *
     * @param roster - the reader instance
     * @throws IOException - the exception if the Reader has invalid input
     */
    public StudentNameMapper(Reader roster) throws IOException
    {
         // SET new Roster using Reader
    }

    /**
     * getName looks for closest matching name in student roster 
     * file that matches the OCR outputs.
     *
     * @param usrName - a two dimensional array containing, for each individual
     *                  character in the username, possible choices based 
     *                  on probability.
     * @return a Student matched form roster file and OCR output
     */
    public Student getName(char[][] usrName)
    {
        // FOR EACH column in Roster CALL getRecordByColumnValue from Roster
            
            // IF length of name == # of rows in userName (length of OCR name)   
                // STORE that name from roster into an array to check later
            // ENDIF
        // ENDFOR EACH
        
        // FOR EACH name in new array
            
            // COMPARE letters in the name to the corresponding row in userName
            // CHECK for matches
            // STORE the name that matched the closest
        
        // ENDFOR EACH
        
        // GET corresponding student information from roster file
        // SET Student first name field
        // SET Student Last name field
        // SET Student ID field
        
        //RETURN Student
        return null;
    }
    
    /**
     * Updates the roster file associated with the StudentNameMapper.
     * @param reader reader to read from
     * @throws IOException - the exception if the Reader has invalid input
     */
    public void updateRoster(Reader reader) throws IOException
    {
        // SET Roster
    }
 

}
