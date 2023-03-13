/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.adminview;

import view.teacherview.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mcsmuscle
 */
public class TimetableFormFrame extends javax.swing.JFrame {
    
    private int class_id;

    
    /**
     * Creates new form StudentInformationFrame
     */
    public TimetableFormFrame() {
        initComponents();
        tableSetup();
        setListenerToComponent();
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
    
    private void setListenerToComponent() {
        JComboBox cbSubject = new JComboBox();
        cbSubject.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { null, "math", "physics", "chemistry", "english" }));
        
        // replace cell by textfield
        // only 3 cells are editable
        // so, we just replace only them
        for(int columnIndex = 0; columnIndex < 8; columnIndex++) {
            table.getColumnModel().getColumn(columnIndex).setCellEditor(new DefaultCellEditor(cbSubject));
        }
        // table get only number
        // if comment block of code below and spam char while edit a cell
        // the cell will be get char
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                e.consume();
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
            
            // change color of row when user select a row
            if (!isSelected) {
                c.setBackground(row % 2 == 0 ? new Color(102,102,102) : new Color(153,153,153));
            } else {
                c.setBackground(new Color(204,153,0));
            }
            return c;
        }
    }
    
    public void addAllRowToTable(Object[][] tableData) {
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        for(Object[] object : tableData) {
            ((DefaultTableModel) table.getModel()).addRow(object);
        }
    }
    
    private HashMap<Integer, Integer> mathTeacher_id = new HashMap<>();
    private HashMap<Integer, Integer> physicsTeacher_id = new HashMap<>();
    private HashMap<Integer, Integer> chemistryTeacher_id = new HashMap<>();
    private HashMap<Integer, Integer> englishTeacher_id = new HashMap<>();
    
    public void setMathTeacher(String[] object, List<Integer> teacher_id) {
        String[] teacherName = new String[object.length + 1];
        for(int i = 1; i < teacherName.length; i++) {
            teacherName[i] = object[i - 1];
        }
        
        cbMath.setModel(new javax.swing.DefaultComboBoxModel<>(teacherName));
        
        for(int i = 1; i < teacherName.length; i++) {
            mathTeacher_id.put(i, teacher_id.get(i - 1));
        }
    }
    
    public void setPhysicsTeacher(String[] object, List<Integer> teacher_id) {
        String[] teacherName = new String[object.length + 1];
        for(int i = 1; i < teacherName.length; i++) {
            teacherName[i] = object[i - 1];
        }
        cbPhysics.setModel(new javax.swing.DefaultComboBoxModel<>(teacherName));
        
        for(int i = 1; i < teacherName.length; i++) {
            physicsTeacher_id.put(i, teacher_id.get(i - 1));
        }
    }
    
    public void setChemistryTeacher(String[] object, List<Integer> teacher_id) {
        String[] teacherName = new String[object.length + 1];
        for(int i = 1; i < teacherName.length; i++) {
            teacherName[i] = object[i - 1];
        }
        cbChemistry.setModel(new javax.swing.DefaultComboBoxModel<>(teacherName));
        
        for(int i = 1; i < teacherName.length; i++) {
            chemistryTeacher_id.put(i, teacher_id.get(i - 1));
        }
    }
    
    public void setEnglishTeacher(String[] object, List<Integer> teacher_id) {
        String[] teacherName = new String[object.length + 1];
        for(int i = 1; i < teacherName.length; i++) {
            teacherName[i] = object[i - 1];
        }
        cbEnglish.setModel(new javax.swing.DefaultComboBoxModel<>(teacherName));
        
        for(int i = 1; i < teacherName.length; i++) {
            englishTeacher_id.put(i, teacher_id.get(i - 1));
        }
    }
    
    public int getMathTeacher_id() {
        if (cbMath.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Please select teacher");
            return -1;
        }
        return mathTeacher_id.get(cbMath.getSelectedIndex());
    }
    public int getPhysicsTeacher_id() {
        if (cbPhysics.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Please select teacher");
            return -1;
        }
        return physicsTeacher_id.get(cbPhysics.getSelectedIndex());
    }
    public int getChemistryTeacher_id() {
        if (cbChemistry.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Please select teacher");
            return -1;
        }
        return chemistryTeacher_id.get(cbChemistry.getSelectedIndex());
    }
    public int getEnglishTeacher_id() {
        if (cbEnglish.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Please select teacher");
            return -1;
        }
        return englishTeacher_id.get(cbEnglish.getSelectedIndex());
    }
    
    public JTable getTable() {
        return table;
    }
    
    public void setClassName(String className) {
        lbClassName.setText(className);
    }
    
    public int getClass_id() {
        return class_id;
    }
    
    public void setClass_id(int class_id) {
        this.class_id = class_id;
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
        cbEnglish = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        lbClassName = new javax.swing.JLabel();
        cbChemistry = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbPhysics = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cbMath = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));

        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        table.setBackground(new java.awt.Color(51, 51, 51));
        table.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        table.setForeground(new java.awt.Color(255, 255, 255));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"7h30", "", "", "", "", "", "", ""},
                {"8h30", null, null, null, null, null, null, null},
                {"9h30", null, null, null, null, null, null, null},
                {"10h30", null, null, null, null, null, null, null},
                {"11h30", null, null, null, null, null, null, null},
                {"13h00", null, null, null, null, null, null, null},
                {"14h00", null, null, null, null, null, null, null},
                {"15h00", null, null, null, null, null, null, null},
                {"16h00", null, null, null, null, null, null, null},
                {"17h00", null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
            }
        ));
        table.setGridColor(new java.awt.Color(255, 255, 255));
        table.setRowHeight(60);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        scrollPane.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMinWidth(50);
            table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(0).setMaxWidth(50);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
            table.getColumnModel().getColumn(6).setResizable(false);
            table.getColumnModel().getColumn(7).setResizable(false);
        }

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        cbEnglish.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbEnglish.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("english");

        lbClassName.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        lbClassName.setForeground(new java.awt.Color(255, 153, 153));
        lbClassName.setText("ClassName");

        cbChemistry.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbChemistry.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("chemistry");

        cbPhysics.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbPhysics.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("physics");

        cbMath.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbMath.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("math");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(cbMath, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(cbPhysics, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(cbChemistry, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(cbEnglish, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(102, 102, 102)
                .addComponent(lbClassName, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbClassName)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbMath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbPhysics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbChemistry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cbEnglish, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(57, 57, 57))
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
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            java.util.logging.Logger.getLogger(TimetableFormFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TimetableFormFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TimetableFormFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TimetableFormFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TimetableFormFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbChemistry;
    private javax.swing.JComboBox<String> cbEnglish;
    private javax.swing.JComboBox<String> cbMath;
    private javax.swing.JComboBox<String> cbPhysics;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbClassName;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
