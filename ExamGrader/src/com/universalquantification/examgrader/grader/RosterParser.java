package com.universalquantification.examgrader.grader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a roster using a {@link Roster} to read information about records
 * @author luis
 */
public final class RosterParser
{

    private RosterParser()
    {
    }
    
    /**
     * Parses a list of RosterEntrys, one per line.
     *
     * @param roster the roster to read from
     * @return a list of newly constructed {@code RosterEntry}s
     */
    public static List<RosterEntry> parseRoster(Roster roster)
    {
        List<RosterEntry> result = new ArrayList<RosterEntry>();

        // Iterate over every record and parse it
        for (int onStudent = 1; onStudent < roster.getNumStudents() + 1; onStudent++)
        {
            //int sequenceNumber, String first, String last
            Map<String, String> entry = roster.getRecordByColumnValue("No.",
                Integer.toString(onStudent));
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

            result.add(new RosterEntry(onStudent, first, last, id));
        }

        return result;
    }
}
