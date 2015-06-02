package com.universalquantification.examgrader.ui.swing;

import com.universalquantification.examgrader.controller.Controller;
import com.universalquantification.examgrader.grader.Grader;
import com.universalquantification.examgrader.grader.RosterEntry;
import com.universalquantification.examgrader.models.GradedExamCollection;
import com.universalquantification.examgrader.models.InputFileList;
import com.universalquantification.examgrader.models.Student;
import com.universalquantification.examgrader.ui.AppView;
import com.universalquantification.examgrader.ui.AppViewExceptionHandler;
import com.universalquantification.examgrader.utils.AppFileFilter;
import com.universalquantification.examgrader.utils.PreferencesManager;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import sun.swing.ImageIconUIResource;

/**
 * main program
 *
 * @author Luis
 */
public class GUIView extends javax.swing.JFrame implements AppView,
    Observer
{
    private Controller controller;
    private List<String> fileLocations;

    /**
     * Creates new form NewApplication
     */
    public GUIView() throws IOException
    {
        controller = new Controller(this);
        
        initComponents();
        
        removeFileButton.setEnabled(false);

        setLocationRelativeTo(null);
        String rs = "resources/Icon.PNG";
        InputStream stream = this.getClass().getResourceAsStream(rs);
        ImageIcon appIcon = new ImageIcon(ImageIO.read(stream));
        this.setIconImage(appIcon.getImage());
    }

    /**
     * Present the verification dialog after exams have been graded.
     *
     * @param results the results of grading
     * @param roster the list of roster entries
     */
    @Override
    public void checkRoster(final Map<File, GradedExamCollection> results,
        final List<RosterEntry> roster)
    {
        progressBar.setIndeterminate(true);
        
        final List<Student> bigList = new ArrayList<Student>();

        // add each collection to the list
        for (GradedExamCollection collection : results.values())
        {
            bigList.addAll(collection.getAllStudents());
        }
        
        
        
        java.awt.EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {

                final VerifyDialog dialog = new VerifyDialog(bigList, roster);
                
                ActionListener finishedListener = new ActionListener()
                {

                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        String successMessage
                            = "Success! Your score reports have been written to:\n\n";

                        dialog.setVisible(false);
                        dialog.dispose();
                        controller.writeReports(results);

                        Object overrideDir = PreferencesManager.getInstance()
                                .get(PreferencesManager.kOverrideDir);
                        
                        for (File fileLocation : results.keySet())
                        {
                            if (overrideDir == null)
                            {
                                successMessage = successMessage.concat("\t•  " +
                                        fileLocation.getParent()
                                        + "\n");
                            }
                            else
                            {
                                successMessage = successMessage.concat("\t•  "
                                        + ((File) overrideDir).getPath()
                                        + "\n");
                            }
                        }
                        progressBar.setIndeterminate(false);
                        JOptionPane.showMessageDialog(GUIView.this,
                            successMessage, "Success!",
                            JOptionPane.INFORMATION_MESSAGE);
                        
                    }
                };
                
                dialog.addFinishedListener(finishedListener);

                dialog.setVisible(true);
            }
        });
    }

    @Override
    public void showError(String e)
    {
        if (this.isDisplayable())
        {
            progressBar.setIndeterminate(false);
            JOptionPane.showMessageDialog(this, e, "Error! ):",
                JOptionPane.ERROR_MESSAGE);        
        }
    }

    @Override
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
                @Override
                public void run()
                {
                    int pagesGraded = grader.getPagesGraded();
                    
                    if (pagesGraded != 0) {
                        progressBar.setMaximum(grader.getTotalPagesToGrade());
                        progressBar.setValue(pagesGraded);
                        
                        progressBar.setIndeterminate(false);
                    }
                }
            });
        }
    }

    /**
     * Sets the preference for whether or not to display the full test image on
     * the results web page for each exam.
     *
     * @param isFullImage Boolean condition representing whether or not to
     * display a full image.
     */
    public void setTestImagePreference(boolean isFullImage)
    {
        // do something with isFullImage
        PreferencesManager.getInstance().set("show-full-image", isFullImage);
    }

    /**
     * Sets the preference for whether or not to display the correct answers on
     * the results web page for each exam.
     *
     * @param doShow Boolean condition representing whether or not to display
     * the correct answers.
     */
    public void setShowCorrectAnswerPreference(boolean doShow)
    {
        // do something with doShow
        PreferencesManager.getInstance().set("show-correct-answers", doShow);
    }

    /**
     * Sets the preference for whether or not to display the incorrect answers
     * on the results web page for each exam.
     *
     * @param doShow Boolean condition representing whether or not to display
     * the incorrect answers.
     */
    public void setShowIncorrectAnswerPreference(boolean doShow)
    {
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
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        fileChooser = new javax.swing.JFileChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        ButtonPanel = new javax.swing.JPanel();
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

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bubblit Version 2 - Universal Quantification");
        setForeground(java.awt.Color.white);
        setPreferredSize(new java.awt.Dimension(460, 420));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 136, 228));
        jLabel1.setText("Bubblit");
        jLabel1.setToolTipText("What a clever name.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        ButtonPanel.setMinimumSize(new java.awt.Dimension(280, 18));
        ButtonPanel.setPreferredSize(new java.awt.Dimension(280, 18));
        ButtonPanel.setRequestFocusEnabled(false);
        ButtonPanel.setVerifyInputWhenFocusTarget(false);
        ButtonPanel.setLayout(new java.awt.GridBagLayout());

        addFileButton.setText("Add Exam File");
        addFileButton.setToolTipText("Add an exam file in PDF format to the queue.");
        addFileButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addFileButton.setMargin(new java.awt.Insets(2, 4, 2, 4));
        addFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0E-4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 6);
        ButtonPanel.add(addFileButton, gridBagConstraints);

        removeFileButton.setText("Remove Exam File");
        removeFileButton.setToolTipText("Remove the selected exam file from the queue.");
        removeFileButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        removeFileButton.setMargin(new java.awt.Insets(2, 4, 2, 4));
        removeFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0E-4;
        ButtonPanel.add(removeFileButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0E-4;
        gridBagConstraints.insets = new java.awt.Insets(8, 10, 2, 10);
        getContentPane().add(ButtonPanel, gridBagConstraints);

        listModel = new DefaultListModel();
        fileList.setModel(listModel);
        fileList.setToolTipText("The exam file queue. Files in this queue will be graded by the 'Grade!' button.");
        fileList.setAutoscrolls(false);
        fileList.setFixedCellHeight(24);
        fileList.setFocusCycleRoot(true);
        fileList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
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
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0E-4;
        gridBagConstraints.insets = new java.awt.Insets(12, 10, 10, 10);
        getContentPane().add(jSeparator1, gridBagConstraints);

        rosterFileLabel.setText("No Roster File Selected");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 14, 0, 0);
        getContentPane().add(rosterFileLabel, gridBagConstraints);

        addRosterFileButton.setText("Select Roster File");
        addRosterFileButton.setToolTipText("Select a roster file, in TSV format, to be used for matching exams to students.");
        addRosterFileButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addRosterFileButton.setMargin(new java.awt.Insets(2, 12, 2, 12));
        addRosterFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0E-4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jSeparator2, gridBagConstraints);

        gradePanel.setDoubleBuffered(false);
        gradePanel.setMinimumSize(new java.awt.Dimension(110, 28));
        gradePanel.setLayout(new java.awt.GridBagLayout());

        gradeButton.setText("Grade!");
        gradeButton.setToolTipText("Grade all exam files currently in the queue.");
        gradeButton.setAlignmentX(0.5F);
        gradeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gradeButton.setMaximumSize(new java.awt.Dimension(110, 23));
        gradeButton.setMinimumSize(new java.awt.Dimension(110, 23));
        gradeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gradePanel.add(gradeButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        getContentPane().add(gradePanel, gridBagConstraints);

        progressBar.setToolTipText("A progress bar that shows the progress of the grading process.");
        progressBar.setMinimumSize(new java.awt.Dimension(10, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(-10, 10, 0, 128);
        getContentPane().add(progressBar, gridBagConstraints);

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Preferences");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preferencesMenuItemSelected(evt);
            }
        });
        fileMenu.add(jMenuItem1);
        fileMenu.add(jSeparator3);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        contentsMenuItem.setMnemonic('c');
        contentsMenuItem.setText("User Manual");
        contentsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contentsMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(contentsMenuItem);
        contentsMenuItem.getAccessibleContext().setAccessibleName("How-To");

        aboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFileButtonActionPerformed
        AppFileFilter filter = new AppFileFilter("Portable Document Format (*.pdf)", new String [] {"PDF"});
        
        fileChooser.resetChoosableFileFilters();
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Bubblit - Please choose a PDF File");

        int returnVal = fileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            
            controller.addInputFile(file);
        }
        else
        {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_addFileButtonActionPerformed

    private void gradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeButtonActionPerformed
        SwingWorker<Void, Void> task = new SwingWorker<Void, Void>()
        {

            @Override
            public Void doInBackground()
            {
                ListModel<Class<?>> model = fileList.getModel();

                fileLocations = new ArrayList<String>();

                // get all selected files
                for (int onFile = 0; onFile < model.getSize(); onFile++)
                {
                    fileLocations.add(new File(
                            model.getElementAt(onFile) + "").getName());
                }

                progressBar.setIndeterminate(true);

                if (!controller.grade()) progressBar.setIndeterminate(false);

                return null;
            }

            @Override
            protected void done()
            {
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
        String url = "http://cytancy.github.io/BubblitUserManual/";
        
        if (Desktop.isDesktopSupported()) {
            try
            {
                Desktop.getDesktop().browse(new URL(url).toURI());
            }
            catch (IOException | URISyntaxException ex)
            {
                JTextArea textarea = new JTextArea("There was an error opening your browser. "
                    + "Please visit " + url + " to view the User Manaual.");
                
                JOptionPane.showMessageDialog(this, textarea,
                "User Manual", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else{
            Runtime runtime = Runtime.getRuntime();
            
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                JTextArea textarea = new JTextArea("There was an error opening your browser. "
                    + "Please visit " + url + " to view the User Manaual.");
                
                JOptionPane.showMessageDialog(this,
                textarea,
                "User Manual", JOptionPane.INFORMATION_MESSAGE);
            }
        }
            
    }//GEN-LAST:event_contentsMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this,
            "Welcome to Bubblit, a grading software that uses computer vision to grade tests. Created by Universal Quantification.",
            "About", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void removeFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFileButtonActionPerformed
        int selectedIndex = fileList.getSelectedIndex();

        // delete the input file
        if (selectedIndex != -1)
        {
            controller.deleteInputFile(selectedIndex);
            removeFileButton.setEnabled(false);
        }
    }//GEN-LAST:event_removeFileButtonActionPerformed

    private void fileListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_fileListValueChanged
        removeFileButton.setEnabled(true);
    }//GEN-LAST:event_fileListValueChanged

    private void addRosterFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRosterFileButtonActionPerformed
        AppFileFilter filter = new AppFileFilter("Tab Separated Values (*.tsv)", new String [] {"TSV"});
        
        fileChooser.resetChoosableFileFilters();
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Bubblit - Please choose a TSV File");

        int returnVal = fileChooser.showOpenDialog(this);

        // if the user selected a file
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            boolean succeeded = controller.changeRosterFile(file);
            
            // check that the file has a valid extension
            if (succeeded)
            {
                rosterFileLabel.setText(file.getName());
            }
        }
        else
        {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_addRosterFileButtonActionPerformed

    private void preferencesMenuItemSelected(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preferencesMenuItemSelected
        if (preferencesWindow == null)
        {
            preferencesWindow = new ExamPreferences(this);
        }

        preferencesWindow.setVisible(true);
    }//GEN-LAST:event_preferencesMenuItemSelected

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
     * Run the GUI
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
            java.util.logging.Logger.getLogger(GUIView.class.getName()).
                log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(GUIView.class.getName()).
                log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(GUIView.class.getName()).
                log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(GUIView.class.getName()).
                log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

          // Mac Compatbilitiy
        try
        {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.
                setProperty("com.apple.mrj.application.apple.menu.about.name",
                    "Bubblit Version 1 - Universal Quanitification");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("ClassNotFoundException: " + e.getMessage());
        }
        catch (InstantiationException e)
        {
            System.out.println("InstantiationException: " + e.getMessage());
        }
        catch (IllegalAccessException e)
        {
            System.out.println("IllegalAccessException: " + e.getMessage());
        }
        catch (UnsupportedLookAndFeelException e)
        {
            System.out.println("UnsupportedLookAndFeelException: " + e.
                getMessage());
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {

            public void run()
            {
                try
                {
                    final GUIView app = new GUIView();
                    app.setVisible(true);

                    Thread.setDefaultUncaughtExceptionHandler(
                        new AppViewExceptionHandler(app));

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        });

    }


    //private GradeExams task;

    // Variables for creation
    private ExamPreferences preferencesWindow;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ButtonPanel;
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
    private javax.swing.JList jList1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton removeFileButton;
    private javax.swing.JLabel rosterFileLabel;
    // End of variables declaration//GEN-END:variables

}
