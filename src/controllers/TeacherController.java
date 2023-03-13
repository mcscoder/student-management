/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import model.Database;
import model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Encrypt;
import view.teacherview.StudentScoreFrame;


/**
 *
 * @author mcsmuscle
 */
public class TeacherController {
    private Database database = new Database();
    
    private final java.sql.Connection conn = model.Database.getDBConnection();
    private Teacher teacher;
    
    
    public TeacherController(){}
    public TeacherController(Teacher teacher) {
        this.teacher = teacher;
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
                    "join classes on classes.id = timetable.classes_id " +
                    "where teachers_id = ?;";
            ResultSet rs = database.executeQuery(sqlStatement, teacher.getTeacher_id());
            
            while(rs.next()) {
                int day = rs.getInt("day");
                int lesson = rs.getInt("lesson");
                String className = rs.getString("ClassName");
                timeTableDataStore[lesson][day] = className;
            }
            System.out.println("Successfully retrieved teacher timetable from the database");
        } catch (SQLException ex) {
            Logger.getLogger(TeacherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return timeTableDataStore;
    }
    
    public List<Object[]> getClassesInformation() {
        List<Object[]> classDataStore = new ArrayList<Object[]>();
        try {
            
            String sqlStatement = "select classes.ClassName, students.id from teachers_has_classes " +
                    "join classes on classes.id = teachers_has_classes.classes_id " +
                    "join students on classes.id = students.classes_id " +
                    "where teachers_id = ? order by classes.ClassName asc, students.FirstName asc";
            ResultSet rs = database.executeQuery(sqlStatement, teacher.getTeacher_id());
            HashMap<String, Integer> hashMap = new HashMap<>();
            
            while(rs.next()) {
                String className = rs.getString("ClassName");
                if (hashMap.get(className) == null) {
                    hashMap.put(className, 1);
                } else {
                    hashMap.put(className, hashMap.get(className) + 1);
                }
            }
            
            for(HashMap.Entry<String, Integer> set : hashMap.entrySet()) {
                Object[] objects = {null, set.getKey(), set.getValue()};
                classDataStore.add(objects);
            }
            System.out.println("Successfully retrieved all class information from the database");
        } catch (SQLException ex) {
            Logger.getLogger(TeacherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classDataStore;
    }
    
    public List<Object[]> getStudentInformation(String className) {
        List<Object[]> classDataStore = new ArrayList<Object[]>();
        try {
            
            
            String sqlStatement = "select * from students " +
                    "join classes on classes.id = students.classes_id " +
                    "where classes.ClassName like ?";
            ResultSet rs = database.executeQuery(sqlStatement, className);
            while (rs.next()) {
                Object[] object = 
                {
                    null, rs.getString("FirstName"), rs.getString("LastName"), 
                    rs.getString("Gender"), rs.getString("StudentCode"),
                    rs.getString("Address"), rs.getString("PhoneNumber"),
                    rs.getString("DateOfBirth"), rs.getString("Email")
                };
                classDataStore.add(object);
            }
            System.out.println("Successfully retrieved student information from the database");
            
        } catch (SQLException ex) {
            Logger.getLogger(TeacherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classDataStore;
    }
    
    
    public List<Object[]> getStudentScore(String subject, String className) {
        List<Object[]> classDataStore = new ArrayList<Object[]>();
        try {
            
            String sqlStatement = String.format("select * from classes " +
                    "join students on classes.id = students.classes_id " +
                    "join subjects on students.id = subjects.students_id " +
                    "join %s_score on subjects.id = %s_score.subjects_id " +
                    "where classes.ClassName = ?", subject, subject);
            ResultSet rs = database.executeQuery(sqlStatement,className);
            while (rs.next()) {
                Object[] object = 
                {
                    null, rs.getString("FirstName"), rs.getString("LastName"), 
                    rs.getString("Gender"), rs.getString("StudentCode"),
                    rs.getString("ParticipationScore"), rs.getString("MidtermScore"),
                    rs.getString("FinalScore"), rs.getString("OverallScore"), 
                    rs.getInt("students.id") 
                    // the last value "student.id" won't appear on the table
                    // it's would be define by Sequence number on the table and accessed via Key of HashMap
                    // if we want to query database via student.id
                    // just access it via Key of HashMap
                    // where key = Sequence number on the table and value = student.id
                    // The block of code that does this is written in StudentScoreFrame.java
                };
                classDataStore.add(object);
            }
            System.out.println("Successfully retrieved student score data from the database");
            
        } catch (SQLException ex) {
            Logger.getLogger(TeacherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classDataStore;
    }
    
    public void updateStudentScore(String subject, String gradesType, String score, int student_id) {
        
        String sqlStatement = String.format("update %s_score " +
                "set %s = '%s' " +
                "where subjects_id = %s;", subject, gradesType, score, student_id);
        database.execute(sqlStatement);
        
        // Change console message if overall score updated
        if (gradesType.equals(StudentScoreFrame.gradesType.get(StudentScoreFrame.OVERALL_SCORE_COLUMN_INDEX))) {
            System.out.println("Student overall score updated successfully");
            return;
        }
        System.out.println("Student score updated successfully");
    }
    
    // testing main
    public static void main(String[] args) {
        Teacher teacher = new Teacher();
        teacher.setTeacher_id(1);
        TeacherController controller = new TeacherController(teacher);
        System.out.println(controller.getClassesInformation());
    }
    
    public void updateTeacherInformation() {
        String sqlStatement = "update teachers " +
                "set FirstName = ?, LastName = ?, Gender = ?, Address = ?, PhoneNumber = ?, DateOfBirth = ?, Email = ? " +
                "where id = ?";
        database.execute(sqlStatement, teacher.getFirstName(), teacher.getLastName(),
        teacher.getGender(), teacher.getAddress(), teacher.getPhoneNumber(), 
        teacher.getDob(), teacher.getEmail(), teacher.getTeacher_id());
        System.out.println("Teacher information updated successfully");
    }
    
    public String getPassword() {
        String password = null;
        try {
            // admin: type = 1
            // teacher: type = 2
            // student: type = 3
            String sqlStatement = "select * from accounts where type = 2 and target_id = ?";
            ResultSet rs = database.executeQuery(sqlStatement, teacher.getTeacher_id());
            rs.next();
            
            password = rs.getString("Password");
        } catch (SQLException ex) {
            Logger.getLogger(TeacherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return password;
    }
    
    public void updateNewPassword(String newPassword) {
        String sqlStatement = "update accounts " +
                "set Password = ? " +
                "where type = 2 and target_id = ?";
        database.execute(sqlStatement, Encrypt.encryptPassword(newPassword), teacher.getTeacher_id());
        System.out.println("Teacher password updated successfully");
    }
}
