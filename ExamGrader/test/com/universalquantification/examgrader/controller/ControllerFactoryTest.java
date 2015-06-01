/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.controller;

import com.universalquantification.examgrader.ui.AppView;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

/**
 *
 * @author jenny
 */
public class ControllerFactoryTest extends TestCase {
    
    public void testBuildController()
    {
        assertNotNull(new ControllerFactory().buildController(
                mock(AppView.class)));
    }
    
}
