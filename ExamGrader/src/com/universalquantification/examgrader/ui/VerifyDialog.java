package com.universalquantification.examgrader.ui;

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
        TableColumn chooseColumn = nameTable.getColumnModel().getColumn(4);
        chooseColumn.setCellEditor(new ButtonColumn());
        // The JList needs a listener that when a row is clicked,
        // it will set that value into the table at the row
        // saved in the instance field "chosenTableRow".
        // then disable the JList.
        listRoster.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int index = listRoster.getSelectedIndex();
                String desiredName = (String) listRoster.getSelectedValue();
                MyTableModel tModel = (MyTableModel) nameTable.getModel();
                //TODO: We might want to search the table to see if the desiredName
                // is already in the table, and if so, change it to "unknown".
                tModel.setNameFromRoster(desiredName, chosenTableRow);
                listRoster.setEnabled(false);
            }
        });
    }
    
    public void addFinishedListener(ActionListener listener)
    {
        jButton1.addActionListener(listener);  
    }

    class MyTableModel extends AbstractTableModel
    {
        String[] columnNames =
        {
            "First Name Image",
            "Last Name Image",
            "First Name",
            "Last Name",
            "Choose",
            "Confidence",
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
                return buttons.get(row);
            }
            else if (col == 5)
            {  
                return student.getConfidence();
            }
            else 
            {
                return Boolean.FALSE;
            }
        }

        public boolean isCellEditable(int row, int col)
        {
            return true;
        }

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
        JButton btn = new JButton("Choose");
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

        jLabel1 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        listRoster = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        nameTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Verify Dialog");

        jLabel1.setText("Please verify the students have been correctly identified.");

        listRoster.setModel(createListModel());
        listRoster.setEnabled(false);
        jScrollPane2.setViewportView(listRoster);

        jSplitPane1.setRightComponent(jScrollPane2);

        nameTable.setModel(tableModel);
        jScrollPane1.setViewportView(nameTable);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jButton1.setText("OK");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(676, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList listRoster;
    private javax.swing.JTable nameTable;
    // End of variables declaration//GEN-END:variables
    private MyTableModel tableModel;
    private JButton btnChoose;
    //  the row in the table the user chose to change
    private int chosenTableRow;
}
