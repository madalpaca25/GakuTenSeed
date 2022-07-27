package com.example.proj.action;

import com.opensymphony.xwork2.ActionSupport;

import com.example.proj.model.Student;

import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class Performance extends ActionSupport {

    Login login = new Login();
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    String sql = null;
    ResultSet rs = null;
    private String currentStatus = "error";

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
    Date date = new Date();
    String currentDate = formatter.format(date);

    private int userStudentID;
    private String userRemarks;

    private int userStar;
    private int currentTeacherID;

    ArrayList<Integer> listOfStudentsID = new ArrayList<Integer>();


    public String execute() throws Exception{
        
        teacherIDGetter();
        listStudents();
        return SUCCESS;
    }

    public String performanceRemarks(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                teacherIDGetter();
                listStudents();
                statement = login.connection.createStatement();
                sql = "INSERT INTO performances(teacherID, studentID, performance, stars, perfDate) VALUES('"+getCurrentTeacherID()+"','"+getUserStudentID()+"','"+getUserRemarks()+"','"+getUserStar()+"','"+currentDate+"')";
                statement.executeUpdate(sql);
                currentStatus = SUCCESS;
                System.out.println("Performance Checkpoint: " + currentStatus);
            }
         } catch (Exception e) {
            System.out.println(e);
            currentStatus = ERROR;  
         } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
         }

         return currentStatus;  
    }

    public void teacherIDGetter(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT teachers.teacherID FROM accounts RIGHT JOIN teachers ON accounts.accountID = teachers.accountID where accounts.accountID =?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setString(1, Login.getSession().get("accountID").toString()); 
                rs = preparedStatement.executeQuery();
                while(rs.next()){        
                    setCurrentTeacherID(rs.getInt(1));
                    System.out.println("Current Teacher ID: " + rs.getInt(1));
                    currentStatus = SUCCESS;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
            currentStatus = ERROR; 
        }
    }

    public void listStudents() {
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT studentID FROM duo WHERE teacherID=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, getCurrentTeacherID());
                rs = preparedStatement.executeQuery();
                while(rs.next()){  
                    Student student = new Student();
                    student.setMyStudentID(rs.getInt(1)); 
                    listOfStudentsID.add(student.getMyStudentID());
                }
            } 
            removeDupli();
        } 
        catch (Exception e) {
            System.out.println(e);
            currentStatus = ERROR; 
        } 
        finally { }
    }

    public void removeDupli(){ 
        Set<Integer> listWithoutDuplicates2 = new LinkedHashSet<Integer>(listOfStudentsID);
        listOfStudentsID.clear();
        listOfStudentsID.addAll(listWithoutDuplicates2);
    }

    public int getUserStar() {
        return userStar;
    }

    public void setUserStar(int userStar) {
        this.userStar = userStar;
    }


    public int getUserStudentID() {
        return userStudentID;
    }

    public void setUserStudentID(int userStudentID) {
        this.userStudentID = userStudentID;
    }

    public int getCurrentTeacherID() {
        return currentTeacherID;
    }

    public void setCurrentTeacherID(int currentTeacherID) {
        this.currentTeacherID = currentTeacherID;
    }


    public String getUserRemarks() {
        return userRemarks;
    }

    public void setUserRemarks(String userRemarks) {
        this.userRemarks = userRemarks;
    }

    
    public ArrayList<Integer> getListOfStudentsID() {
        return listOfStudentsID;
    }

    public void setListOfStudentsID(ArrayList<Integer> listOfStudentsID) {
        this.listOfStudentsID = listOfStudentsID;
    }

}


