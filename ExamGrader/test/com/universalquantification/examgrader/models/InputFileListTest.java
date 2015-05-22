package com.universalquantification.examgrader.models;

import java.io.File;
import java.util.Arrays;
import java.util.Observer;
import junit.framework.TestCase;

import static org.mockito.Mockito.*;

/**
 *
 * @author jenwang
 */
public class InputFileListTest extends TestCase {
    
    public InputFileListTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testAddInputFile() throws Exception {
        File file = new File("Exams.pdf");
        InputFileList inputFileList = new InputFileList();
        
        Observer observer = mock(Observer.class);
        inputFileList.addObserver(observer);
        inputFileList.addInputFile(file);
        
        assertEquals(inputFileList.getFiles(), Arrays.asList(file));
        assertEquals(1, inputFileList.getTotalPages());
        
        verify(observer, times(1)).update(inputFileList, null);
        
        inputFileList.deleteInputFile(0);
        verify(observer, times(2)).update(inputFileList, null);
        assertEquals(0, inputFileList.getTotalPages());
    }
    
}
