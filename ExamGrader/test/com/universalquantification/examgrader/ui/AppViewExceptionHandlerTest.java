package com.universalquantification.examgrader.ui;

import junit.framework.TestCase;

import static org.mockito.Mockito.*;

/**
 *
 * @author jenwang
 */
public class AppViewExceptionHandlerTest extends TestCase {
    
    public AppViewExceptionHandlerTest(String testName)
    {
        super(testName);
    }
    
    public void testUncaughtException()
    {
        
        AppView mockedAppView = mock(AppView.class);
        
        AppViewExceptionHandler handler =
                new AppViewExceptionHandler(mockedAppView);
        handler.uncaughtException(Thread.currentThread(), new Exception());
        verify(mockedAppView, times(1)).showError(any(String.class));
        
        
    }
}
