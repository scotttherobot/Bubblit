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
import com.universalquantification.examgrader.reader.InvalidExamException;
import com.universalquantification.examgrader.reader.NameRecognitionGateway;
import java.util.List;

/**
 * Create a grader
 * @author jenny
 */
public class GraderFactory
{

    /**
     * Create a grader with a given {@link InputFileList} and {@link RosterEntry}s
     * @param inputFileList the list of input files
     * @param rosterEntries the list of roster entries
     * @return the newly created Grader
     * @throws com.universalquantification.examgrader.reader.InvalidExamException
     */
    public Grader buildNewGrader(InputFileList inputFileList,
        List<RosterEntry> rosterEntries) throws InvalidExamException
    {
        return new Grader(inputFileList,
            new ExamReader(new NameRecognitionGateway()), rosterEntries);
    }

}
