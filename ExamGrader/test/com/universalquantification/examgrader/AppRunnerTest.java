/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

import junit.framework.TestCase;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
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
        AppRunner runner = Mockito.spy(new AppRunner());
        doNothing().when(runner).runCli(any(String.class), any(String.class),
                any(String.class), any(String[].class));
        doNothing().when(runner).runGui();
        
        runner.run("-i file1.pdf file2.pdf -o output -r roster".split(" "));
        
        verify(runner).runCli(AppRunner.kNameAndVersion, "output",
                "roster", new String[] {"file1.pdf", "file2.pdf"});
                
    }
    
    public void testGui() throws Exception
    {
        AppRunner runner = Mockito.spy(new AppRunner());
        doNothing().when(runner).runCli(any(String.class), any(String.class),
                any(String.class), any(String[].class));
        doNothing().when(runner).runGui();
        runner.run(new String[] {});
        
        verify(runner).runGui();
    }
    
    public void testCliNoRoster() throws Exception
    {
        AppRunner runner = Mockito.spy(new AppRunner());
        doNothing().when(runner).runCli(any(String.class), any(String.class),
                any(String.class), any(String[].class));
        doNothing().when(runner).runGui();
        runner.run("-i file1.pdf file2.pdf -o output".split(" "));
        verify(runner, never()).runGui();
        verify(runner, never()).runCli(any(String.class),
                any(String.class), any(String.class), any(String[].class));
    }
    
    public void testCliNoInput() throws Exception
    {
        AppRunner runner = Mockito.spy(new AppRunner());
        doNothing().when(runner).runCli(any(String.class), any(String.class),
                any(String.class), any(String[].class));
        doNothing().when(runner).runGui();
        runner.run("-r roster -o output".split(" "));
        verify(runner, never()).runGui();
        verify(runner, never()).runCli(any(String.class),
                any(String.class), any(String.class), any(String[].class));
    }
    
    public void testHelp() throws Exception
    {
        AppRunner runner = Mockito.spy(new AppRunner());
        doNothing().when(runner).runCli(any(String.class), any(String.class),
                any(String.class), any(String[].class));
        doNothing().when(runner).runGui();
        runner.run("-h".split(" "));
        verify(runner, never()).runGui();
        verify(runner, never()).runCli(any(String.class),
                any(String.class), any(String.class), any(String[].class));   
    }
    
    public void testVersion() throws Exception
    {
        AppRunner runner = Mockito.spy(new AppRunner());
        doNothing().when(runner).runCli(any(String.class), any(String.class),
                any(String.class), any(String[].class));
        doNothing().when(runner).runGui();
        runner.run("-v".split(" "));
        verify(runner, never()).runGui();
        verify(runner, never()).runCli(any(String.class),
                any(String.class), any(String.class), any(String[].class));   
    }
    
    public void testMain() throws Exception
    {
        AppRunner.main(new String[] {"-v"});
    }
}
