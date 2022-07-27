package com.example.proj.model;

public class Teacher {

    private int myTeacherID;
    private int myAccountID;
    private String myFirstName;
    private String myLastName;

    public Teacher() {}

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

    public int getMyTeacherID() {
        return myTeacherID;
    }

    public void setMyTeacherID(int myTeacherID) {
        this.myTeacherID = myTeacherID;
    }

    public int getMyAccountID() {
        return myAccountID;
    }

    public void setMyAccountID(int myAccountID) {
        this.myAccountID = myAccountID;
    }

    
}
