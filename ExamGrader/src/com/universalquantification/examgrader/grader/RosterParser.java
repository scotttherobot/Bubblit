/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.grader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author luis
 */
public class RosterParser
{
    
    /**
     * Parses a single {@code RosterEntry}.
     * The input should be of the form, e.g., {@code 1  "Smith, Jones"};
     * that is, the sequence number should be in the first column,
     * the name should be quoted and its parts comma-separated,
     * and there may be any amount of whitespace between tokens.
     *
     * @param line
     *      the line containing the roster entry to parse
     * @return
     *      a newly constructed {@code RosterEntry}
     */
    public static RosterEntry parseRosterEntry(String line)
    {
        final String pattern = "([0-9]+)\\s*\"([^,]+),\\s*([^\\s]+).*\"$";
        Matcher matcher = Pattern.compile(pattern).matcher(line);

        // Make sure that the input is valid.
        if (!matcher.matches())
        {
            throw new IllegalArgumentException(line);
        }

        int sequenceNumber = Integer.parseInt(matcher.group(1));
        String last = matcher.group(2);
        String first = matcher.group(3);

        return new RosterEntry(sequenceNumber, first, last, "IS THIS USED");
    }

    /**
     * Parses a list of {@code RosterEntry}s, one per line.
     * Each line should be of the form
     * expected by {@link #parseRosterEntry(String)}.
     *
     * @param file
     *      the file containing the roster entries to parse
     * @return
     *      a list of newly constructed {@code RosterEntry}s
     */
    public static List<RosterEntry> parseRoster(Roster roster)
    {
        List<RosterEntry> result = new ArrayList<>();

        for(int i = 1; i < roster.getNumStudents() + 1; i++)
        {
            //int sequenceNumber, String first, String last
            Map<String,String> entry = roster.getRecordByColumnValue("No.", Integer.toString(i));
            String name = entry.get("Student Name");
            String id = entry.get("EMPLID");
            
            final String pattern = "\"?([^,]+),\\s*([^\\s\"]+).*\"?";
            Matcher matcher = Pattern.compile(pattern).matcher(name);

            // Make sure that the input is valid.
            if (!matcher.matches())
            {
                throw new IllegalArgumentException(name);
            }

            String last = matcher.group(1);
            String first = matcher.group(2);

            result.add(new RosterEntry(i, first, last, id));
        }
        
        return result;
    }
}
