package com.example.proj.model;


public class Person {

    private String userName;
    private String passWord;
    private String firstName;
    private String lastName;
    private String EMail;
    private int accountBatch;
    private String accountType;

    public Person() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getAccountBatch() {
        return accountBatch;
    }

    public void setAccountBatch(int accountBatch) {
        this.accountBatch = accountBatch;
    }

    public String toString() {
        return "Username: " + getUserName() + " Password: "  + getPassWord() +" First Name: " + getFirstName() + " Last Name: " + getLastName() + 
        " EMAIL: " + getEMail() + " Account Type: " + getAccountType(); 
    }
}
