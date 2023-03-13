/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
*/
package view.teacherview;

import controllers.TeacherController;
import model.Teacher;

import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import view.loginview.LoginMainFrame;


/**
 *
 * @author mcsmuscle
 */
public class TeacherMainFrame extends javax.swing.JFrame {
    
    TeacherController teacherController;
    private final String TIME_TABLE_FORM        = "1";
    private final String CLASSES_FORM           = "2";
    private final String ACCOUNT_SETTING_FORM   = "3";
    
    private TimeTableForm timeTableForm;
    private ClassesForm classesForm;
    private AccountSettingForm accountSettingForm;
    
    private Teacher teacher;
    
    CardLayout cardLayout;
    
    /**
     * Creates new form TeacherMainFrame
     */
    public TeacherMainFrame(Teacher teacher) {
        this.teacher = teacher;
        this.teacherController = new TeacherController(teacher);
        
        initComponents();
        initForm();
        cardLayoutSetup();
        setAction();
        setLocationRelativeTo(null);
    }
    
    private void initForm() {
        timeTableForm = new TimeTableForm();
        classesForm = new ClassesForm();
        accountSettingForm = new AccountSettingForm(teacher);
        
        lbUserFullname.setText(teacher.getFirstName() + " " + teacher.getLastName());
    }
    
    private void cardLayoutSetup() {
        mainPanel.setLayout(new CardLayout());
        cardLayout = (CardLayout) mainPanel.getLayout();
        
        mainPanel.add(timeTableForm, TIME_TABLE_FORM);
    }
    
