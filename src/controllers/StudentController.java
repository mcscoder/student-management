/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import model.Database;
import model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Encrypt;


/**
 *
 * @author mcsmuscle
 */
public class StudentController {
    private Database database = new Database();
    
    private final java.sql.Connection conn = model.Database.getDBConnection();
    private final String[] SUBJECT_NAME = {"Math", "Physics", "Chemistry", "English"};
    private Student student;
    
    
    public StudentController(){}
    public StudentController(Student student) {
        this.student = student;
    }
    
    public Object[][] getTimeTable() {
        Object[][] timeTableDataStore = {
                // day     1     2     3     4     5     6     7
                //        mon   tue   wed   thu   fri   sat   sun       lesson
                {"7:30",  null, null, null, null, null, null, null},    // 0 7h30
                {"8:30",  null, null, null, null, null, null, null},    // 1 8h30
                {"9:30",  null, null, null, null, null, null, null},    // 2 9h30
                {"10:30", null, null, null, null, null, null, null},    // 3 10h30
                {"11:30", null, null, null, null, null, null, null},    // 4 11h30
                {"13:00", null, null, null, null, null, null, null},    // 5 13h00
                {"14:00", null, null, null, null, null, null, null},    // 6 14h00
                {"15:00", null, null, null, null, null, null, null},    // 7 15h00
                {"16:00", null, null, null, null, null, null, null},    // 8 16h00
                {"17:00", null, null, null, null, null, null, null}     // 9 17h00
            };
        try {
            String sqlStatement = "select * from timetable " +
                    "join teachers on teachers.id = timetable.teachers_id " +
                    "where classes_id = ?";
            ResultSet rs = database.executeQuery(sqlStatement, student.getClass_id());
            
            while(rs.next()) {
                int day = rs.getInt("day");
                int lesson = rs.getInt("lesson");
                String subject = rs.getString("Subject");
                timeTableDataStore[lesson][day] = subject;
            }
            System.out.println("Successfully retrieved teacher timetable from the database");
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return timeTableDataStore;
    }
    
    public List<Object[]> getStudentScore() {
        List<Object[]> classDataStore = new ArrayList<Object[]>();
        try {
            
            String sqlStatement = "select * from subjects " +
                    "join math_score on subjects.id = math_score.subjects_id " +
                    "join physics_score on subjects.id = physics_score.subjects_id " +
                    "join chemistry_score on subjects.id = chemistry_score.subjects_id " +
                    "join english_score on subjects.id = english_score.subjects_id " +
                    "where students_id = ?;";
            ResultSet rs = database.executeQuery(sqlStatement,student.getStudent_id());
            rs.next();
            for(int i = 0; i < 4; i++) {
                String subject = SUBJECT_NAME[i];
                String tableName = subject.concat("_score");
                Object[] object = 
                {
                    null, subject, 
                    rs.getString(tableName.concat(".ParticipationScore")),
                    rs.getString(tableName.concat(".MidtermScore")),
                    rs.getString(tableName.concat(".FinalScore")),
                    rs.getString(tableName.concat(".OverallScore"))
                };
                classDataStore.add(object);
            }
            System.out.println("Successfully retrieved student score data from the database");
            
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classDataStore;
    }
    
    public void updateStudentInformation() {
        String sqlStatement = "update students " +
                "set FirstName = ?, LastName = ?, Gender = ?, Address = ?, PhoneNumber = ?, DateOfBirth = ?, Email = ? " +
                "where id = ?";
        database.execute(sqlStatement, student.getFirstName(), student.getLastName(),
        student.getGender(), student.getAddress(), student.getPhoneNumber(), 
        student.getDob(), student.getEmail(), student.getStudent_id());
        System.out.println("Student information updated successfully");
    }
    
    public String getPassword() {
        String password = null;
        try {
            // admin: type = 1
            // teacher: type = 2
            // student: type = 3
            String sqlStatement = "select * from accounts where type = 3 and target_id = ?";
            ResultSet rs = database.executeQuery(sqlStatement, student.getStudent_id());
            rs.next();
            
            password = rs.getString("Password");
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return password;
    }
    
    public void updateNewPassword(String newPassword) {
        // admin: type = 1
        // teacher: type = 2
        // student: type = 3
        String sqlStatement = "update accounts " +
                "set Password = ? " +
                "where type = 3 and target_id = ?";
        database.execute(sqlStatement, Encrypt.encryptPassword(newPassword), student.getStudent_id());
        System.out.println("Student password updated successfully");
    }
}
