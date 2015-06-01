package com.universalquantification.examgrader.ui.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.universalquantification.examgrader.grader.RosterEntry;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.models.Student;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * Partially functioning prototype of a verify dialog.
 * @author jdalbey
 */
public class VerifyDialog extends javax.swing.JFrame
{ 
    private List<RosterEntry> roster;

    /**
     * Creates new form VerifyDialog
     */
    public VerifyDialog(List<Student> matchResults, List<RosterEntry> roster)
    {
        this.roster = roster;
        tableModel = new MyTableModel(matchResults);

        initComponents();
        TableCellRenderer defaultRenderer = nameTable.getDefaultRenderer(
                JButton.class);
        nameTable.setDefaultRenderer(JButton.class,
                new JTableButtonRenderer(defaultRenderer));
        
        nameTable.getColumnModel().getColumn(0).setPreferredWidth(260);
        nameTable.getColumnModel().getColumn(1).setPreferredWidth(260);
        nameTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        
        TableColumn chooseColumn = nameTable.getColumnModel().getColumn(5);
        
        chooseColumn.setCellEditor(new ButtonColumn());
        // The JList needs a listener that when a row is clicked,
        // it will set that value into the table at the row
        // saved in the instance field "chosenTableRow".
        // then disable the JList.
        
         setLocationRelativeTo(null);
    }
    
    public void addFinishedListener(ActionListener listener)
    {
        VerifyButton.addActionListener(listener);  
    }

    class MyTableModel extends AbstractTableModel
    {
        String[] columnNames =
        {
            "First Name Image",
            "Last Name Image",
            "First Name",
            "Last Name",
            "Confidence",
            "Change Name",
        };
        
        private String[][] model;
        
        private List<Student> matchResults;
        
        private List<JButton> buttons; 
        
        MyTableModel(List<Student> matchResults)
        {
            this.matchResults = matchResults;
            this.buttons = new ArrayList<>();
            
            for (int i = 0; i < matchResults.size(); i++)
            {
                buttons.add(mkChooseBtn(i));
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
            if (col == 0)
            {
                return new ImageIcon(student.getFirstNameImage());
            }
            else if (col == 1)
            {
                return new ImageIcon(student.getLastNameImage());
            }
            else if (col == 2)
            {
                return student.getFirstName();
            }
            else if (col == 3)
            {
                return student.getLastName();
            }
            else if (col == 4)
            {
                return student.getConfidence();
            }
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
            return true;
        }

        @Override
        public Class getColumnClass(int column)
        {
            return getValueAt(0, column).getClass();
        }
        
        public void setNameFromRoster(String value, int row)
        {
            String[] name = value.split(", ");
            setValueAt(name[1], row, 2);
            setValueAt(name[0], row, 3);
        }

        public void setValueAt(Object value, int row, int col)
        {
            if (col == 2)
            {
                matchResults.get(row).setFirstName(value.toString());
            }
            else if (col == 3)
            {   
                matchResults.get(row).setlastName(value.toString());
            }
            fireTableCellUpdated(row, col);
        }
    }

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
            if (value instanceof Component)
            {
                return (Component) value;
            }
            return defaultRenderer.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
        }
    }

    class ButtonColumn extends AbstractCellEditor implements TableCellEditor
    {
        JButton tempButton;

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
    private void initComponents()
    {
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bubblit Verification Dialog");
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
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
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

        NameSelectionPane.setMinimumSize(new java.awt.Dimension(0, 0));
        NameSelectionPane.setPreferredSize(new java.awt.Dimension(240, 66));
        NameSelectionPane.setRequestFocusEnabled(false);

        listRoster.setModel(createListModel());
        listRoster.setAutoscrolls(false);
        listRoster.setPreferredSize(new java.awt.Dimension(200, 0));
        listRoster.setRequestFocusEnabled(false);
        listRoster.setValueIsAdjusting(true);
        listRoster.setVisibleRowCount(4);
        listRoster.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
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
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jSplitPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 10, 10, 10);
        getContentPane().add(jSeparator2, gridBagConstraints);

        VerifyButton.setText("Verify");
        VerifyButton.setToolTipText("");
        VerifyButton.setMargin(new java.awt.Insets(2, 20, 2, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        getContentPane().add(VerifyButton, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listRosterValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listRosterValueChanged
        int index = listRoster.getSelectedIndex();
        String desiredName = (String) listRoster.getSelectedValue();
        MyTableModel tModel = (MyTableModel) nameTable.getModel();
        //TODO: We might want to search the table to see if the desiredName
        // is already in the table, and if so, change it to "unknown".
        if (desiredName != null) {
            tModel.setNameFromRoster(desiredName, chosenTableRow);

            listRoster.setEnabled(false);
            
            listRoster.setBackground(new Color(240, 240, 240));
            //NameSelectionPane.setPreferredSize(new Dimension(0, 0));
        }
    }//GEN-LAST:event_listRosterValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
