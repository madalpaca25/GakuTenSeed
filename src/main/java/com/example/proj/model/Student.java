package com.example.proj.model;

public class Student {
    
    private int myStudentID;
    private int myAccountID;
    private String myFirstName;
    private String myLastName;

    public Student() {}

    public int getMyStudentID() {
        return myStudentID;
    }
    public void setMyStudentID(int myStudentID) {
        this.myStudentID = myStudentID;
    }
    public int getMyAccountID() {
        return myAccountID;
    }
    public void setMyAccountID(int myAccountID) {
        this.myAccountID = myAccountID;
    }

    public String getMyFirstName() {
        return myFirstName;
    }

    public void setMyFirstName(String myFirstName) {
        this.myFirstName = myFirstName;
    }

    public String getMyLastName() {
        return myLastName;
    }

    public void setMyLastName(String myLastName) {
        this.myLastName = myLastName;
    }

}
