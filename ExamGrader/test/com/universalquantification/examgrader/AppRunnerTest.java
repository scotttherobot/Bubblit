/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

import com.universalquantification.examgrader.AppRunner.ViewInitializer;
import junit.framework.TestCase;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 *
 * @author jenny
 */
public class AppRunnerTest extends TestCase {
    
    public AppRunnerTest(String testName) {
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

    public void testCommandLine() throws Exception
    {
        ViewInitializer initializer = mock(ViewInitializer.class);
        AppRunner runner = new AppRunner(initializer);
        runner.run("-i file1.pdf file2.pdf -o output -r roster".split(" "));
        
        verify(initializer).runCli(AppRunner.kNameAndVersion, "output",
                "roster", new String[] {"file1.pdf", "file2.pdf"});
                
    }
    
    public void testGui() throws Exception
    {
        ViewInitializer initializer = mock(ViewInitializer.class);
        AppRunner runner = new AppRunner(initializer);
        runner.run(new String[] {});
        
        verify(initializer).runGui();
    }
    
    public void testCliNoRoster() throws Exception
    {
        ViewInitializer initializer = mock(ViewInitializer.class);
        AppRunner runner = new AppRunner(initializer);
        runner.run("-i file1.pdf file2.pdf -o output".split(" "));
        verify(initializer, never()).runGui();
        verify(initializer, never()).runCli(any(String.class),
                any(String.class), any(String.class), any(String[].class));
    }
    
    public void testCliNoInput() throws Exception
    {
        ViewInitializer initializer = mock(ViewInitializer.class);
        AppRunner runner = new AppRunner(initializer);
        runner.run("-r roster -o output".split(" "));
        verify(initializer, never()).runGui();
        verify(initializer, never()).runCli(any(String.class),
                any(String.class), any(String.class), any(String[].class));
    }
    
    public void testHelp() throws Exception
    {
        ViewInitializer initializer = mock(ViewInitializer.class);
        AppRunner runner = new AppRunner(initializer);
        runner.run("-h".split(" "));
        verify(initializer, never()).runGui();
        verify(initializer, never()).runCli(any(String.class),
                any(String.class), any(String.class), any(String[].class));   
    }
    
    public void testVersion() throws Exception
    {
        ViewInitializer initializer = mock(ViewInitializer.class);
        AppRunner runner = new AppRunner(initializer);
        runner.run("-v".split(" "));
        verify(initializer, never()).runGui();
        verify(initializer, never()).runCli(any(String.class),
                any(String.class), any(String.class), any(String[].class));   
    }
    
    public void testMain() throws Exception
    {
        AppRunner.main(new String[] {"-v"});
    }
}
