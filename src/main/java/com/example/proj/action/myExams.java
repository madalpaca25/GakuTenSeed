package com.example.proj.action;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

import java.util.LinkedHashSet;
import java.util.Set;

import com.example.proj.model.Exam;
import com.opensymphony.xwork2.ActionSupport;

public class myExams extends ActionSupport {
    Login login = new Login();
    Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    String sql;
    ResultSet rs;
    private String currentStatus = ERROR;
    private String errorCause;
    private String adminAction;

    private int examID;
    private int myAccountBatch;
    private String myTerm;
    private String myDesc;
    private String myLang;

    private int sortExamID;
    private String sortLang;

    ArrayList<Exam> exams = new ArrayList<Exam>();
    public ArrayList<Integer> listOfExams = new ArrayList<Integer>();
    public ArrayList<Integer> listOfBatches = new ArrayList<Integer>();
    public ArrayList<String> listOfLanguages = new ArrayList<String>();
    public ArrayList<String> listForSortLanguages = new ArrayList<String>();

    public String execute() throws Exception{
        listAllExams();
        myData();
        return SUCCESS;
    }

    public String adminCreateExam(){
        setAdminAction("adminCreateExam");
        listAllExams();
        myData();
        return SUCCESS;
    }
    public String createExam() {
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "INSERT INTO exams(accountBatch, term, language, description) " + "VALUES(?,?,?,?)";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, getMyAccountBatch());
                preparedStatement.setString(2, getMyTerm());
                preparedStatement.setString(3, getMyLang());
                preparedStatement.setString(4, getMyDesc());
                preparedStatement.executeUpdate();
                setCurrentStatus(SUCCESS);
                addActionMessage(" CREATE EXAM :" + getCurrentStatus().toUpperCase());
            }
        } catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("CREATE EXAM : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
            listAllExams();
            myData();
        } return currentStatus;
    }

    public String adminUpdateExam(){
        setAdminAction("adminUpdateExam");
        listAllExams();
        myData();
        return SUCCESS;
    }

    public String updateExam() {
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                    sql = "UPDATE exams SET accountBatch=?, term=?, language=?, description=?  WHERE examID=?";
                    preparedStatement = login.connection.prepareStatement(sql);
                    preparedStatement.setInt(1, getMyAccountBatch());
                    preparedStatement.setString(2, getMyTerm());
                    preparedStatement.setString(3, getMyLang());
                    preparedStatement.setString(4, getMyDesc());
                    preparedStatement.setInt(5, getExamID());
                    preparedStatement.executeUpdate();
                    setCurrentStatus(SUCCESS);
                    addActionMessage("UPDATE EXAM : " + getCurrentStatus());
                }
            }
        catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("UPDATE EXAM : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
            listAllExams();
            myData();
        }
        return currentStatus;
    }

    public String adminDeleteExam(){
        setAdminAction("adminDeleteExam");
        listAllExams();
        myData();
        return SUCCESS;
    }

    public String deleteExam() {
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "DELETE FROM exams where examID=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, getExamID());
                preparedStatement.execute();
                setCurrentStatus(SUCCESS);
                addActionMessage("DELETE EXAM : " + getCurrentStatus());
            }
        }
        catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("DELETE EXAM : FAILED : CAUSE: " + getErrorCause());
        } 
        finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
            listAllExams();
            myData();
        }
        return currentStatus;
    }

    public String adminCancelExam() {
        setAdminAction("adminCancel");
        listAllExams();
        myData();
        return SUCCESS;
    }

    public void myData(){
        displayExamsID();
        displayBatches();
        displayLanguages();
        removeDupli();
    }

    public void displayExamsID(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT * FROM exams";
                preparedStatement = login.connection.prepareStatement(sql);
                rs= preparedStatement.executeQuery();
                while(rs.next()){        
                    listOfExams.add(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("DISPLAY EXAM : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
    }

    public void listAllExams(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT * FROM exams";
                statement = login.connection.createStatement();
                rs = statement.executeQuery(sql);
                while(rs.next()){        
                    Exam exam = new Exam();
                    exam.setExamID(rs.getInt(1));
                    exam.setMyAccountBatch(rs.getInt(2));  
                    exam.setMyTerm(rs.getString(3)); 
                    exam.setMyLang(rs.getString(4)); 
                    exam.setMyDesc(rs.getString(5));
                    exams.add(exam);
                }
            }
        } catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("LIST ALL EXAM : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
    }

    public String adminSortExams(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                setAdminAction("adminSortExams");
                sql = "SELECT * FROM exams where language=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setString(1, getSortLang());
                rs = preparedStatement.executeQuery();
                while(rs.next()){        
                    Exam exam = new Exam();
                    exam.setExamID(rs.getInt(1));
                    exam.setMyAccountBatch(rs.getInt(2));  
                    exam.setMyTerm(rs.getString(3)); 
                    exam.setMyLang(rs.getString(4)); 
                    exam.setMyDesc(rs.getString(5));
                    exams.add(exam);
                    setCurrentStatus(SUCCESS);  
                }
                if (getCurrentStatus().equals(SUCCESS)) { addActionMessage("SORT EXAM : " + getCurrentStatus().toUpperCase());}
                else { addActionMessage("SORT EXAM : NO" + getCurrentStatus().toUpperCase());}
            }
        } catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("SORT EXAM : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
            myData();
        } return currentStatus;
    }

    public void displayBatches(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT accountBatch FROM accounts";
                statement = login.connection.createStatement();
                rs = statement.executeQuery(sql);
                while(rs.next()) {              
                        listOfBatches.add(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("DISPLAY BATCHES : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
    }

    public void displayLanguages(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT language FROM duo";
                statement = login.connection.createStatement();
                rs= statement.executeQuery(sql);
                while(rs.next()) {              
                        listOfLanguages.add(rs.getString(1));;  
                }
                sql = "SELECT language FROM exams";
                statement = login.connection.createStatement();
                rs= statement.executeQuery(sql);
                while(rs.next()) {              
                    listForSortLanguages.add(rs.getString(1));;  
                }
            }
        } catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("DISPLAY LANGUAGES : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
    }

    public void removeDupli(){ 
        Set<Integer> listWithoutDuplicates1 = new LinkedHashSet<Integer>(listOfExams);
        listOfExams.clear();
        listOfExams.addAll(listWithoutDuplicates1);
        Set<Integer> listWithoutDuplicates2 = new LinkedHashSet<Integer>(listOfBatches);
        listOfBatches.clear();
        listOfBatches.addAll(listWithoutDuplicates2);
        Set<String> listWithoutDuplicates3 = new LinkedHashSet<String>(listOfLanguages);
        listOfLanguages.clear();
        listOfLanguages.addAll(listWithoutDuplicates3);
        Set<String> listWithoutDuplicates4 = new LinkedHashSet<String>(listForSortLanguages);
        listForSortLanguages.clear();
        listForSortLanguages.addAll(listWithoutDuplicates4);
    }

    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public String getMyTerm() {
        return myTerm;
    }

    public void setMyTerm(String myTerm) {
        this.myTerm = myTerm;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
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

    public int getMyAccountBatch() {
        return myAccountBatch;
    }

    public void setMyAccountBatch(int myAccountBatch) {
        this.myAccountBatch = myAccountBatch;
    }
    
    public int getSortExamID() {
        return sortExamID;
    }

    public void setSortExamID(int sortExamID) {
        this.sortExamID = sortExamID;
    }

    public String getSortLang() {
        return sortLang;
    }

    public void setSortLang(String sortLang) {
        this.sortLang = sortLang;
    }

    public String getAdminAction() {
        return adminAction;
    }

    public void setAdminAction(String adminAction) {
        this.adminAction = adminAction;
    }
    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(String errorCause) {
        this.errorCause = errorCause;
    }
} 