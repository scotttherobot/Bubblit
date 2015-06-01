/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.ui;

import com.universalquantification.examgrader.ui.console.ConsoleView;
import com.universalquantification.examgrader.controller.Controller;
import com.universalquantification.examgrader.controller.ControllerFactory;
import com.universalquantification.examgrader.controller.ControllerFactoryTest;
import com.universalquantification.examgrader.grader.Grader;
import com.universalquantification.examgrader.grader.RosterEntry;
import com.universalquantification.examgrader.models.GradedExamCollection;
import com.universalquantification.examgrader.utils.PreferencesManager;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;


/**
 *
 * @author jenny
 */
public class ConsoleViewTest extends TestCase {
    
    private ControllerFactory controllerFactory;
    
    public ConsoleViewTest(String testName) {
        super(testName);
        
        controllerFactory = mock(ControllerFactory.class);
        when(controllerFactory.buildController(any(AppView.class))).
                thenReturn(mock(Controller.class));
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUpdate() {        
        String[] inputPaths = {
            "a"
        };
        
        StringWriter stringWriter = new StringWriter();
        Grader grader = mock(Grader.class);
        
        ConsoleView consoleView = new ConsoleView("bubblit", "roster",
                inputPaths, null, stringWriter, controllerFactory);
        
        when(grader.getPagesGraded()).thenReturn(0, 2);
        when(grader.getTotalPagesToGrade()).thenReturn(2);
        consoleView.update(grader, null);
        consoleView.update(grader, null);
        
        consoleView.checkRoster(new HashMap<File, GradedExamCollection>(),
                new ArrayList<RosterEntry>());
        
        assertEquals(stringWriter.toString(), "bubblit\n" +
            "roster validated\n" +
            "Grading a\n" +
            "0%... 100%\n" +
            "Done!\n");
        
    }
    
    public void testSettingOutputDir()
    {
          String[] inputPaths = {
            "a"
        };
        
        StringWriter stringWriter = new StringWriter();
        Grader grader = mock(Grader.class);
        
        ConsoleView consoleView = new ConsoleView("bubblit", "roster",
                inputPaths, "asdf", stringWriter, controllerFactory);
        
        assertEquals(PreferencesManager.getInstance().get(
                PreferencesManager.kOverrideDir), new File("asdf"));
        
    }
    
    public void testWritingFailed() throws IOException
    {
        Writer writer = mock(Writer.class);
        doThrow(new IOException()).when(writer).write(any(String.class));
        boolean succeeded = false;
        try
        {
            ConsoleView consoleView = new ConsoleView("bubblit", "roster",
                new String[] {}, null, writer, controllerFactory);
        }
        catch (RuntimeException e)
        {
            succeeded = true;
        }
        assertTrue(succeeded);
    }
}
