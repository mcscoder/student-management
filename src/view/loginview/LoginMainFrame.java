/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.loginview;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controllers.AccountController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import model.Student;
import model.Teacher;
import view.adminview.AdminMainFrame;

/**
 *
 * @author mcsmuscle
 */
public class LoginMainFrame extends javax.swing.JFrame {
    final String LOGIN_FORM = "1";
    final String FORGOT_PASSWORD_FORM = "2";
    
    private LoginForm loginForm;
    private ForgotPasswordForm forgotPasswordForm;
    private final AccountController accountController = new AccountController();
    
    CardLayout cardLayout;
    
    /**
     * Creates new form LoginFrame
     */
    public LoginMainFrame() {
        initComponents();
        initForm();
        cardLayoutSetUp();
        
        setLocationRelativeTo(null);
    }
    
    private void initForm() {
        loginForm = new LoginForm();
        forgotPasswordForm = new ForgotPasswordForm();
    }
    
    private void cardLayoutSetUp() {
        mainPanel.setLayout(new CardLayout());
        cardLayout = (CardLayout) mainPanel.getLayout();
        
        mainPanel.add(loginForm, LOGIN_FORM);
        
        cardLayout.show(mainPanel, LOGIN_FORM);
    }
    
    class LoginForm extends LoginFormPanel {
        public LoginForm() {
            setAction();
        }
        
        private void setAction() {
            tfUsername.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == 10) {
                        signIn();
                    }   
                }
            });
            
            tfPassword.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == 10) {
                        signIn();
                    }
                }
            });
            
            // switch to forgot password form
            lbForgotPassword.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    forgotPasswordForm = new ForgotPasswordForm();
                    mainPanel.add(forgotPasswordForm, FORGOT_PASSWORD_FORM);
                    cardLayout.show(mainPanel, FORGOT_PASSWORD_FORM);
                }
            });
            
            // get access to account
            btnSignIn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    signIn();
                }
            });
        }
        
        private void signIn() {
            String username = tfUsername.getText();
            String password = tfPassword.getText();
            int type = accountController.getAuthentication(username, password);
            
            // Invalid username or password
            if (type == -1) {
                JOptionPane.showMessageDialog(rootPane, "Invalid Username or Password",
                        "Invalid", JOptionPane.ERROR_MESSAGE);
            }
            
            // admin
            else if (type == 1) {
                System.out.println("Sucessfully logged in with Admin account");
                dispose();
                new AdminMainFrame().setVisible(true);
            }
            
            // teacher
            else if (type == 2) {
                System.out.println("Successfully logged in with Teacher account");
                
                // assign teacher information
                Teacher teacher = accountController.getTeacher();
                dispose(); // close login view
                new view.teacherview.TeacherMainFrame(teacher).setVisible(true);
            }
            
            // student
            else if (type == 3) {
                System.out.println("Successfully logged in with Student account");
                
                Student student = accountController.getStudent();
                dispose();
                new view.studentview.StudentMainFrame(student).setVisible(true);
            }
        }
    }
    
    class ForgotPasswordForm extends ForgotPasswordFormPanel {
        public ForgotPasswordForm() {
            lbGoBack.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    loginForm = new LoginForm();
                    mainPanel.add(loginForm, LOGIN_FORM);
                    cardLayout.show(mainPanel, LOGIN_FORM);
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

        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(LoginMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginMainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
