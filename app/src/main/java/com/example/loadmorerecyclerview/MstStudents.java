package com.example.loadmorerecyclerview;

/**
 * Created by root on 5/10/17.
 */

public class MstStudents {
    int id;
    String studname;
    int rollno;

    public MstStudents() {
    }

    public MstStudents(String studname, int rollno) {
        this.studname = studname;
        this.rollno = rollno;
    }

    public MstStudents(int id, String studname, int rollno) {
        this.id = id;
        this.studname = studname;
        this.rollno = rollno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudname() {
        return studname;
    }

    public void setStudname(String studname) {
        this.studname = studname;
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }
}

