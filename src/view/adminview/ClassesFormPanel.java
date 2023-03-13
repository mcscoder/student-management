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
public abstract class ClassesFormPanel extends javax.swing.JPanel {
    private List<Object[]> tableData;
    /**
     * Creates new form ClassListFormPanel
     */
    public ClassesFormPanel() {
        initComponents();
        tableSetup();
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
        
        table.setComponentPopupMenu(getMyPopupMenu());
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
    
    public int getSequenceNumber() {
        return (Integer) table.getValueAt(table.getSelectedRow(), 0);
    }
    
    public void addAllRowToTable(List<Object[]> tableData) {
        this.tableData = tableData;
        tableDataSort();
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        int i = 1;
        for(Object[] object : tableData) {
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

        table.setBackground(new java.awt.Color(51, 51, 51));
        table.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        table.setForeground(new java.awt.Color(255, 255, 255));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "9/10"},
                {"2", "9/11"},
                {"3", "9/12"},
                {"4", "9/13"}
            },
            new String [] {
                "", "Class"
            }
        ));
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
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 694, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollPane;
    protected javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
