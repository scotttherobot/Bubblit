package com.universalquantification.examgrader;

import com.universalquantification.examgrader.controller.ControllerFactory;
import com.universalquantification.examgrader.ui.console.ConsoleView;
import com.universalquantification.examgrader.ui.swing.GUIView;
import java.io.PrintWriter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * This is the main entry point for the Bubblit application. Parses command line
 * arguments. If found, passes them to the ConsoleView. If it finds none, spawns
 * a GUIView.
 *
 * @author scottvanderlind
 */
public class AppRunner
{

    /**
     * The name and version string to use in the interfaces
     */
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
     * Runs the GUI.
     */
    void runGui()
    {
        GUIView.runGui();  
    }
    
    void runCli(String nameAndVersion, String outputDir,
            String rosterFile, String[] inputFiles)
    {
        ConsoleView view = new ConsoleView(nameAndVersion, rosterFile,
                inputFiles, outputDir, new PrintWriter(System.out),
                new ControllerFactory());
        view.run();
    }

    /**
     * Run the application.
     *
     * @param args command arguments
     * @throws org.apache.commons.cli.ParseException
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

        // check if we have arguments
        if (args.length != 0)
        {
            // check that no arguments are missing
            if (rArg == null || iArgs == null)
            {
                System.out.println("Argument missing. See the --help option.");
                return;
            }
            runCli(kNameAndVersion, oArg, rArg, iArgs);
        }
        else
        {
            runGui();
        }
    }

    /**
     * Prints the help for the Console interface
     *
     * @param options an Options object containing the CLI options
     */
    private void printHelp(Options options)
    {
        // Use Apache's neato CommonsCLI Helptext generator
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Bubblit2.0.jar", options, /* show usage */ true);
    }

    /**
     * Run the application
     * @param args the command line arguments
     * @throws ParseException 
     */
    public static void main(String[] args) throws ParseException
    {
        new AppRunner().run(args);
    }

}
