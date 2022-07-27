package com.example.proj.model;

public class Exam {

    private int examID;
    private int myAccountBatch;
    private String myTerm;
    private String myDesc;
    private String myLang;

    public Exam() {}

    public int getExamID() {
        return examID;
    }
    public void setExamID(int examID) {
        this.examID = examID;
    }

    public int getMyAccountBatch() {
        return myAccountBatch;
    }

    public void setMyAccountBatch(int myAccountBatch) {
        this.myAccountBatch = myAccountBatch;
    }

    public String getMyTerm() {
        return myTerm;
    }

    public void setMyTerm(String myTerm) {
        this.myTerm = myTerm;
    }

    public String getMyDesc() {
        return myDesc;
    }

    public void setMyDesc(String myDesc) {
        this.myDesc = myDesc;
    }

    public String getMyLang() {
        return myLang;
    }

    public void setMyLang(String myLang) {
        this.myLang = myLang;
    }

}
