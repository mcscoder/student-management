/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.adminview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mcsmuscle
 */
public abstract class StudentsFormPanel extends javax.swing.JPanel {
    private List<Object[]> tableData;
    private HashMap<Integer, Integer> student_id = new HashMap<>();
    /**
     * Creates new form ClassListFormPanel
     */
    public StudentsFormPanel() {
        initComponents();
        tableSetup();
        addListenerToComponent();
    }
    
    public void tableSetup() {
        // setup for table header
        table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(), 40));
        for (int i = 0; i < table.getModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(new MyTableHeaderRenderer());
        }
        
        // setup for table body
        table.setDefaultRenderer(Object.class, new MyTableBodyRenderer());
        
        // change table background
        scrollPane.getViewport().setBackground(new Color(51,51,51));
        
        // sort
        table.setAutoCreateRowSorter(true);
        
        table.setComponentPopupMenu(getMyPopupMenu());
    }
    
    private void addListenerToComponent() {
        tfSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                search(tfSearch.getText(), cbColTarget.getSelectedIndex());
            }
        });
        // add listener for combo box ColTarget
        cbColTarget.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search(tfSearch.getText(), cbColTarget.getSelectedIndex());
            }
        });
    }
    
    class MyTableHeaderRenderer extends DefaultTableCellRenderer {
        // set center text
        @Override
        public void setHorizontalAlignment(int alignment) {
            super.setHorizontalAlignment(JLabel.CENTER);
        }
        
        // set background color for table header
        // https://stackoverflow.com/a/15280574/20232773
        @Override
        public void setBackground(Color c) {
            super.setBackground(Color.BLACK);
        }
        
        // set text color
        @Override
        public void setForeground(Color c) {
            super.setForeground(Color.WHITE);
        }
        
        // set font
        @Override
        public void setFont(Font font) {
            super.setFont(new Font("Dialog", Font.BOLD, 25));
        }
        
        // set gridline and height
        // chatGPT answer
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            ((JComponent)c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.WHITE));
            return c;
        }
    }
    
    class MyTableBodyRenderer extends DefaultTableCellRenderer {
        // set center text
        @Override
        public void setHorizontalAlignment(int alignment) {
            super.setHorizontalAlignment(JLabel.CENTER);
        }
        
        // change row color
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBorder(noFocusBorder); // remove cell border when selected
            
            // change row color when user select
            if (!isSelected) {
                c.setBackground(row % 2 == 0 ? new Color(102,102,102) : new Color(153,153,153));
            } else {
                c.setBackground(new Color(204,153,0));
            }
            return c;
        }
    }
    
    class MyPopupMenu extends JPopupMenu {
        protected JMenuItem mitEdit = new JMenuItem("Edit");
        public MyPopupMenu() {
            add(mitEdit);
            
            // Set action listener for mitedit
            mitEdit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                     mitEditActionPerformed();
                }
            });
        }
    }
    
    private MyPopupMenu getMyPopupMenu() {
        MyPopupMenu popupMenu = new MyPopupMenu();
        popupMenu.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int rowAtPoint = table.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), table));
                        if (rowAtPoint > -1) {
                            table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                // TODO Auto-generated method stub

            }
        });
        
        return popupMenu;
    }
    
    public abstract void mitEditActionPerformed();
    
    public void updateInformationOnTable(int rowIndex, String firstName, String lastName, String studentCode, String className) {
        
        // update value in tableData array list
        tableData.get(rowIndex - 1)[1] = firstName;
        tableData.get(rowIndex - 1)[2] = lastName;
        tableData.get(rowIndex - 1)[3] = studentCode;
        tableData.get(rowIndex - 1)[4] = className;
        
        // update value on table
        for(int i = 0; i < table.getRowCount(); i++) {
            if ((Integer) table.getValueAt(i, 0) == rowIndex) {
                table.setValueAt(firstName, i, 1);
                table.setValueAt(lastName, i, 2);
                table.setValueAt(studentCode, i, 3);
                table.setValueAt(className, i, 4);
            }
        }
    }
    
    public void addAllRowToTable(List<Object[]> tableData) {
        this.tableData = tableData;
        tableDataSort();
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        int i = 1;
        for(Object[] object : tableData) {
            student_id.put(i, (Integer) object[object.length - 1]);
            object[0] = i;
            i++;
            ((DefaultTableModel) table.getModel()).addRow(object);
        }
    }
    
    private void tableDataSort() {
        Collections.sort(tableData, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                return String.valueOf(o1[1]).compareTo(String.valueOf(o2[1]));
            }       
        });
    }

    // Search feature
    private void search(String target, int selectedOptionIndex) {
        // cause array start from 0
        selectedOptionIndex += 1;
        
        // remove all of row on the table first
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        
        // then add values that match to the UI table
        for(Object[] object : tableData) {
            String columnTarget = String.valueOf(object[selectedOptionIndex]).toLowerCase();
            int isExist = columnTarget.indexOf(target.toLowerCase());
            if (isExist != -1) {
                ((DefaultTableModel) table.getModel()).addRow(object); 
            }
        }
    }
    
    public int getSequenceNumber() {
        return (Integer) table.getValueAt(table.getSelectedRow(), 0);
    }
    
    public int getStudent_id(int sequenceNumber) {
        return student_id.get(sequenceNumber);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        tfSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbColTarget = new javax.swing.JComboBox<>();

        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        table.setBackground(new java.awt.Color(51, 51, 51));
        table.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        table.setForeground(new java.awt.Color(255, 255, 255));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"123", "123", "123", null, null},
                {"123", "123", "123", null, null},
                {"123", "123", "123", null, null}
            },
            new String [] {
                "", "Firstname", "Lastname", "Student code", "Class"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setGridColor(new java.awt.Color(255, 255, 255));
        table.setRowHeight(30);
        table.setSelectionBackground(new java.awt.Color(204, 153, 0));
        table.setSelectionForeground(new java.awt.Color(255, 255, 255));
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(false);
        scrollPane.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMinWidth(50);
            table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(0).setMaxWidth(50);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        tfSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Search");

        cbColTarget.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cbColTarget.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Firstname", "Lastname", "Student code", "Class name" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(362, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbColTarget, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbColTarget)
                    .addComponent(tfSearch))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbColTarget;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane scrollPane;
    protected javax.swing.JTable table;
    private javax.swing.JTextField tfSearch;
    // End of variables declaration//GEN-END:variables
}