    private void setAction() {
        // change view to time table view
        tobTimetable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unSelectToggleButton();
                tobTimetable.setSelected(true);
                
                timeTableForm = new TimeTableForm();
                mainPanel.add(timeTableForm, TIME_TABLE_FORM);
                cardLayout.show(mainPanel, TIME_TABLE_FORM);
            }
        });
        
        // change view to classes view
        tobClasses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unSelectToggleButton();
                tobClasses.setSelected(true);
                
                classesForm = new ClassesForm();
                mainPanel.add(classesForm, CLASSES_FORM);
                cardLayout.show(mainPanel, CLASSES_FORM);
                
                classesForm.addAllRowToTable(teacherController.getClassesInformation());
            }
        });
        
        // change view to account setting
        tobAccountSetting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unSelectToggleButton();
                tobAccountSetting.setSelected(true);
                
                accountSettingForm = new AccountSettingForm(teacher);
                mainPanel.add(accountSettingForm, ACCOUNT_SETTING_FORM);
                cardLayout.show(mainPanel, ACCOUNT_SETTING_FORM);
            }
        });
        
        // Logout
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginMainFrame().setVisible(true);
            }
        });
    }
    
    private void unSelectToggleButton() {
        tobTimetable.setSelected(false);
        tobClasses.setSelected(false);
        tobAccountSetting.setSelected(false);
    }
    
    class TimeTableForm extends TimeTableFormPanel {
        public TimeTableForm() {
            addAllRowToTable(teacherController.getTimeTable());
        }
    }
    
    class ClassesForm extends ClassesFormPanel {
        StudentInformationFrame studentInformationFrame;
        StudentScoreFrame studentScoreFrame;
        
        public ClassesForm() {
            // add action for view student information button
            tobViewStudentInformation.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int rowSelected = table.getSelectedRow();
                    if (rowSelected != -1) {
                        if (tobViewStudentInformation.isSelected()) {
                            studentInformationFrame = new StudentInformationFrame();
                            studentInformationFrame.setVisible(true);
                            studentInformationFrame.setLocation(getPopUpLocation());
                            
                            // add action when closing the window (close JFrame)
                            // https://stackoverflow.com/a/9093526
                            studentInformationFrame.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosed(WindowEvent e) {
                                    tobViewStudentInformation.setSelected(false);
                                }
                            });
                            
                            String className = (String) table.getValueAt(table.getSelectedRow(), 1);
                            studentInformationFrame.setLbClassName(className);
                            studentInformationFrame.addAllRowToTable(teacherController.getStudentInformation(className));
                        } else { // press button again to close frame
                            studentInformationFrame.dispose();
                            studentInformationFrame = null;
                        }
                    } else { // if there is no row selected, button is still unselected
                        tobViewStudentInformation.setSelected(false);
                    }
                }
            });
            
            // add action for view student score button
            tobViewStudentScore.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int rowSelected = table.getSelectedRow();
                    if (rowSelected != -1) {
                        if (tobViewStudentScore.isSelected()) {
                            studentScoreFrame = new StudentScoreFrame();
                            studentScoreFrame.setVisible(true);
                            studentScoreFrame.setLocation(getPopUpLocation());
                            
                            // add action when closing the window (close JFrame)
                            // https://stackoverflow.com/a/9093526
                            studentScoreFrame.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosed(WindowEvent e) {
                                    tobViewStudentScore.setSelected(false);
                                }
                            });
                            
                            String className = (String) table.getValueAt(table.getSelectedRow(), 1);
                            String subjectName = teacher.getSubject();
                            studentScoreFrame.setLbClassName(className);
                            studentScoreFrame.addAllRowToTable(teacherController.getStudentScore(subjectName, className));
                            
                            // listen any changed value when user edit the cell
                            // https://coderanch.com/t/338715/java/Update-database-editing-cells-JTable
                            studentScoreFrame.getTable().getModel().addTableModelListener(new TableModelListener() {
                                @Override
                                public void tableChanged(TableModelEvent e) {
                                    int rowOnTableUI = e.getFirstRow();
                                    int column = e.getColumn();
                                    if (column == 8 || column == -1) {
                                        return;
                                    }
                                    TableModel model = (TableModel)e.getSource();
                                    // Get score from the cell that has just been edited
                                    String score = (String) model.getValueAt(rowOnTableUI, column);//1.1
                                    
                                    // Get student ID from the row that has just been edited
                                    int student_id = studentScoreFrame.getStudent_id((int) model.getValueAt(rowOnTableUI, 0));//1.2
                                    
                                    // Get grades type from the column that has just been edited
                                    String gradesType = studentScoreFrame.getGradesType(column);
                                    
                                    // Update to table data first
                                    int tableDataRowIndex = (int) model.getValueAt(rowOnTableUI, 0) - 1; //2.1
                                    studentScoreFrame.updateScoreToTableData(tableDataRowIndex, column, score); //2.2
                                    
                                    // Then update to database
                                    // update component score
                                    teacherController.updateStudentScore(subjectName, gradesType, score, student_id);
                                    
                                    // update to overall score if all of component score is not empty
                                    String overallScore = studentScoreFrame.getOverallScore(tableDataRowIndex);
                                    if (overallScore == null) {
                                        overallScore = "";
                                    }
                                    teacherController.updateStudentScore(subjectName,
                                            studentScoreFrame.getGradesType(StudentScoreFrame.OVERALL_SCORE_COLUMN_INDEX),
                                            overallScore, student_id);
                                }
                            });
                        } else { // press button again to close frame
                            studentScoreFrame.dispose();
                            studentScoreFrame = null;
                        }
                    } else { // if there is no row is selected, button is still unselected
                        tobViewStudentScore.setSelected(false);
                    }
                }
            });
        }
        
    }
    
    class AccountSettingForm extends AccountSettingFormPanel {
        
        ChangePasswordFrame changePasswordFrame;
        
        public AccountSettingForm(Teacher teacher) {
            super(teacher);
            
            
            // add action for save button
            btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Apply all changed values to the teacher variable if the user confirms to save
                    // Then use that value to update to the database
                    // 0=yes, 1=no, 2=cancel
                    int status = getSaveInformationConfirmationStatus();
                    if (status != 0) {
                        return;
                    }
                    teacherController.updateTeacherInformation();
                }
            });
            
            // add action for change password button
            tobChangePassword.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tobChangePassword.isSelected()) {
                        changePasswordFrame = new ChangePasswordFrame(teacherController.getPassword());
                        changePasswordFrame.setVisible(true);
                        
                        // apply password to the database and close the frame
                        changePasswordFrame.btnChangePassword.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String newPassword = changePasswordFrame.getNewPassword();
                                if (newPassword != null) {
                                    teacherController.updateNewPassword(newPassword);
                                    changePasswordFrame.dispose();
                                    changePasswordFrame = null;
                                }
                            }
                        });
                        
                        // add action when closing the window (close JFrame)
                        // https://stackoverflow.com/a/9093526
                        changePasswordFrame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                tobChangePassword.setSelected(false);
                            }
                        });
                        
                    } else {
                        changePasswordFrame.dispose();
                        changePasswordFrame = null;
                    }
                }
            });
        }
    }
    
    private Point getPopUpLocation() {
        Point point = getLocationOnScreen();
        point.setLocation(point.getX() + 27, point.getY() + 27);
        return point;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tobTimetable = new javax.swing.JToggleButton();
        tobClasses = new javax.swing.JToggleButton();
        mainPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbUserFullname = new javax.swing.JLabel();
        tobAccountSetting = new javax.swing.JToggleButton();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/java_logo.png"))); // NOI18N

        tobTimetable.setBackground(new java.awt.Color(153, 153, 153));
        tobTimetable.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        tobTimetable.setForeground(new java.awt.Color(255, 255, 255));
        tobTimetable.setSelected(true);
        tobTimetable.setText("Timetable");
        tobTimetable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tobTimetable.setFocusPainted(false);

        tobClasses.setBackground(new java.awt.Color(153, 153, 153));
        tobClasses.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        tobClasses.setForeground(new java.awt.Color(255, 255, 255));
        tobClasses.setText("Classes");
        tobClasses.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tobClasses.setFocusPainted(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tobTimetable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tobClasses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(tobTimetable, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tobClasses, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 694, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 255, 255));
        jLabel2.setText("Hello");

        lbUserFullname.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lbUserFullname.setForeground(new java.awt.Color(255, 102, 0));
        lbUserFullname.setText("user fullname");

        tobAccountSetting.setBackground(new java.awt.Color(153, 153, 0));
        tobAccountSetting.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tobAccountSetting.setForeground(new java.awt.Color(204, 255, 255));
        tobAccountSetting.setText("Account settings");
        tobAccountSetting.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tobAccountSetting.setFocusPainted(false);

        btnLogout.setBackground(new java.awt.Color(204, 0, 102));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 204, 204));
        btnLogout.setText("Logout");
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbUserFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(tobAccountSetting)
                .addGap(18, 18, 18)
                .addComponent(btnLogout)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbUserFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tobAccountSetting)
                    .addComponent(btnLogout))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
            java.util.logging.Logger.getLogger(TeacherMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TeacherMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TeacherMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TeacherMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Teacher teacher = new Teacher();
                teacher.setTeacher_id(2);
                new TeacherMainFrame(teacher).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbUserFullname;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JToggleButton tobAccountSetting;
    private javax.swing.JToggleButton tobClasses;
    private javax.swing.JToggleButton tobTimetable;
    // End of variables declaration//GEN-END:variables
}
