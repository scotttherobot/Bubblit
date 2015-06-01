/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader;

import com.universalquantification.examgrader.controller.ControllerFactory;
import com.universalquantification.examgrader.ui.ConsoleView;
import com.universalquantification.examgrader.ui.GUIView;
import java.io.PrintWriter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * This is the main entry point for the Bubblit application.
 * Parses command line arguments. If found, passes them to the ConsoleView.
 * If it finds none, spawns a GUIView.
 * @author scottvanderlind
 */
public class AppRunner
{
    // The name and version string to use in the interfaces
    public static final String kNameAndVersion
        = "Bubblit V2.0 by Universal Quantification";

    // The command line options to parse before starting a console.
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
     * A class for initializing the views. Separated out from AppRunner for
     * testability.
     * @author jenny
     */         
    public static class ViewInitializer {
        /**
         * Runs the GUI.
         */
        public void runGui()
        {
            GUIView.runGui();
        }

        /**
         * Instantiate and run a ConsoleView interface.
         * Starts a console for running the app.
         * @param outputDir the directory to output files to
         * @param rosterFile the roster file to use
         * @param inputFiles the paths of the files to grade
         */
        public void runCli(String nameAndVersion, String outputDir,
                String rosterFile, String[] inputFiles)
        { 
            ConsoleView view = new ConsoleView(nameAndVersion, rosterFile,
                inputFiles, outputDir, new PrintWriter(System.out),
                    new ControllerFactory());
        }

    }
    
    private ViewInitializer viewInitializer;
    
    public AppRunner(ViewInitializer viewInitializer)
    {
        this.viewInitializer = viewInitializer;
    }
    
    /**
     * Run the application.
     * @param args command arguments
     * @throws ParseException 
     */
    public void run(String[] args) throws ParseException
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
            return;
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
                return;
            }
            viewInitializer.runCli(kNameAndVersion, oArg, rArg, iArgs);
        }
        else
        {
            viewInitializer.runGui();
        }
    }
    
    /**
     * Prints the help for the Console interface
     * @param options an Options object containing the CLI options
     */
    private void printHelp(Options options)
    {
        // Use Apache's neato CommonsCLI Helptext generator
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Bubblit2.0.jar", options, /* show usage */ true);
    }

    public static void main(String[] args) throws ParseException
    {
        new AppRunner(new ViewInitializer()).run(args);
    }
    
}
