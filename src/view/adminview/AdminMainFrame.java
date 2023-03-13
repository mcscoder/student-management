/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.adminview;

import controllers.AdminController;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import view.loginview.LoginMainFrame;

import model.Teacher;
import model.Student;

/**
 *
 * @author mcsmuscle
 */
public class AdminMainFrame extends javax.swing.JFrame {
    private final String STUDENTS_FORM          = "1";
    private final String TEACHERS_FORM          = "2";
    private final String ADD_NEW_STUDENT_FORM   = "3";
    private final String ADD_NEW_TEACHER_FORM   = "4";
    private final String CLASSES_FORM           = "5";
    
    private AdminController adminController = new AdminController();
    private CardLayout cardLayout;
    
    private StudentsForm classesForm;
    private TeachersForm teachersForm;
    private AddNewStudentForm addNewStudentForm;
    private AddNewTeacherForm addNewTeacherForm;
    private ClassesForm timeTableForm;
    
    /**
     * Creates new form AdminMainFrame
     */
    public AdminMainFrame() {
        initComponents();
        cardLayoutSetup();
        addListenerToComponent();
        
        setLocationRelativeTo(null);    
    }
    
    private void cardLayoutSetup() {
        mainPanel.setLayout(new CardLayout());
        cardLayout = (CardLayout) mainPanel.getLayout();
        
        classesForm = new StudentsForm();
        mainPanel.add(classesForm, STUDENTS_FORM);
    }
    
