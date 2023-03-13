/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author mcsmuscle
 */
public class Student extends Person {
    private int student_id;
    private int class_id;
    private String studentCode;
    private String className;

    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getClassName() {
        return className;
    }
    
    public Student() {}
    public Student(int student_id, int class_id) {
        this.student_id = student_id;
        this.class_id = class_id;
    }

    public Student(int student_id, int class_id, String studentCode, String firstName, 
            String lastName, String gender, String address, String phoneNumber, 
            String dob, String emai) {
        super(firstName, lastName, gender, address, phoneNumber, dob, emai);
        this.studentCode = studentCode;
        this.student_id = student_id;
        this.class_id = class_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    @Override
    public String toString() {
        return "Student{" + "student_id=" + student_id + ", class_id=" + class_id + ", " + super.toString() + '}';
    }
    
    
}
