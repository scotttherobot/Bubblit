package com.universalquantification.examgrader.ui.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import com.universalquantification.examgrader.grader.RosterEntry;
import com.universalquantification.examgrader.models.Student;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Partially functioning prototype of a verify dialog.
 * 
 * @author jdalbey
 */
public class VerifyDialog extends javax.swing.JFrame
{ 
    private final List<RosterEntry> roster;
    private boolean verificationSuccessful;

    /**
     * Creates new form VerifyDialog
     * 
     * @param matchResults The results from matching entries to students
     * @param roster The roster to get student info from.
     * 
     * @throws java.io.IOException
     */
    public VerifyDialog(List<Student> matchResults, List<RosterEntry> roster) throws IOException
    {
        this.roster = roster;
        
        tableModel = new MyTableModel(matchResults);
        
        verificationSuccessful = false;

        initComponents();
        
        this.setIconImage(new ImageIcon("Icon.PNG").getImage());
        
        TableCellRenderer defaultRenderer = nameTable.getDefaultRenderer(
                JButton.class);
        nameTable.setDefaultRenderer(JButton.class,
                new JTableButtonRenderer(defaultRenderer));
        
        nameTable.getColumnModel().getColumn(0).setPreferredWidth(240);
        nameTable.getColumnModel().getColumn(2).setPreferredWidth(240);
        nameTable.getColumnModel().getColumn(4).setPreferredWidth(60);
        nameTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        
        ((DefaultTableCellRenderer)
            nameTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(
                JLabel.CENTER);
        
        listRoster.setEnabled(false);
        listRoster.setBackground(new Color(240, 240, 240));
        
        TableColumn chooseColumn = nameTable.getColumnModel().getColumn(5);
        
        chooseColumn.setCellEditor(new ButtonColumn());
        // The JList needs a listener that when a row is clicked,
        // it will set that value into the table at the row
        // saved in the instance field "chosenTableRow".
        // then disable the JList.
        
        setLocationRelativeTo(null);
        
        // Redundancy needed for some Windows platforms
        String rs = "resources/Icon.PNG";
        InputStream stream = this.getClass().getResourceAsStream(rs);
        ImageIcon appIcon = new ImageIcon(ImageIO.read(stream));
        this.setIconImage(appIcon.getImage());
    }
    
    /**
     * Add a listener that listens for verification completion
     * @param listener the listener
     */
    public void addFinishedListener(ActionListener listener)
    {
        VerifyButton.addActionListener(listener);  
    }

    /**
     * Confirms that the results were successfully verified.
     */
    public void setVerificationToSuccessful() {
        verificationSuccessful = true;
    }
    
    /**
     * Returns the verification button for the dialog.
     * 
     * @return The verification button for the dialog. 
     */
    public JButton getVerifyButton() {
        return VerifyButton;
    }

    
    /**
     * Represents a table model
     */
    class MyTableModel extends AbstractTableModel
    {
        private String[] columnNames =
        {
            "First Name Image",
            "First Name",
            "Last Name Image",
            "Last Name",
            "Confidence",
            "",
        };
        
        private String[][] model;
        
        private List<Student> matchResults;
        
        private List<JButton> buttons; 
        
        MyTableModel(List<Student> matchResults)
        {
            this.matchResults = matchResults;
            this.buttons = new ArrayList<JButton>();
            // add all match results to the table
            for (int onMatch = 0; onMatch < matchResults.size(); onMatch++)
            {
                buttons.add(mkChooseBtn(onMatch));
            }
        }

        public String getColumnName(int col)
        {
            return columnNames[col].toString();
        }

        public int getRowCount()
        {
            return matchResults.size();
        }

        public int getColumnCount()
        {
            return columnNames.length;
        }

        public Object getValueAt(int row, int col)
        {
            Student student = matchResults.get(row);
            // show the first name image
            if (col == 0)
            {
                return new ImageIcon(student.getFirstNameImage());
            }
            // show the first name string
            else if (col == 1)
            {
                return student.getFirstName();
            }
            // show the lastname image
            else if (col == 2)
            {
                return new ImageIcon(student.getLastNameImage());
            }
            // show the last name string
            else if (col == 3)
            {
                return student.getLastName();
            }
            // show the match confidence
            else if (col == 4)
            {
                return student.getConfidence();
            }
            // show the button to change the matched student
            else if (col == 5)
            {  
                return buttons.get(row);
            }
            else 
            {
                return Boolean.FALSE;
            }
        }

        @Override
        public boolean isCellEditable(int row, int col)
        {
            return (col == 1 || col == 3 || col == 5);
        }

        @Override
        public Class getColumnClass(int column)
        {
            return getValueAt(0, column).getClass();
        }
        
        public void setNameFromRoster(String value, int row)
        {
            String[] name = value.split(", ");
            setValueAt(name[1], row, 1);
            setValueAt(name[0], row, 3);
        }

        public void setValueAt(Object value, int row, int col)
        {
            // set the first name string
            if (col == 1)
            {
                matchResults.get(row).setFirstName(value.toString());
            }
            // set the last name string
            else if (col == 3)
            {   
                matchResults.get(row).setlastName(value.toString());
            }
            fireTableCellUpdated(row, col);
        }
    }

    /**
     * Renders a button in a cell
     */
    class JTableButtonRenderer implements TableCellRenderer
    {
        private TableCellRenderer defaultRenderer;

        public JTableButtonRenderer(TableCellRenderer renderer)
        {
            defaultRenderer = renderer;
        }

        public Component getTableCellRendererComponent(JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row, int column)
        {
            // check thta the button can be clicked
            if (value instanceof Component)
            {
                return (Component) value;
            }
            return defaultRenderer.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
        }
    }

    /**
     * represents a column of buttons
     */
    class ButtonColumn extends AbstractCellEditor implements TableCellEditor
    {
        private JButton tempButton;

        @Override
        public Object getCellEditorValue()
        {
            return tempButton;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column)
        {
            tempButton = (JButton) value;
            return (Component) value;
        }
    }

    private DefaultListModel createListModel()
    {
        DefaultListModel aModel = new DefaultListModel();
        // add all entries to the table
        for (RosterEntry rosterEntry : roster)
        {
            aModel.addElement(rosterEntry.getLast() + ", " + rosterEntry.getFirst());
        }
        return aModel;
    }

    private JButton mkChooseBtn(int row)
    {
        JButton btn = new JButton("Change Name");
        btn.setActionCommand("" + row);
        btn.addActionListener(new ChooseBtnListener());
        return btn;
    }
    
    /**
     * Listens for clicks on "choose" button
     */
    class ChooseBtnListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt)
        {
            String action = evt.getActionCommand();
            chosenTableRow = Integer.parseInt(action);
            listRoster.setEnabled(true);
            listRoster.clearSelection();
            listRoster.setBackground(new Color(255, 255, 255));
        }
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

