
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

/**
 * Partially functioning prototype of a verify dialog.
 * @author jdalbey
 */
public class VerifyDialog extends javax.swing.JFrame
{
    final String[] sampleNames =
    {
        "Bland", "Brown", "Doe", "Smith", "White"
    };
    
    /**
     * Creates new form VerifyDialog
     */
    public VerifyDialog()
    {
        tableModel = new MyTableModel();

        initComponents();
        TableCellRenderer defaultRenderer = nameTable.getDefaultRenderer(
                JButton.class);
        nameTable.setDefaultRenderer(JButton.class,
                new JTableButtonRenderer(defaultRenderer));
        TableColumn chooseColumn = nameTable.getColumnModel().getColumn(2);
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
                TableModel tModel = nameTable.getModel();
                //TODO: We might want to search the table to see if the desiredName
                // is already in the table, and if so, change it to "unknown".
                nameTable.setValueAt(desiredName, chosenTableRow, 1);
                listRoster.setEnabled(false);
            }
        });

    }

    class MyTableModel extends AbstractTableModel
    {
        ImageIcon demoImg = new ImageIcon("NameFields.png");
        String[] columnNames =
        {
            "Image",
            "Name",
            "Choose",
            "Confidence",
        };
        Object[][] rowData =
        {
            {
                demoImg, sampleNames[3],
                mkChooseBtn(0), new Integer(15), new Boolean(false)
            },
            {
                demoImg, sampleNames[1],
                mkChooseBtn(1), new Integer(33)
            },
            {
                demoImg, sampleNames[2],
                mkChooseBtn(2), new Integer(72)
            },
            {
                demoImg, sampleNames[4],
                mkChooseBtn(3), new Integer(80)
            },
            {
                demoImg, sampleNames[0],
                mkChooseBtn(4), new Integer(97)
            }
        };

        public String getColumnName(int col)
        {
            return columnNames[col].toString();
        }

        public int getRowCount()
        {
            return rowData.length;
        }

        public int getColumnCount()
        {
            return columnNames.length;
        }

        public Object getValueAt(int row, int col)
        {
            return rowData[row][col];
        }

        public boolean isCellEditable(int row, int col)
        {
            return true; //col == 2;
        }

        public Class getColumnClass(int column)
        {
            return getValueAt(0, column).getClass();
        }

        public void setValueAt(Object value, int row, int col)
        {
            rowData[row][col] = value;
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

    private DefaultListModel createListModel(String[] names)
    {
        DefaultListModel aModel = new DefaultListModel();
        for (String name : names)
        {
            aModel.addElement(name);
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
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        listRoster = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        nameTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Verify Dialog");

        jLabel1.setText("Please verify the students have been correctly identified.");

        listRoster.setModel(createListModel(sampleNames));
        listRoster.setEnabled(false);
        jScrollPane2.setViewportView(listRoster);

        jSplitPane1.setRightComponent(jScrollPane2);

        nameTable.setModel(tableModel);
        jScrollPane1.setViewportView(nameTable);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jButton1.setText("OK");

        jButton2.setText("Cancel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
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
            java.util.logging.Logger.getLogger(VerifyDialog.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(VerifyDialog.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(VerifyDialog.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(VerifyDialog.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new VerifyDialog().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
