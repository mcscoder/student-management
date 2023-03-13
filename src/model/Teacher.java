/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

import java.util.List;



/**
 *
 * @author mcsmuscle
 */
public class Teacher extends Person {
    private int teacher_id;
    private List<Integer> _classes_id;
    private String subject;

    public Teacher() {}
    public Teacher(int teacher_id, List<Integer> _classes_id, String subject) {
        this.teacher_id = teacher_id;
        this._classes_id = _classes_id;
        this.subject = subject;
    }

    public Teacher(int teacher_id, List<Integer> _classes_id, String subject, 
            String firstName, String lastName, String gender, String address, 
            String phoneNumber, String dob, String email) {
        super(firstName, lastName, gender, address, phoneNumber, dob, email);
        this.teacher_id = teacher_id;
        this._classes_id = _classes_id;
        this.subject = subject;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public List<Integer> getClasses_id() {
        return _classes_id;
    }

    public void setClasses_id(List<Integer> class_id) {
        this._classes_id = class_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    
}
