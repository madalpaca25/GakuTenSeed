package com.example.proj.action;

import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import com.example.proj.model.Exam;
import com.example.proj.model.Question;

import java.util.LinkedHashSet;
import java.util.Set;

import java.text.SimpleDateFormat;  
import java.util.Date;

public class StudentExam extends ActionSupport {
    Login login = new Login();
    Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    String sql;
    ResultSet rs;
    private String currentStatus = ERROR;
    private String errorCause;
    private String studentAction;
    private int examID;
    private int myAccountBatch;
    private String myTerm;
    private String myDesc;
    private String myLang;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
    Date date = new Date();
    String currentDate = formatter.format(date);

    private int currentStudentID;
    private int currentTeacherID;
    private int duoID;

    private boolean examStatusClosed = false;

    private String sortLang;

    ArrayList<Exam> exams = new ArrayList<Exam>();
    ArrayList<Question> questions = new ArrayList<Question>();
    public ArrayList<Integer> listOfAvailableExams = new ArrayList<Integer>();
    public ArrayList<String> listForSortLanguages = new ArrayList<String>();

    public String execute() throws Exception{
        listAllExams();
        myData();
        return SUCCESS;
    }

    public String studentTakeExam(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                statusExamGetter();
                if (examStatusClosed) {
                    System.out.println("Already took the exam");
                    setCurrentStatus("Closed");         
                }
                else{
                    sql = "INSERT INTO scores(examID, duoID, examDate)" + " VALUES(?,?,?)";
                    preparedStatement = login.connection.prepareStatement(sql);
                    preparedStatement.setInt(1, getExamID());
                    preparedStatement.setInt(2, getDuoID());
                    preparedStatement.setString(3, currentDate);
                    preparedStatement.executeUpdate();
                    
                    System.out.println("HERE 1");
                    sql = "SELECT scoreID  FROM scores WHERE examID=? AND duoID=? AND examDate=?";
                    preparedStatement = login.connection.prepareStatement(sql);
                    preparedStatement.setInt(1, getExamID()); 
                    preparedStatement.setInt(2, getDuoID());
                    preparedStatement.setString(3, currentDate);
                    rs = preparedStatement.executeQuery();
                    while(rs.next()){
                        System.out.println("HERE 2");
                        Login.getSession().put("scoreID", rs.getInt(1));
                        setCurrentStatus(SUCCESS);
                    }
                    System.out.println("LAST Score ID: " + Login.getSession().get("scoreID"));
                }

            }
        } catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("TAKE EXAM : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
            myData();
        }
        return currentStatus;
    }    

    public boolean statusExamGetter(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                duoIDGetter();
                sql = "Select examStatus FROM scores WHERE examID=? AND duoID=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, getExamID());
                preparedStatement.setInt(2, getDuoID());
                rs = preparedStatement.executeQuery();
                while(rs.next()){
                    if ( rs.getString(1).equals("Closed")){
                        examStatusClosed = true;
                    }
                }          
            }
        } catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("EXAM STATUS CLOSED : FAILED : CAUSE: " + getErrorCause());
        } finally {} 
        return examStatusClosed;
    }
         
    public void duoIDGetter(){ 
        try {  
            login.ConnectionChecker();
            if (login.connection != null) {
                setCurrentStudentID(Integer.parseInt(Login.getSession().get("accountTypeID").toString()));
                sql = "SELECT Duo.duoID FROM exams RIGHT JOIN DUO ON Exams.language = Duo.language where Duo.studentID=? AND Exams.language=?";
                preparedStatement = login.connection.prepareStatement(sql);
                System.out.println("HERE student ID:" + getCurrentStudentID());
                System.out.println("HERE LANG :" + getMyLang());
                preparedStatement.setInt(1, getCurrentStudentID()); 
                preparedStatement.setString(2, getMyLang()); 
                rs= preparedStatement.executeQuery();
                if (rs.next()){        
                    setDuoID(rs.getInt(1));
                    Login.getSession().put("duoID", rs.getInt(1));
                    Login.getSession().put("examID", getExamID());
                    System.out.println("CURRENT DUO ID : " + getDuoID());

                }
            }
        } catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("DUO ID GETTER : FAILED : CAUSE: " + getErrorCause());
        } finally {}
    }
           
    public String myData(){
        try{
            login.ConnectionChecker();
                if (login.connection != null) {
                    sql = "SELECT language FROM exams where accountBatch=? ";
                    preparedStatement = login.connection.prepareStatement(sql);
                    preparedStatement.setString(1, Login.getSession().get("accountBatch").toString());  
                    rs = preparedStatement.executeQuery();
                    while(rs.next()){        
                        listForSortLanguages.add(rs.getString(1));
                        setCurrentStatus(SUCCESS);
                    }
                }
            }
             
        catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("DISPLAY DATA : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
            removeDupli();
        }
        return currentStatus;
    }

    public void listAllExams(){
        try{
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT * FROM exams where accountBatch=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setString(1, Login.getSession().get("accountBatch").toString());  
                rs = preparedStatement.executeQuery();
                while(rs.next()){        
                    Exam exam = new Exam();
                    exam.setExamID(rs.getInt(1));
                    exam.setMyAccountBatch(rs.getInt(2));  
                    exam.setMyTerm(rs.getString(3)); 
                    exam.setMyLang(rs.getString(4)); 
                    exam.setMyDesc(rs.getString(5)); 
                    exams.add(exam);
                }
                // sql = "SELECT exams.* FROM scores RIGHT JOIN exams ON exams.examID = scores.examID  WHERE exams.accountBatch=? AND scores.duoID=? AND scores.ExamID=? AND scores.ExamStatus=?";
                // preparedStatement = connection.prepareStatement(sql);
                // preparedStatement.setInt(1, Integer.parseInt(Login.getSession().get("accountBatch").toString())); 
                // preparedStatement.setInt(2, Integer.parseInt(Login.getSession().get("duoID").toString())); 
                // preparedStatement.setInt(3, Integer.parseInt(Login.getSession().get("examID").toString())); 
                // preparedStatement.setString(3, "Open"); 
                // rs = preparedStatement.executeQuery();
                // while(rs.next()){   
                //     Exam exam = new Exam();
                //     exam.setExamID(rs.getInt(1));
                //     exam.setMyAccountBatch(rs.getInt(2));  
                //     exam.setMyTerm(rs.getString(3)); 
                //     exam.setMyLang(rs.getString(4)); 
                //     exam.setMyDesc(rs.getString(5)); 
                //     exams.add(exam);
                //  }
            }
        }
        catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("DISPLAY EXAM : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }

    }

    public String studentSortExams(){
        try{
            login.ConnectionChecker();
            if (login.connection != null) {
                setStudentAction("studentSortExams");
                sql = "SELECT * FROM exams where accountBatch=? AND language=? ";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setString(1, Login.getSession().get("accountBatch").toString());  
                preparedStatement.setString(2, getSortLang());  
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
            }
        }
        catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("SORT EXAM : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
            myData();
        }
        return currentStatus;
    }

    public String studentCancelExam() {
        setStudentAction("studentCancelExam");
        listAllExams();
        myData();
        return SUCCESS;
    }

    public void removeDupli(){ 
        Set<String> listWithoutDuplicates1 = new LinkedHashSet<String>(listForSortLanguages);
        listForSortLanguages.clear();
        listForSortLanguages.addAll(listWithoutDuplicates1);
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

    public String getMyDesc() {
        return myDesc;
    }

    public void setMyDesc(String myDesc) {
        this.myDesc = myDesc;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
    }

    public int getMyAccountBatch() {
        return myAccountBatch;
    }

    public void setMyAccountBatch(int myAccountBatch) {
        this.myAccountBatch = myAccountBatch;
    }

    public String getMyLang() {
        return myLang;
    }

    public void setMyLang(String myLang) {
        this.myLang = myLang;
    }

    public int getCurrentStudentID() {
        return currentStudentID;
    }

    public void setCurrentStudentID(int currentStudentID) {
        this.currentStudentID = currentStudentID;
    }

    public int getCurrentTeacherID() {
        return currentTeacherID;
    }

    public void setCurrentTeacherID(int currentTeacherID) {
        this.currentTeacherID = currentTeacherID;
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

    public String getStudentAction() {
        return studentAction;
    }

    public void setStudentAction(String studentAction) {
        this.studentAction = studentAction;
    }

    public String getSortLang() {
        return sortLang;
    }

    public void setSortLang(String sortLang) {
        this.sortLang = sortLang;
    }

    public int getDuoID() {
        return duoID;
    }

    public void setDuoID(int duoID) {
        this.duoID = duoID;
    }



}
