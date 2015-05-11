package com.universalquantification.examgrader.reader;

import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.InputPage;

/**
 * Detects and parses exams.
 * @version 2.0
 * @author lcuellar
 */
public class ExamReader
{
    /**
     * The gateway that we send images to for OCR detection.
     */
    private NameRecognitionGateway nameRecognitionGateway;
    
    /**
     * The mapper to query when we get results from the gateway
     */
    private StudentNameMapper mapper;
    
    /**
     * Initializes an ExamReader with a student name mapper.
     * @param mapper mapper to use
     */
    public ExamReader(StudentNameMapper mapper)
    {
        
    }
    
    /**
     * Initializes an ExamReader with a student name mapper.
     * @param mapper mapper to use
     * @param gateway gateway to use
     */
    public ExamReader(StudentNameMapper mapper, NameRecognitionGateway gateway)
    {
        
    }
    
    /**
     * Detect and parse an exam from a given input file.
     * @param file The input page to scan
     * @param mapper the StudentNameMapper to query when determining student
     * names.
     * @return an Exam with questions and student name filled in.
     * @throws {@link InvalidExamException} when a file cannot be detected.
     */
    public Exam getExam(InputPage file, StudentNameMapper mapper) throws InvalidExamException {
        return null;
    }

}
