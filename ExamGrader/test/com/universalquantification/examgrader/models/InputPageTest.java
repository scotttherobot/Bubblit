package com.universalquantification.examgrader.models;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import com.sun.pdfview.PDFPage;
import java.awt.geom.Rectangle2D;
import java.io.File;
import junit.framework.TestCase;

import static org.mockito.Mockito.*;

/**
 *
 * @author Jenny Wang
 */
public class InputPageTest extends TestCase {
    
    private InputPage inputPage;
    private PDFPage pdfPageMock;
    
    public InputPageTest(String testName) {
        super(testName);
        
        File file = new File("test.pdf");
        pdfPageMock = mock(PDFPage.class);
     
        when(pdfPageMock.getBBox()).thenReturn(new Rectangle2D.Float(0, 0, 100, 100));
        BufferedImage mockedImage = new BufferedImage(850, 1000,
                BufferedImage.TYPE_INT_RGB);
        
        when(pdfPageMock.getImage(anyInt(), anyInt(), any(Rectangle2D.class),
                any(ImageObserver.class), anyBoolean(), anyBoolean())).thenReturn(mockedImage);
        
        
        inputPage = new InputPage(file, pdfPageMock);
    }
    
    public void testInputPage()
    {
        assertNotNull(inputPage.getBufferedImage());
        verify(pdfPageMock).getImage(anyInt(), anyInt(), any(Rectangle2D.class),
                any(ImageObserver.class), anyBoolean(), anyBoolean());
    }
    
}
