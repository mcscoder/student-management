/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controllers;

import model.Database;
import model.Student;
import model.Teacher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mcsmuscle
 */
public class AccountController {
    
    private Teacher teacher;
    private Student student;
    
    private Database database = new Database();
    
    private int type = -1;   // 1: admin, 2: teacher, 3: student
    
    public AccountController() {
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    
    public int getAuthentication(String username, String password) {
        try {
            password = model.Encrypt.encryptPassword(password);
            String sqlStatement;
            ResultSet rs;
            
            sqlStatement = "select * from accounts where username = ? and password = ?";
            rs = database.executeQuery(sqlStatement, username, password);
            if (rs.next()) {
                type = rs.getInt("Type");
                int target_id = rs.getInt("Target_id");
                
                // set data for teacher
                if (type == 2) {
                    sqlStatement = "select * from teachers where id = ?";
                    rs = database.executeQuery(sqlStatement, target_id);
                    rs.next();
                    teacher = new Teacher(rs.getInt("teachers.id"), null,
                            rs.getString("Subject"), rs.getString("FirstName"),
                            rs.getString("LastName"), rs.getString("Gender"),
                            rs.getString("Address"), rs.getString("PhoneNumber"),
                            rs.getString("DateOfBirth"), rs.getString("Email"));
                    
                    // teacher has many class
                    sqlStatement = "select classes_id from teachers_has_classes where teachers_id = ?";
                    rs = database.executeQuery(sqlStatement, target_id);
                    java.util.List<Integer> _classes_id = new java.util.ArrayList<>();
                    while (rs.next()) {
                        _classes_id.add(rs.getInt("classes_id"));
                    }
                    teacher.setClasses_id(_classes_id);
                }
                
                // set data for student, use list to store class id
                else if (type == 3) {
                    sqlStatement = "select * from students where id = ?";
                    rs = database.executeQuery(sqlStatement, target_id);
                    rs.next();
                    student = new Student(rs.getInt("id"), rs.getInt("classes_id"),
                            rs.getString("StudentCode"),rs.getString("FirstName"),
                            rs.getString("LastName"),rs.getString("Gender"),
                            rs.getString("Address"),rs.getString("PhoneNumber"),
                            rs.getString("DateOfBirth"),rs.getString("Email"));
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return type;
    }
    
    public static void main(String[] args) {
        AccountController acc = new AccountController();
        System.out.println(acc.getAuthentication("s2", "s2"));
        System.out.println(acc.getStudent().toString());
    }
}
