/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.controller;

import com.universalquantification.examgrader.grader.Grader;
import com.universalquantification.examgrader.grader.RosterEntry;
import com.universalquantification.examgrader.models.InputFileList;
import com.universalquantification.examgrader.reader.ExamReader;
import com.universalquantification.examgrader.reader.NameRecognitionGateway;
import java.util.List;

/**
 *
 * @author jenny
 */
public class GraderFactory {
    
    public Grader buildNewGrader(InputFileList inputFileList,
            List<RosterEntry> rosterEntries)
    {
        return new Grader(inputFileList,
                new ExamReader(new NameRecognitionGateway()), rosterEntries);
    }
    
}
