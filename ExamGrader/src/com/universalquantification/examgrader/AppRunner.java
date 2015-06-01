/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

import com.universalquantification.examgrader.ui.GUIView;
import static com.universalquantification.examgrader.ui.GUIView.runGui;
import com.universalquantification.examgrader.controller.ControllerFactory;
import com.universalquantification.examgrader.ui.ConsoleView;
import java.io.PrintWriter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author scottvanderlind
 */
public class AppRunner
{
    
     private static final String kNameAndVersion
        = "Bubblit V2.0 by Universal Quantification";

    private static final Options kCommandLineOptions = new Options()
    {
        {
            // add v option, with no arguments
            this.addOption("v", "version", false,
                "display version and team info.");
            this.addOption("h", "help", false, "display syntax help info.");
            // add r flag with argument boolean SET to true
            Option inputFile = Option.builder("i")
                    .longOpt("input-file")
                    .hasArg(true)
                    .desc("Path to PDF Input Exam File(s)")
                    .required(false)
                    .hasArgs()
                    .build();
            // add i flag with argument boolean SET to true 
            this.addOption(inputFile);
            this.addOption("r", "roster", true,
                "Path to student roster TSV file (relative or absolute)");
            // add o flag with argument boolean SET to true
            this.addOption("o", "outputDirectoryOverride", true,
                "Path to folder for placing result files");
        }
    };
    
    /**
     * Run the application.
     * @param args command arguments
     * @throws ParseException 
     */
    public static void main(String[] args) throws ParseException
    {
        CommandLineParser parser = new GnuParser();

        CommandLine cmd = null;
        try
        {
            cmd = parser.parse(kCommandLineOptions, args);
        }
        catch (ParseException ex)
        {
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        //check for -v flag to print out the version of the application
        //   and team information.
        if (cmd.hasOption("v"))
        {
            System.out.println(kNameAndVersion);
            return;
        }

        //check for -h flag to print out the syntax help information.
        if (cmd.hasOption("h"))
        {
            printHelp(kCommandLineOptions);
            return;
        }

        String oArg = cmd.getOptionValue("o");
        String rArg = cmd.getOptionValue("r");
        String[] iArgs = cmd.getOptionValues("i");

        if (args.length != 0)
        {

            if (rArg == null || iArgs == null)
            {
                System.out.println("Argument missing. See the --help option.");
                System.exit(1);
            }
            runCli(oArg, rArg, iArgs);
        }
        else
        {
            GUIView.runGui();
        }
    }
    
    private static void printHelp(Options options)
    {
        // Use Apache's neato CommonsCLI Helptext generator
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Bubblit2.0.jar", options, /* show usage */ true);
    }
        
    private static void runCli(String outputDir, String rosterFile,
        String[] inputFiles)
    {
        ConsoleView view = new ConsoleView(kNameAndVersion, rosterFile,
            inputFiles, outputDir, new PrintWriter(System.out),
                new ControllerFactory());
    }
    
}