        jDesktopPane1 = new javax.swing.JDesktopPane();
        InstructionLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        nameTable = new javax.swing.JTable();
        NameSelectionPane = new javax.swing.JScrollPane();
        listRoster = new javax.swing.JList();
        jSeparator2 = new javax.swing.JSeparator();
        VerifyButton = new javax.swing.JButton();
        CloseButton = new javax.swing.JButton();

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Bubblit Verification Dialog");
        setPreferredSize(new java.awt.Dimension(1280, 720));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        InstructionLabel.setText("Please verify that the students have been correctly identified.");
        InstructionLabel.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 10, 10, 10);
        getContentPane().add(InstructionLabel, gridBagConstraints);

        jSeparator1.setPreferredSize(new java.awt.Dimension(10, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0E-4;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        getContentPane().add(jSeparator1, gridBagConstraints);

        jSplitPane1.setResizeWeight(1.0);
        jSplitPane1.setAlignmentX(0.5F);
        jSplitPane1.setAlignmentY(0.5F);
        jSplitPane1.setMinimumSize(new java.awt.Dimension(100, 23));
        jSplitPane1.setPreferredSize(new java.awt.Dimension(100, 100));
        jSplitPane1.setRequestFocusEnabled(false);
        jSplitPane1.setVerifyInputWhenFocusTarget(false);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(460, 21));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 402));
        jScrollPane1.setRequestFocusEnabled(false);

        nameTable.setModel(tableModel);
        nameTable.setFocusTraversalPolicyProvider(true);
        nameTable.setInheritsPopupMenu(true);
        jScrollPane1.setViewportView(nameTable);

        jSplitPane1.setLeftComponent(jScrollPane1);

        NameSelectionPane.setMinimumSize(new java.awt.Dimension(200, 0));
        NameSelectionPane.setPreferredSize(new java.awt.Dimension(240, 6));
        NameSelectionPane.setRequestFocusEnabled(false);

        listRoster.setModel(createListModel());
        listRoster.setToolTipText("This panel allows you set names to exams via the \"Choose Name\" button. ");
        listRoster.setAutoscrolls(false);
        listRoster.setRequestFocusEnabled(false);
        listRoster.setValueIsAdjusting(true);
        listRoster.setVisibleRowCount(4);
        listRoster.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listRosterValueChanged(evt);
            }
        });
        NameSelectionPane.setViewportView(listRoster);

        jSplitPane1.setRightComponent(NameSelectionPane);
        NameSelectionPane.getAccessibleContext().setAccessibleName("NameSelectionPane");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jSplitPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0E-4;
        gridBagConstraints.insets = new java.awt.Insets(2, 10, 10, 10);
        getContentPane().add(jSeparator2, gridBagConstraints);

        VerifyButton.setText("Accept");
        VerifyButton.setToolTipText("Accept that these name-to-exam pairs will be used for scoring results.");
        VerifyButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        VerifyButton.setMargin(new java.awt.Insets(2, 4, 2, 4));
        VerifyButton.setMaximumSize(new java.awt.Dimension(85, 35));
        VerifyButton.setNextFocusableComponent(CloseButton);
        VerifyButton.setPreferredSize(new java.awt.Dimension(85, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 102);
        getContentPane().add(VerifyButton, gridBagConstraints);

        CloseButton.setText("Cancel");
        CloseButton.setToolTipText("Does the same thing as the close button!");
        CloseButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloseButton.setMargin(new java.awt.Insets(2, 4, 2, 4));
        CloseButton.setMaximumSize(new java.awt.Dimension(85, 35));
        CloseButton.setMinimumSize(new java.awt.Dimension(85, 35));
        CloseButton.setPreferredSize(new java.awt.Dimension(85, 35));
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        getContentPane().add(CloseButton, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listRosterValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listRosterValueChanged
        int index = listRoster.getSelectedIndex();
        String desiredName = (String) listRoster.getSelectedValue();
        MyTableModel tModel = (MyTableModel) nameTable.getModel();
        // check that we chose a desired name
        if (desiredName != null)
        {
            tModel.setNameFromRoster(desiredName, chosenTableRow);

            listRoster.setEnabled(false);
            listRoster.setBackground(new Color(240, 240, 240));
            //NameSelectionPane.setPreferredSize(new Dimension(0, 0));
        }
    }//GEN-LAST:event_listRosterValueChanged

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CloseButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if (!verificationSuccessful) {
            JOptionPane.showMessageDialog(this, "Verification was cancelled, "
                    + "the resulting scores files were not generated.",
                "Notice", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_formWindowClosed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JLabel InstructionLabel;
    private javax.swing.JScrollPane NameSelectionPane;
    private javax.swing.JButton VerifyButton;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList listRoster;
    private javax.swing.JTable nameTable;
    // End of variables declaration//GEN-END:variables
    private MyTableModel tableModel;
    private JButton btnChoose;
    //  the row in the table the user chose to change
    private int chosenTableRow;
}