    private void addListenerToComponent() {
        // Add action listener to tobClasses
        tobStudents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unSelectToggleButton();
                tobStudents.setSelected(true);
                
                classesForm = new StudentsForm();
                mainPanel.add(classesForm, STUDENTS_FORM);
                cardLayout.show(mainPanel, STUDENTS_FORM);
            }
        });
        
        // Add action listener to tobTeachers
        tobTeachers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unSelectToggleButton();
                tobTeachers.setSelected(true);
                
                teachersForm = new TeachersForm();
                mainPanel.add(teachersForm, TEACHERS_FORM);
                cardLayout.show(mainPanel, TEACHERS_FORM);
            }
        });
        
        // Add action listener to tobAddNewStudent
        tobAddNewStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unSelectToggleButton();
                tobAddNewStudent.setSelected(true);
                
                addNewStudentForm = new AddNewStudentForm();
                mainPanel.add(addNewStudentForm, ADD_NEW_STUDENT_FORM);
                cardLayout.show(mainPanel, ADD_NEW_STUDENT_FORM);
            }
        });
        
        // Add action listener to tobAddNewTeacher
        tobAddNewTeacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unSelectToggleButton();
                tobAddNewTeacher.setSelected(true);
                
                addNewTeacherForm = new AddNewTeacherForm();
                mainPanel.add(addNewTeacherForm, ADD_NEW_TEACHER_FORM);
                cardLayout.show(mainPanel, ADD_NEW_TEACHER_FORM);
            }
        });
        
        // Add action listener to tobAddNewTeacher
        tobTimeTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unSelectToggleButton();
                tobTimeTable.setSelected(true);
                
                timeTableForm = new ClassesForm();
                mainPanel.add(timeTableForm, CLASSES_FORM);
                cardLayout.show(mainPanel, CLASSES_FORM);
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
    
    class StudentsForm extends StudentsFormPanel {
        ChangePasswordFrame changePasswordFrame;
        
        public StudentsForm() {
            addAllRowToTable(adminController.getStudentInformationForTable());
        }
        @Override
        public void mitEditActionPerformed() {
            int sequenceNumber = getSequenceNumber();
            int student_id = getStudent_id(sequenceNumber);
            StudentEditInformationFrame studentEditInformationFrame;
            
            // init data
            studentEditInformationFrame = new StudentEditInformationFrame(adminController.getStudentInformation(student_id));
            studentEditInformationFrame.setVisible(true);
            
            // add action listener for btnSave
            studentEditInformationFrame.btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // get update confirm
                    // yes=0, no=1, cancel=2
                    int status = studentEditInformationFrame.getSaveInformationConfirmationStatus();
                    if (status == 0) {
                        Student student = studentEditInformationFrame.getStudent();
                        
                        // update information to the database
                        adminController.updateStudentInformation(student);
                        
                        // alse update on the table
                        updateInformationOnTable(sequenceNumber, student.getFirstName(), 
                                student.getLastName(), student.getStudentCode(), student.getClassName());
                    }
                }
            });
            
            // Show change password frame
            studentEditInformationFrame.tobChangePassword.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (studentEditInformationFrame.tobChangePassword.isSelected()) {
                        changePasswordFrame = new ChangePasswordFrame();
                        changePasswordFrame.setVisible(true);
                        
                        // apply password to the database and close the frame
                        changePasswordFrame.btnChangePassword.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String newPassword = changePasswordFrame.getNewPassword();
                                if (newPassword != null) {
                                    adminController.updateNewPasswordForStudent(newPassword, student_id);
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
                                studentEditInformationFrame.tobChangePassword.setSelected(false);
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
    
    class TeachersForm extends TeachersFormPanel {
        ChangePasswordFrame changePasswordFrame;
        public TeachersForm() {
            addAllRowToTable(adminController.getTeacherInformationForTable());
            
        }
        
        @Override
        public void mitEditActionPerformed() {
            int sequenceNumber = getSequenceNumber();
            int teacher_id = getTeacher_id();
            TeacherEditInformationFrame teacherEditInformationFrame;
            
            // init data
            teacherEditInformationFrame = new TeacherEditInformationFrame(adminController.getTeacherInformation(teacher_id));
            teacherEditInformationFrame.setVisible(true);
            
            // Add action listener to btnSave
            teacherEditInformationFrame.btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // get update confirm
                    // yes=0, no=1, cancel=2
                    int status = teacherEditInformationFrame.getSaveInformationConfirmationStatus();
                    if (status == 0) {
                        Teacher teacher = teacherEditInformationFrame.getTeacher();
                        
                        // update information to the database
                        adminController.updateTeacherInformation(teacher);
                        
                        // also update on the table
                        updateInformationOnTable(sequenceNumber, 
                                teacher.getFirstName(), teacher.getLastName(), teacher.getSubject());
                    }
                }
            });
            
            // Show change password frame
            teacherEditInformationFrame.tobChangePassword.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (teacherEditInformationFrame.tobChangePassword.isSelected()) {
                        changePasswordFrame = new ChangePasswordFrame();
                        changePasswordFrame.setVisible(true);
                        
                        // apply password to the database and close the frame
                        changePasswordFrame.btnChangePassword.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String newPassword = changePasswordFrame.getNewPassword();
                                if (newPassword != null) {
                                    adminController.updateNewPasswordForTeacher(newPassword, teacher_id);
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
                                teacherEditInformationFrame.tobChangePassword.setSelected(false);
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
    
    class AddNewStudentForm extends AddNewStudentFormPanel {
        public AddNewStudentForm() {
            btnAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // get update confirm
                    // yes=0, no=1, cancel=2
                    int status = getAddStudentConfirmationStatus();
                    if (status == 0) {
                        Student student = getStudent();
                        adminController.addNewStudent(student);
                    }
                }
            });
        }
    }
    
    class AddNewTeacherForm extends AddNewTeacherFormPanel {
        public AddNewTeacherForm() {
            btnAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // get update confirm
                    // yes=0, no=1, cancel=2
                    int status = getAddTeacherConfirmationStatus();
                    if (status == 0) {
                        Teacher teacher = getTeacher();
                        adminController.addNewTeacher(teacher);
                    }
                }
            });
        }
    }
    
    class ClassesForm extends ClassesFormPanel {
        public ClassesForm() {
        }

        @Override
        public void mitEditActionPerformed() {
            int class_id =  Integer.valueOf((String) table.getValueAt(table.getSelectedRow(), 0));
            TimetableFormFrame timetableFormFrame = new TimetableFormFrame();
            timetableFormFrame.setVisible(true);
            timetableFormFrame.setClass_id(class_id);
            timetableFormFrame.setClassName((String) table.getValueAt(table.getSelectedRow(), 1));
            timetableFormFrame.addAllRowToTable(adminController.getTimeTable(class_id));
            
            timetableFormFrame.setMathTeacher(adminController.getTeacher("math"), adminController.getTeacher_id());
            timetableFormFrame.setPhysicsTeacher(adminController.getTeacher("physics"), adminController.getTeacher_id());
            timetableFormFrame.setChemistryTeacher(adminController.getTeacher("chemistry"), adminController.getTeacher_id());
            timetableFormFrame.setEnglishTeacher(adminController.getTeacher("english"), adminController.getTeacher_id());
            
            timetableFormFrame.getTable().getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    int lesson = e.getFirstRow();
                    int day = e.getColumn();
                    TableModel model = (TableModel)e.getSource();
                    
                    String subject = (String) model.getValueAt(lesson, day);
                    System.out.println(subject);
                    
                    Integer teacher_id = 0;
                    
                    // remove subject
                    if (subject == null) {
                        System.out.println("Subject is null");
                        adminController.addToTimetable(-1, class_id, day, lesson);
                        return;
                    }
                    else if (subject.equals("math")) {
                        teacher_id = timetableFormFrame.getMathTeacher_id();
                    } else if (subject.equals("physics")) {
                        teacher_id = timetableFormFrame.getPhysicsTeacher_id();
                    } else if (subject.equals("chemistry")) {
                        teacher_id = timetableFormFrame.getChemistryTeacher_id();
                    } else if (subject.equals("english")) {
                        teacher_id = timetableFormFrame.getEnglishTeacher_id();
                    }
                    
                    if (teacher_id == 0) {
                        return;
                    }
                    if (teacher_id == -1) {
                        model.setValueAt("", lesson, day);
                        return;
                    }
                    
                    // update after filled information
                    adminController.addToTimetable(teacher_id, class_id, day, lesson);
                }
            });
        }
    }
    
    private void unSelectToggleButton() {
        tobStudents.setSelected(false);
        tobTeachers.setSelected(false);
        tobAddNewStudent.setSelected(false);
        tobAddNewTeacher.setSelected(false);
        tobTimeTable.setSelected(false);
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
        tobStudents = new javax.swing.JToggleButton();
        tobTeachers = new javax.swing.JToggleButton();
        tobAddNewStudent = new javax.swing.JToggleButton();
        tobAddNewTeacher = new javax.swing.JToggleButton();
        tobTimeTable = new javax.swing.JToggleButton();
        mainPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/java_logo.png"))); // NOI18N

        tobStudents.setBackground(new java.awt.Color(153, 153, 153));
        tobStudents.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        tobStudents.setForeground(new java.awt.Color(255, 255, 255));
        tobStudents.setSelected(true);
        tobStudents.setText("Students");
        tobStudents.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tobStudents.setFocusPainted(false);

        tobTeachers.setBackground(new java.awt.Color(153, 153, 153));
        tobTeachers.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        tobTeachers.setForeground(new java.awt.Color(255, 255, 255));
        tobTeachers.setText("Teachers");
        tobTeachers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tobTeachers.setFocusPainted(false);

        tobAddNewStudent.setBackground(new java.awt.Color(153, 153, 153));
        tobAddNewStudent.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        tobAddNewStudent.setForeground(new java.awt.Color(255, 255, 255));
        tobAddNewStudent.setText("Add new Student");
        tobAddNewStudent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tobAddNewStudent.setFocusPainted(false);

        tobAddNewTeacher.setBackground(new java.awt.Color(153, 153, 153));
        tobAddNewTeacher.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        tobAddNewTeacher.setForeground(new java.awt.Color(255, 255, 255));
        tobAddNewTeacher.setText("Add new teacher");
        tobAddNewTeacher.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tobAddNewTeacher.setFocusPainted(false);

        tobTimeTable.setBackground(new java.awt.Color(153, 153, 153));
        tobTimeTable.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        tobTimeTable.setForeground(new java.awt.Color(255, 255, 255));
        tobTimeTable.setText("Timetable");
        tobTimeTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tobTimeTable.setFocusPainted(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tobStudents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tobTeachers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tobAddNewStudent, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addComponent(tobAddNewTeacher, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addComponent(tobTimeTable, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(tobStudents, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(tobTeachers, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(tobAddNewStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(tobAddNewTeacher, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(tobTimeTable, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 0));
        jLabel2.setText("administrator");

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
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addGap(434, 434, 434)
                .addComponent(btnLogout)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel2)
                .addContainerGap(13, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addGap(30, 30, 30))
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
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(AdminMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminMainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JToggleButton tobAddNewStudent;
    private javax.swing.JToggleButton tobAddNewTeacher;
    private javax.swing.JToggleButton tobStudents;
    private javax.swing.JToggleButton tobTeachers;
    private javax.swing.JToggleButton tobTimeTable;
    // End of variables declaration//GEN-END:variables
}
