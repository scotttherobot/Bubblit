package com.universalquantification.examgrader;

import com.universalquantification.examgrader.controller.Controller;
import com.universalquantification.examgrader.grader.Grader;
import com.universalquantification.examgrader.grader.RosterEntry;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.GradedExamCollection;
import com.universalquantification.examgrader.models.InputFileList;
import com.universalquantification.examgrader.models.Student;
import com.universalquantification.examgrader.ui.AppView;
import com.universalquantification.examgrader.ui.ConsoleView;
import com.universalquantification.examgrader.ui.VerifyDialog;
import com.universalquantification.examgrader.utils.PreferencesManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * main program
 * @author Luis
 */
public class MainApplication extends javax.swing.JFrame implements AppView, Observer
{
    private final MainApplication _this = this;
    private Controller controller;
    private List<String> fileLocations;

    /**
     * Creates new form NewApplication
     */
    public MainApplication() throws IOException
    {
        controller = new Controller(this);
        initComponents();
        removeFileButton.setEnabled(false);
        
        setLocationRelativeTo(null);
    }
    
    public void checkRoster(final Map<File, GradedExamCollection> results, final List<RosterEntry> roster)
    {   
        final List<Student> bigList = new ArrayList<Student>();
        for (GradedExamCollection collection : results.values()) {
            bigList.addAll(collection.getAllStudents());
        }

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                final VerifyDialog dialog = new VerifyDialog(bigList, roster);
                ActionListener finishedListener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        String successMessage = "Success! Your score reports have been written to:\n\n";
                        
                        dialog.setVisible(false);
                        dialog.dispose();
                        controller.writeReports(results);
                        
                        for (String fileLocation : fileLocations) {
                            successMessage = successMessage.concat("\t•  " + new File(fileLocation).getParent() + "\n");
                        }
                        
                        JOptionPane.showMessageDialog(_this, successMessage, "Success!", JOptionPane.INFORMATION_MESSAGE);
                    }
                };

                dialog.addFinishedListener(finishedListener);

                dialog.setVisible(true);
            }
        });
    }
    
    public void showError(String e)
    {
          /* Create and display the form */
        /*
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    new MainApplication().setVisible(true);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        });
        */
  
        if(this.isDisplayable()){
            JOptionPane.showMessageDialog(this, e, "Error! ):", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void update(Observable o, final Object arg)
    {
        if (o instanceof InputFileList)
        {
            InputFileList list = (InputFileList) o;
            fileList.setListData(list.getFiles().toArray());
        }
        else if (o instanceof Grader)
        {
            final Grader grader = (Grader) o;
            java.awt.EventQueue.invokeLater(new Runnable()
            {
                public void run()
                {
                    progressBar.setMaximum(grader.getTotalPagesToGrade());
                    progressBar.setValue(grader.getPagesGraded());
                }
            });
        }
    }
    
    /**
     * Sets the preference for whether or not to display the full test image
     * on the results web page for each exam.
     * 
     * @param isFullImage Boolean condition representing whether or not to 
     * display a full image.
     */
    public void setTestImagePreference(boolean isFullImage) {
        // do something with isFullImage
        PreferencesManager.getInstance().set("show-full-image", isFullImage);
    }
    
    /**
     * Sets the preference for whether or not to display the correct answers
     * on the results web page for each exam.
     * 
     * @param doShow Boolean condition representing whether or not to 
     * display the correct answers.
     */
    public void setShowCorrectAnswerPreference(boolean doShow) {
        // do something with doShow
        PreferencesManager.getInstance().set("show-correct-answers", doShow);
    }

    /**
     * Sets the preference for whether or not to display the incorrect answers
     * on the results web page for each exam.
     * 
     * @param doShow Boolean condition representing whether or not to 
     * display the incorrect answers.
     */
    public void setShowIncorrectAnswerPreference(boolean doShow) {
        // do something with doShow
        PreferencesManager.getInstance().set("show-incorrect-answers", doShow);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        fileChooser = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        addFileButton = new javax.swing.JButton();
        removeFileButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        fileList = new javax.swing.JList();
        jSeparator1 = new javax.swing.JSeparator();
        rosterFileLabel = new javax.swing.JLabel();
        addRosterFileButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        gradePanel = new javax.swing.JPanel();
        gradeButton = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        fileChooser.setDialogTitle("Choose PDF or CSV");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bubblit Version 2 - Universal Quantification");
        setForeground(java.awt.Color.white);
        setPreferredSize(new java.awt.Dimension(480, 400));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 136, 228));
        jLabel1.setText("Bubblit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        addFileButton.setText("Add Exam File");
        addFileButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 134);
        getContentPane().add(addFileButton, gridBagConstraints);

        removeFileButton.setText("Remove Exam File");
        removeFileButton.setToolTipText("");
        removeFileButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                removeFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 10);
        getContentPane().add(removeFileButton, gridBagConstraints);

        listModel = new DefaultListModel();
        fileList.setModel(listModel);
        fileList.setAutoscrolls(false);
        fileList.setFixedCellHeight(24);
        fileList.setFocusCycleRoot(true);
        fileList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                fileListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(fileList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 359;
        gridBagConstraints.ipady = 161;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 0, 10);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jSeparator1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jSeparator1, gridBagConstraints);

        rosterFileLabel.setText("No Roster File Selected");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 14, 0, 0);
        getContentPane().add(rosterFileLabel, gridBagConstraints);

        addRosterFileButton.setText("Select Roster File");
        addRosterFileButton.setMargin(new java.awt.Insets(2, 12, 2, 12));
        addRosterFileButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addRosterFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        getContentPane().add(addRosterFileButton, gridBagConstraints);
        addRosterFileButton.getAccessibleContext().setAccessibleName("Add Roster File");

        jSeparator2.setAutoscrolls(true);
        jSeparator2.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jSeparator2, gridBagConstraints);

        gradePanel.setLayout(new java.awt.BorderLayout());

        gradeButton.setText("Grade!");
        gradeButton.setToolTipText("");
        gradeButton.setAlignmentX(0.5F);
        gradeButton.setMaximumSize(new java.awt.Dimension(110, 23));
        gradeButton.setMinimumSize(new java.awt.Dimension(110, 23));
        gradeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                gradeButtonActionPerformed(evt);
            }
        });
        gradePanel.add(gradeButton, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        getContentPane().add(gradePanel, gridBagConstraints);

        progressBar.setMinimumSize(new java.awt.Dimension(10, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(-10, 10, 0, 128);
        getContentPane().add(progressBar, gridBagConstraints);

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Preferences");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                preferencesMenuItemSelected(evt);
            }
        });
        fileMenu.add(jMenuItem1);
        fileMenu.add(jSeparator3);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentsMenuItem.setMnemonic('c');
        contentsMenuItem.setText("Contents");
        contentsMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                contentsMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(contentsMenuItem);
        contentsMenuItem.getAccessibleContext().setAccessibleName("How-To");

        aboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFileButtonActionPerformed
        fileChooser.setDialogTitle("Choose a PDF File");
        
        int returnVal = fileChooser.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            
            if (file.getName().endsWith(".pdf")) {
                controller.addInputFile(file);
            }
            else {
                 JOptionPane.showMessageDialog(this, "You may only add exam " +
                         "files of Portable Document Format (PDF). " + 
                         "\nPlease see the user manual available in the 'Help' " +
                         "menu for \nmore information.", "Wrong File Type", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_addFileButtonActionPerformed

    private void gradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeButtonActionPerformed
        SwingWorker<Void, Void> task = new SwingWorker<Void, Void>() {

            @Override
            public Void doInBackground() {
                ListModel<Class<?>> model = fileList.getModel();
                
                fileLocations = new ArrayList<String>();
                
                for(int i = 0; i < model.getSize(); i++) {
                    fileLocations.add(model.getElementAt(i) + "");
                } 

                controller.grade();
                        
                return null;
            }
            
             @Override
             protected void done() {
                gradeButton.setEnabled(true);
                gradeButton.setText("Grade!");
             }       

        };
                
        task.execute();
        gradeButton.setEnabled(false);
        gradeButton.setText("Grading...");
    }//GEN-LAST:event_gradeButtonActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void contentsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contentsMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, 
                "How to Add Tests: "
                + "\n\t1) Click the \"Add File\" button."
                + "\n\t2) Select the PDF file(s) containing the forms you want graded. Make sure that "
                + "\n\t     the first page of the PDF file is the form representing the key."
                + "\n\t3) Press \"OK\" and your files will now be queued to be graded."
                + "\n\nHow to Grade Tests: "
                + "\n\t1) Check that the PDF files you have in your lists are the forms you want graded."
                + "\n\t2) Click the \"Grade!\" button."
                + "\n\t3) When the progress bar displayed is full, the resulting the scoring files will"
                + "\n\t     be found in the directories of each of your queued files."
        );
    }//GEN-LAST:event_contentsMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        // TODO add your handling code here:
         JOptionPane.showMessageDialog(this, "Welcome to Bubblit, a grading software that uses computer vision to grade tests. Created by Universal Quantification.", 
                 "About", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void removeFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFileButtonActionPerformed
        int selectedIndex = fileList.getSelectedIndex();
        
        if (selectedIndex != -1) {
            controller.deleteInputFile(selectedIndex);
            removeFileButton.setEnabled(false);
        }
    }//GEN-LAST:event_removeFileButtonActionPerformed

    private void fileListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_fileListValueChanged
        removeFileButton.setEnabled(true);
    }//GEN-LAST:event_fileListValueChanged

    private void addRosterFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRosterFileButtonActionPerformed
        fileChooser.setDialogTitle("Choose a TSV File");

        int returnVal = fileChooser.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            
            if (file.getName().endsWith(".tsv")) {
                try {
                    rosterFileLabel.setText(file.getName());
                    controller.changeRosterFile(file);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                 JOptionPane.showMessageDialog(this, "You may only add roster " +
                         "files of Tab Separated Values (TSV) " + 
                         "\nformat. Please see the user manual available in the 'Help' " +
                         "menu \nfor more information.", "Wrong File Type", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_addRosterFileButtonActionPerformed

    private void preferencesMenuItemSelected(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preferencesMenuItemSelected
        if (preferencesWindow == null)
            preferencesWindow = new ExamPreferences(this);

        preferencesWindow.setVisible(true);
    }//GEN-LAST:event_preferencesMenuItemSelected

   private static final String NAME_AND_VERSION =
            "Bubblit V2.0 by Universal Quantification";
    
    private static final Options COMMAND_LINE_OPTIONS = new Options()
    {{
        // add v option, with no arguments
        this.addOption("v", "version", false, "display version and team info.");
        this.addOption("h", "help", false, "display syntax help info.");
        // add i flag with argument boolean SET to true 
        this.addOption("i", "input-file", true, "Path to PDF Input Exam File(s)");
        // add r flag with argument boolean SET to true
        this.addOption("r", "roster", true, "Path to student roster TSV file (relative or absolute)");
        // add o flag with argument boolean SET to true
        this.addOption("o", "outputDirectoryOverride", true, "Path to folder for placing result files");
    }};  
    
     /**
     * @param args the command line arguments
     */
    public static void runGui()
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info
                    : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(MainApplication.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(MainApplication.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(MainApplication.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(MainApplication.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
          // Mac Compatbilitiy

        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Bubblit Version 1 - Universal Quanitification");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e.getMessage());
        } catch (InstantiationException e) {
            System.out.println("InstantiationException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException: " + e.getMessage());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("UnsupportedLookAndFeelException: " + e.getMessage());
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    new MainApplication().setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        });
        
       
    }
    
    private static void runCli(String outputDir, String rosterFile,
            String[] inputFiles)
    {
        ConsoleView view = new ConsoleView(NAME_AND_VERSION, rosterFile,
                inputFiles, outputDir, new PrintWriter(System.out));
    }

    private static void printHelp(Options options)
    {
        System.out.println("java -jar Bubblit2.0.jar -r <TSV Roster File> -i <PDF Exam(s) separated by spaces> -o <Path to results folder>");
       
        System.out.println("-v: Display version and team info.");
        System.out.println("-h: Display syntax help info.");
        System.out.println("-i: Path to PDF Input Exam File(s).");
        System.out.println("-r: Path to student roster TSV file (relative or absolute).");
        System.out.println("-o: Path to folder for placing result files.");
        System.out.println("Correct flag usage syntax:");
        System.out.println("java -jar Bubblit2.0.jar -r <TSV Roster File> -i <PDF Exam(s) separated by spaces> -o <Path to results folder>");

    } 


    public static void main(String[] args) throws ParseException
    {
        // Init our preferences to false. We should do this later, but it will
        // cause a crash if it's NOT done. So here.
        PreferencesManager.getInstance().set("show-full-image", false);
        PreferencesManager.getInstance().set("show-incorrect-answers", false);
        PreferencesManager.getInstance().set("show-correct-answers", false);
        
        
        CommandLineParser parser = new GnuParser();
     
        CommandLine cmd = null;
        try {
            cmd = parser.parse(COMMAND_LINE_OPTIONS, args);
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
     
        //check for -v flag to print out the version of the application
        //   and team information.
        if (cmd.hasOption("v")) {
            System.out.println(NAME_AND_VERSION);
            return;
        }

        //check for -h flag to print out the syntax help information.
        if (cmd.hasOption("h")) {
   
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
            runGui();
        }   
    }
    //private GradeExams task;
    
    // Variables for creation
    
    private ExamPreferences preferencesWindow;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton addFileButton;
    private javax.swing.JButton addRosterFileButton;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JFileChooser fileChooser;
    private DefaultListModel listModel;
    private javax.swing.JList fileList;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton gradeButton;
    private javax.swing.JPanel gradePanel;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton removeFileButton;
    private javax.swing.JLabel rosterFileLabel;
    // End of variables declaration//GEN-END:variables

}
