/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.teacherview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mcsmuscle
 */
public class StudentInformationFrame extends javax.swing.JFrame {

    private List<Object[]> tableData;

    /**
     * Creates new form StudentInformationFrame
     */
    public StudentInformationFrame() {
        initComponents();
        tableSetup();
        addListenerToComponent();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void tableSetup() {
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
    }
    
    private void addListenerToComponent() {
        // Search feature
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // add listener for search text field
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
            super.setFont(new Font("Dialog", Font.BOLD, 16));
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
            
            if (!isSelected) {
                c.setBackground(row % 2 == 0 ? new Color(102,102,102) : new Color(153,153,153));
            } else {
                c.setBackground(new Color(204,153,0));
            }
            return c;
        }
    }
    
    public void addAllRowToTable(List<Object[]> tableData) {
        this.tableData = tableData;
        tableDataSort(1);
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        int i = 1;
        for(Object[] object : tableData) {
            object[0] = i;
            i++;
            ((DefaultTableModel) table.getModel()).addRow(object);
        }
    }
    
    private void tableDataSort(int i) {
        Collections.sort(tableData, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                return String.valueOf(o1[i]).compareTo(String.valueOf(o2[i]));
            }
        });
    }
    
    public void setLbClassName(String className) {
        lbClassName.setText(className);
    }
    
    // search feature
    private void search(String target, int selectedOptionIndex) {
        // cause array start from 0
        selectedOptionIndex += 1;
        // remove all of row on the table first
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        
        // then add values that match to the UI table
        for(Object[] object : tableData) {
            String columnTarget = String.valueOf(object[selectedOptionIndex]).toLowerCase();
            int isExist = columnTarget.indexOf(target.toLowerCase());
            if (isExist != -1 && selectedOptionIndex != 3 || isExist == 0) {
                ((DefaultTableModel) table.getModel()).addRow(object); 
            }
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

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lbClassName = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tfSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbColTarget = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));

        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        table.setBackground(new java.awt.Color(51, 51, 51));
        table.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        table.setForeground(new java.awt.Color(255, 255, 255));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"123", "Son", "Mai Cong", "Male", "123123123", "bo may ko biet", "123123123123", "1111-11-11", "maicongsondeptrai@gmail.com"},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "First name", "Last name", "Gender", "Student code", "Address", "Phone number", "Date of birth", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(30);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMinWidth(50);
            table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(0).setMaxWidth(50);
            table.getColumnModel().getColumn(1).setPreferredWidth(120);
            table.getColumnModel().getColumn(2).setPreferredWidth(120);
            table.getColumnModel().getColumn(3).setPreferredWidth(60);
            table.getColumnModel().getColumn(4).setPreferredWidth(120);
            table.getColumnModel().getColumn(5).setPreferredWidth(150);
            table.getColumnModel().getColumn(6).setPreferredWidth(120);
            table.getColumnModel().getColumn(7).setPreferredWidth(120);
            table.getColumnModel().getColumn(8).setPreferredWidth(340);
        }

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        lbClassName.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        lbClassName.setForeground(new java.awt.Color(255, 51, 51));
        lbClassName.setText("xxxxxxx");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Class:");

        tfSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Search");

        cbColTarget.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cbColTarget.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First name", "Last name", "Gender", "Student code", "Address", "Phone number", "Date of birth", "Email" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbClassName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lbClassName)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbColTarget)
                            .addComponent(tfSearch))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentInformationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentInformationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentInformationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentInformationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentInformationFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbColTarget;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbClassName;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private javax.swing.JTextField tfSearch;
    // End of variables declaration//GEN-END:variables
}
