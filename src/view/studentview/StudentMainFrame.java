/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
*/
package view.studentview;

import controllers.StudentController;
import model.Student;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import view.loginview.LoginMainFrame;


/**
 *
 * @author mcsmuscle
 */
public class StudentMainFrame extends javax.swing.JFrame {
    
    StudentController studentController;
    private final String TIME_TABLE_FORM        = "1";
    private final String STUDENT_SCORE_FORM     = "2";
    private final String ACCOUNT_SETTING_FORM   = "3";
    
    private TimeTableForm timeTableForm;
    private StudentScoreForm studentScoreForm;
    private AccountSettingForm accountSettingForm;
    
    private Student student;
    
    CardLayout cardLayout;
    
    /**
     * Creates new form TeacherMainFrame
     */
    public StudentMainFrame(Student student) {
        this.student = student;
        this.studentController = new StudentController(student);
        
        initComponents();
        initForm();
        cardLayoutSetup();
        setAction();
        setLocationRelativeTo(null);
    }
    
    private void initForm() {
        timeTableForm = new TimeTableForm();
        
        // say hello to users
        lbUserFullname.setText(student.getFirstName() + " " + student.getLastName());
        
        // display student code
        lbStudentCode.setText(student.getStudentCode());
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
        
        tobViewScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unSelectToggleButton();
                tobViewScore.setSelected(true);
                
                studentScoreForm = new StudentScoreForm();
                mainPanel.add(studentScoreForm, STUDENT_SCORE_FORM);
                cardLayout.show(mainPanel, STUDENT_SCORE_FORM);
            }
        });
        
        // change view to account setting
        tobAccountSetting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unSelectToggleButton();
                tobAccountSetting.setSelected(true);
                
                accountSettingForm = new AccountSettingForm(student);
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
        tobViewScore.setSelected(false);
        tobAccountSetting.setSelected(false);
    }
    
    class TimeTableForm extends TimeTableFormPanel {
        public TimeTableForm() {
            addAllRowToTable(studentController.getTimeTable());
        }
    }
    
    class StudentScoreForm extends StudentScoreFormPanel {
        public StudentScoreForm() {
            addAllRowToTable(studentController.getStudentScore());
        }
    }
    
    class AccountSettingForm extends AccountSettingFormPanel {
        
        ChangePasswordFrame changePasswordFrame;
        
        public AccountSettingForm(Student student) {
            super(student);
            
            
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
                    studentController.updateStudentInformation();
                }
            });
            
            // add action for change password button
            tobChangePassword.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tobChangePassword.isSelected()) {
                        changePasswordFrame = new ChangePasswordFrame(studentController.getPassword());
                        changePasswordFrame.setVisible(true);
                        
                        // apply password to the database and close the frame
                        changePasswordFrame.btnChangePassword.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String newPassword = changePasswordFrame.getNewPassword();
                                if (newPassword != null) {
                                    studentController.updateNewPassword(newPassword);
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
        tobViewScore = new javax.swing.JToggleButton();
        mainPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbUserFullname = new javax.swing.JLabel();
        tobAccountSetting = new javax.swing.JToggleButton();
        btnLogout = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lbStudentCode = new javax.swing.JLabel();

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

        tobViewScore.setBackground(new java.awt.Color(153, 153, 153));
        tobViewScore.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        tobViewScore.setForeground(new java.awt.Color(255, 255, 255));
        tobViewScore.setText("View Score");
        tobViewScore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tobViewScore.setFocusPainted(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tobTimetable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tobViewScore, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(tobViewScore, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(406, Short.MAX_VALUE))
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
            .addGap(0, 0, Short.MAX_VALUE)
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

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 255, 255));
        jLabel3.setText("Student code:");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lbStudentCode.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbStudentCode.setForeground(new java.awt.Color(255, 153, 0));
        lbStudentCode.setText("student codeeeeeeeee");
        lbStudentCode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbStudentCode, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbUserFullname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(49, 49, 49)
                .addComponent(tobAccountSetting)
                .addGap(18, 18, 18)
                .addComponent(btnLogout)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tobAccountSetting)
                    .addComponent(btnLogout))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbUserFullname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbStudentCode)
                    .addComponent(jLabel3))
                .addGap(0, 9, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(StudentMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Student student = new Student();
                student.setStudent_id(2);
                new StudentMainFrame(student).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbStudentCode;
    private javax.swing.JLabel lbUserFullname;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JToggleButton tobAccountSetting;
    private javax.swing.JToggleButton tobTimetable;
    private javax.swing.JToggleButton tobViewScore;
    // End of variables declaration//GEN-END:variables
}
