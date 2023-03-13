/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.util.List;
import model.Database;
import model.Teacher;
import model.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Encrypt;

/**
 *
 * @author mcsmuscle
 */
public class AdminController {
    private final Database database = new Database();
    
    
    public List<Object[]> getStudentInformationForTable() {
        List<Object[]> classesDataStore = new ArrayList<>();
        try {
            
            String sqlStatement = "select * from classes " +
                    "join students on classes.id = students.classes_id";
            
            ResultSet rs = database.executeQuery(sqlStatement);
            
            while (rs.next()) {
                Object[] objects = {null, rs.getString("FirstName"), 
                    rs.getString("LastName"), rs.getString("StudentCode"),
                    rs.getString("ClassName"), rs.getInt("students.id")};
                
                classesDataStore.add(objects);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classesDataStore;
    }
    
    public Student getStudentInformation(int student_id) {
        Student student = null;
        try {
            String sqlStatement = "select * from classes " +
                    "join students on classes.id = students.classes_id " +
                    "where students.id = ?";
            ResultSet rs = database.executeQuery(sqlStatement, student_id);
            rs.next();
            student = new Student(rs.getInt("students.id"), rs.getInt("classes_id"),
                    rs.getString("StudentCode"),rs.getString("FirstName"),
                    rs.getString("LastName"),rs.getString("Gender"),
                    rs.getString("Address"),rs.getString("PhoneNumber"),
                    rs.getString("DateOfBirth"),rs.getString("Email"));
            student.setClassName(rs.getString("ClassName"));
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return student;
    }
    
    public void updateStudentInformation(Student student) {
        String sqlStatement = "update students " +
                "set FirstName = ?, LastName = ?, Gender = ?, Address = ?, PhoneNumber = ?, DateOfBirth = ?, Email = ?, StudentCode = ?, classes_id = ? " +
                "where id = ?";
        database.execute(sqlStatement, student.getFirstName(), student.getLastName(),
        student.getGender(), student.getAddress(), student.getPhoneNumber(), 
        student.getDob(), student.getEmail(), student.getStudentCode(),
        student.getClass_id(), student.getStudent_id());
        
        System.out.println("Student information updated successfully");
    }
    
    public void updateNewPasswordForStudent(String newPassword, int student_id) {
        // admin: type = 1
        // teacher: type = 2
        // student: type = 3
        String sqlStatement = "update accounts " +
                "set Password = ? " +
                "where type = 3 and target_id = ?";
        database.execute(sqlStatement, Encrypt.encryptPassword(newPassword), student_id);
        
        System.out.println("Student password updated successfully");
    }
    
    public void addNewStudent(Student student) {
        
        try {
            String sqlStatement;
            ResultSet rs;
            
            // add to students table
            sqlStatement = "INSERT INTO students (FirstName, LastName, Gender, StudentCode, Address, PhoneNumber, DateOfBirth, Email, classes_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            database.execute(sqlStatement, student.getFirstName(), student.getLastName(),
                    student.getGender(), student.getStudentCode(), student.getAddress(),
                    student.getPhoneNumber(), student.getDob(), student.getEmail(),
                    student.getClass_id());
            
            // get the last student_id
            sqlStatement = "SELECT * FROM students ORDER BY ID DESC LIMIT 1";
            rs = database.executeQuery(sqlStatement);
            rs.next();
            int student_id = rs.getInt("id");
            
            // add to accounts table
            sqlStatement = "INSERT INTO accounts (UserName, Password, Type, Target_id) " + 
                    "VALUES(?, ?, ?, ?)";
            database.execute(sqlStatement, student.getStudentCode(), 
                    Encrypt.encryptPassword(student.getStudentCode()), 
                    3, student_id);
            
            // add to subjects table
            sqlStatement = "INSERT INTO subjects (students_id) VALUES (?)";
            database.execute(sqlStatement, student_id);
            
            // add to math_score table
            sqlStatement = "INSERT INTO math_score (subjects_id) VALUES (?)";
            database.execute(sqlStatement, student_id);
            
            // add to math_score table
            sqlStatement = "INSERT INTO physics_score (subjects_id) VALUES (?)";
            database.execute(sqlStatement, student_id);
            
            // add to math_score table
            sqlStatement = "INSERT INTO chemistry_score (subjects_id) VALUES (?)";
            database.execute(sqlStatement, student_id);
            
            // add to math_score table
            sqlStatement = "INSERT INTO english_score (subjects_id) VALUES (?)";
            database.execute(sqlStatement, student_id);
            
            
            System.out.println("Student added successfullyyyy");
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Object[]> getTeacherInformationForTable() {
        List<Object[]> teacherInformationForTable = new ArrayList<Object[]>();
        try {
            String sqlStatement = "select * from teachers";
            
            ResultSet rs = database.executeQuery(sqlStatement);
            
            while (rs.next()) {
                Object[] objects = {null, rs.getString("FirstName"), rs.getString("LastName"), 
                    rs.getString("Subject"), rs.getInt("id")};
                
                teacherInformationForTable.add(objects);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teacherInformationForTable;
    }
    
    public Teacher getTeacherInformation(int teacher_id) {
        Teacher teacher = null;
        try {
            
            String sqlStatement = "select * from teachers where id = ?";
            ResultSet rs = database.executeQuery(sqlStatement, teacher_id);
            rs.next();
            teacher = new Teacher(rs.getInt("teachers.id"), null,
                    rs.getString("Subject"), rs.getString("FirstName"),
                    rs.getString("LastName"), rs.getString("Gender"),
                    rs.getString("Address"), rs.getString("PhoneNumber"),
                    rs.getString("DateOfBirth"), rs.getString("Email"));
            System.out.println("Get teacher information successfully");
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teacher;
    }
    
    public void updateTeacherInformation(Teacher teacher) {
        String sqlStatement = "update teachers " +
                "set FirstName = ?, LastName = ?, Gender = ?, Address = ?, PhoneNumber = ?, DateOfBirth = ?, Email = ?, Subject = ? " +
                "where id = ?";
        database.execute(sqlStatement, teacher.getFirstName(), teacher.getLastName(),
                teacher.getGender(), teacher.getAddress(), teacher.getPhoneNumber(),
                teacher.getDob(), teacher.getEmail(), teacher.getSubject(), teacher.getTeacher_id());
        System.out.println("Teacher information updated successfully");
    }
    
    public void updateNewPasswordForTeacher(String newPassword, int teacher_id) {
        String sqlStatement = "update accounts " +
                "set Password = ? " +
                "where type = 2 and target_id = ?";
        database.execute(sqlStatement, newPassword, teacher_id);
        System.out.println("Teacher password updated successfully");
    }
    
    public void addNewTeacher(Teacher teacher) {
        try {
            String sqlStatement;
            ResultSet rs;
            
            // add new teacher to the database
            sqlStatement = "INSERT INTO teachers (FirstName, LastName, Gender, Address, PhoneNumber, DateOfBirth, Email, Subject) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            database.execute(sqlStatement, teacher.getFirstName(), teacher.getLastName(),
                    teacher.getGender(), teacher.getAddress(), teacher.getPhoneNumber(),
                    teacher.getDob(), teacher.getEmail(), teacher.getSubject());
            
            // get last record
            sqlStatement = "SELECT * FROM teachers ORDER BY ID DESC LIMIT 1";
            rs = database.executeQuery(sqlStatement);
            rs.next();
            int teacher_id = rs.getInt("id");
            
            // add to accounts table
            sqlStatement = "INSERT INTO accounts (UserName, Password, Type, Target_id) " +
                    "VALUES(?, ?, ?, ?)";
            database.execute(sqlStatement, teacher.getEmail(), 
                    Encrypt.encryptPassword(teacher.getPhoneNumber()),
                    2, teacher_id);
            
            System.out.println("Teacher added successfully");
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Object[]> getClassInformationForTable() {
        List<Object[]> classInformationForTable = new ArrayList<>();
        
        String sqlStatement = "select * from classes";
        
        ResultSet rs = database.executeQuery(sqlStatement);
        
        return classInformationForTable;
    }
    
    private List<Integer> teacher_id = new ArrayList<>();
    public String[] getTeacher(String subject) {
        teacher_id = new ArrayList<>();
        List<String> teacherNameList = new ArrayList<>();
        
        try {
            
            String sqlStatement = "select * from teachers where Subject = ?";
            
            ResultSet rs = database.executeQuery(sqlStatement, subject);
            
            while (rs.next()) {
                String fullName = rs.getString("FirstName") + " " + rs.getString("LastName");
                teacherNameList.add(fullName);
                teacher_id.add(rs.getInt("id"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return teacherNameList.toArray(new String[teacherNameList.size()]);
    }
    
    public List<Integer> getTeacher_id() {
        return teacher_id;
    }
    
    public void addToTimetable(int teacher_id, int clases_id, int day, int lesson) {
        
        String sqlStatement;
        ResultSet rs;
        
        if (teacher_id == -1) {
            try {
                // Check if exist
                sqlStatement = "SELECT * FROM timetable WHERE classes_id = ? AND day = ? AND lesson = ?";
                rs = database.executeQuery(sqlStatement, clases_id, day, lesson);
                
                // if not exist just return to ignore 
                if (!rs.next()) return;
                sqlStatement = "DELETE FROM timetable WHERE classes_id = ? AND day = ? AND lesson = ? ";
                database.execute(sqlStatement, clases_id, day, lesson);
                
                System.out.println("Record has been deleted");
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        sqlStatement = "INSERT INTO timetable (teachers_id, classes_id, day, lesson) VALUES (?, ?, ?, ?)";
        
        database.execute(sqlStatement, teacher_id, clases_id, day, lesson);
        
        System.out.println("Add subject to the timetable success");
    }
    
    public Object[][] getTimeTable(int classes_id) {
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
            ResultSet rs = database.executeQuery(sqlStatement, classes_id);
            
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
}
