
package com.universalquantification.examgrader.helpers;

import java.io.File;
import com.universalquantification.examgrader.models.Answer;
import com.universalquantification.examgrader.models.Bubble;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.InputPage;
import com.universalquantification.examgrader.models.Student;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 *
 * @author jenwang
 */
public class ExamHelper {
    public static Student createStudent(String firstName, String lastName) {
        return new Student(firstName, lastName, "id " + lastName, 
                mock(BufferedImage.class), mock(BufferedImage.class));
    }
    
    public static Answer createAnswer(Integer answerBits, int questionNum)
    {;
        int mask = 0b10000;

        List<Bubble> bubbles = new ArrayList<>();
            
        for (int k = 0; k < 5; k++)
        {

            boolean isFilled = (mask & answerBits) != 0;
            bubbles.add(new Bubble(isFilled, "" + k));         
            mask >>= 1;
        }
        
        return new Answer(bubbles, questionNum);
    }
    
    public static Exam createExam(List<Integer> answerBits, String firstName, String lastName)
    {
        ArrayList<Answer> answers = new ArrayList<>();
        
        for (int i = 0; i < answerBits.size(); i++)
        {
            answers.add(createAnswer(answerBits.get(i), i + 1));
        }
        
        InputPage inputPage = mock(InputPage.class);
        when(inputPage.getFile()).thenReturn(new File("aoeu"));
        when(inputPage.getMetaBufferedImage()).thenReturn(
                new BufferedImage(100, 100, IndexColorModel.BITMASK));
        
        return new Exam(answers, createStudent(firstName, lastName), inputPage);
    }
    
}
