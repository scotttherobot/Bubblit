package com.universalquantification.examgrader.controller;

import com.google.common.collect.Maps;
import com.sun.pdfview.PDFParseException;
import com.universalquantification.examgrader.grader.Grader;
import com.universalquantification.examgrader.grader.GradingException;
import com.universalquantification.examgrader.models.GradedExamCollection;
import com.universalquantification.examgrader.models.InputFile;
import com.universalquantification.examgrader.models.InputFileList;
import com.universalquantification.examgrader.reporter.ReportWriter;
import com.universalquantification.examgrader.ui.AppView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static junit.framework.Assert.assertFalse;
import junit.framework.TestCase;

import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


/**
 *
 * @author jenny
 */
public class ControllerTest extends TestCase {
    
    private InputFileList inputFileList;
    private Grader grader;
    private GraderFactory graderFactory;
    private Controller controller;
    private ReportWriter reportWriter;
    private AppView view;
    
    @Override
    protected void setUp() throws Exception
    {
        grader = mock(Grader.class);
        graderFactory = mock(GraderFactory.class);
        reportWriter = mock(ReportWriter.class);
        when(graderFactory.buildNewGrader(any(InputFileList.class),
                any(List.class))).thenReturn(grader);
        view = mock(AppView.class);
        inputFileList = mock(InputFileList.class);
        
        controller = new Controller(view, inputFileList, reportWriter,
            graderFactory);
    }
    
    public void testInputFiles() throws IOException
    {
        File file = new File("asdf");
        controller.addInputFile(file);
        verify(inputFileList).addInputFile(eq(file));
    }
    
    public void testAddInputFileWithIOExceptionError() throws IOException
    {
        doThrow(new IOException()).when(
                inputFileList).addInputFile(new File("asdf"));
        controller.addInputFile(new File("asdf"));
        
        verify(view).showError(any(String.class));        
    }
    
        
    public void testAddInputFileWithPDFParseException() throws IOException
    {
        doThrow(new PDFParseException("oops")).when(
                inputFileList).addInputFile(new File("asdf"));
        controller.addInputFile(new File("asdf"));
        
        verify(view).showError(any(String.class));        
    }
    
    public void testDeleteInputFile() throws Exception
    {
        controller.deleteInputFile(0);
        verify(inputFileList).deleteInputFile(0);
    }
    
    public void testWriteReports() throws IOException
    {
        Map<File, GradedExamCollection> map = Maps.newHashMap();
        map.put(new File("foo.txt"), mock(GradedExamCollection.class));
        map.put(new File("bar.pdf"), mock(GradedExamCollection.class));
        controller.writeReports(map);
        verify(reportWriter).writeReports(map);   
    }
    
    public void testGrade() throws Exception
    {
        controller.changeRosterFile(new File("students.tsv"));
        when(inputFileList.getInputFiles()).thenReturn(
                Arrays.asList(mock(InputFile.class)));
        Map<File, GradedExamCollection> map = Maps.newHashMap();
        map.put(new File("foo.txt"), mock(GradedExamCollection.class));
        map.put(new File("bar.pdf"), mock(GradedExamCollection.class));
        when(grader.grade()).thenReturn(map);
        
        controller.grade();
        
        verify(grader).addObserver(view);
        verify(inputFileList).clear();
        verify(view).checkRoster(eq(map), any(List.class));
        verify(grader).deleteObserver(view);
    }
    
    public void testGradingFailed() throws Exception
    {
        controller.changeRosterFile(new File("students.tsv"));
        when(inputFileList.getInputFiles()).thenReturn(
                Arrays.asList(mock(InputFile.class)));
        
        when(grader.grade()).thenThrow(GradingException.class);        
        controller.grade();
        
        verify(grader).addObserver(view);
        verify(view).showError(any(String.class));
        verify(grader).deleteObserver(view);
    }
    
    public void testNoRosterEntries() throws Exception
    {
        when(inputFileList.getInputFiles()).thenReturn(
                Arrays.asList(mock(InputFile.class)));
        
        controller.grade();
        
        verify(view).showError(any(String.class));
    }
    
    public void testReportWritingFailed() throws Exception
    {
        doThrow(new IOException()).when(
                reportWriter).writeReports(any(Map.class));
        controller.writeReports(new HashMap<File, GradedExamCollection>());
        verify(view).showError(any(String.class));
         
    }
    
    public void testNoInputFiles() throws Exception
    {
        controller.changeRosterFile(new File("students.tsv"));
        controller.grade();
        verify(view).showError(any(String.class));
    }
    
    public void testChangeRosterFileNotFound() throws Exception
    {
        assertFalse(controller.changeRosterFile(new File("nonexistent")));
        verify(view).showError(any(String.class));
    }
    
    public void testChangeRosterFileFailed() throws Exception
    {
        File failingFile = mock(File.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                throw new RuntimeException("this test is a hack");
            }
        });
        
        assertFalse(controller.changeRosterFile(failingFile));
        verify(view).showError(any(String.class));
    }
    
}
