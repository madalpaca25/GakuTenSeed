package com.example.proj.action;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

import java.util.LinkedHashSet;
import java.util.Set;

import com.example.proj.model.Question;
import com.opensymphony.xwork2.ActionSupport;

public class myQuestions extends ActionSupport {
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
    private int myQuesID;
    private String myQues;
    private String myChoiceA;
    private String myChoiceB;
    private String myChoiceC;
    private String myChoiceD;
    private String myAnsKey;
    private String myExp;

    private String sortQues;

    ArrayList<Question> questions = new ArrayList<Question>();
    public ArrayList<Integer> listOfExams = new ArrayList<Integer>();
    public ArrayList<String> listOfQuestions = new ArrayList<String>();

    public String execute() throws Exception{
        listAllQuestions();
        myData();
        return SUCCESS;
    }

    public void myData(){
        displayExamsID();
        removeDupli();
    }

    public void displayExamsID(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT * FROM exams";
                statement = login.connection.createStatement();
                rs= statement.executeQuery(sql);
                while(rs.next()){        
                    listOfExams.add(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            setErrorCause(e.toString());
            addActionError("FAILED : LIST : EXAM ID : CAUSE : " + getErrorCause());
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
    }

    public void removeDupli(){ 
        Set<Integer> listWithoutDuplicates1 = new LinkedHashSet<Integer>(listOfExams);
        listOfExams.clear();
        listOfExams.addAll(listWithoutDuplicates1);
        Set<String> listWithoutDuplicates2 = new LinkedHashSet<String>(listOfQuestions);
        listOfQuestions.clear();
        listOfQuestions.addAll(listWithoutDuplicates2);
    }

    public String adminCreateQuestion(){
        setAdminAction("adminCreateQuestion");
        listAllQuestions();
        myData();
        return SUCCESS;
    }

    public String createQuestion() {
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "INSERT INTO questions(examID, question, choiceA, choiceB, choiceC, choiceD, answerKey, explanation)" + " VALUES(?,?,?,?,?,?,?,?)";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, getExamID());
                preparedStatement.setString(2, getMyQues());
                preparedStatement.setString(3, getMyChoiceA());
                preparedStatement.setString(4, getMyChoiceB());
                preparedStatement.setString(5, getMyChoiceC());
                preparedStatement.setString(6, getMyChoiceD());
                preparedStatement.setString(7, getMyAnsKey());
                preparedStatement.setString(8, getMyExp());
                preparedStatement.executeUpdate();
                setCurrentStatus(SUCCESS);
                addActionMessage(" CREATE QUESTION : " + getCurrentStatus().toUpperCase());
            }
        } catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("CREATE EXAM : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
            listAllQuestions();
            myData();
        }
        return currentStatus;
    }

    public String adminUpdateQuestion(){
        setAdminAction("adminUpdateQuestion");
        listAllQuestions();
        myData();
        return SUCCESS;
    }

    public String updateQuestion() {
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "UPDATE questions SET examID=?, question=?, choiceA=?, choiceB=?, choiceC=?, choiceD=?, answerKey=?, explanation=? where questionID=? ";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, getExamID());
                preparedStatement.setString(2, getMyQues());
                preparedStatement.setString(3, getMyChoiceA());
                preparedStatement.setString(4, getMyChoiceB());
                preparedStatement.setString(5, getMyChoiceC());
                preparedStatement.setString(6, getMyChoiceD());
                preparedStatement.setString(7, getMyAnsKey());
                preparedStatement.setString(8, getMyExp());
                preparedStatement.setInt(9, getMyQuesID());
                preparedStatement.executeUpdate();
                setCurrentStatus(SUCCESS);
                addActionMessage("UPDATE QUESTION : " + getCurrentStatus());
                }
            }
        catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("UPDATE QUESTION : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
            listAllQuestions();
            myData();
        }
        return currentStatus;
    }

    public String adminDeleteQuestion(){
        setAdminAction("adminDeleteQuestion");
        listAllQuestions();
        myData();
        return SUCCESS;
    }

    public String deleteQuestion() {
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "DELETE FROM questions where questionID=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, getMyQuesID());
                preparedStatement.execute();
                setCurrentStatus(SUCCESS);
                addActionMessage(" DELETE QUESTION : " + getCurrentStatus().toUpperCase());
            }
        }
        catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("UPDATE QUESTION : FAILED : CAUSE: " + getErrorCause());
        } 
        finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
            listAllQuestions();
            myData();
        }
        return currentStatus;
    }
    public String adminCancelQuestion() {
        setAdminAction("adminCancelQuestion");
        listAllQuestions();
        myData();
        return SUCCESS;
    }

    public void listAllQuestions(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT * FROM questions";
                statement = login.connection.createStatement();
                rs = statement.executeQuery(sql);
                while(rs.next()){        
                    Question question = new Question();
                    question.setExamID(rs.getInt(1)); 
                    question.setMyQuesID(rs.getInt(2));
                    question.setMyQues(rs.getString(3));
                    question.setMyChoiceA(rs.getString(4));
                    question.setMyChoiceB(rs.getString(5));
                    question.setMyChoiceC(rs.getString(6));
                    question.setMyChoiceD(rs.getString(7));
                    question.setMyAnsKey(rs.getString(8));
                    question.setMyExp(rs.getString(9));
                    questions.add(question);
                    listOfQuestions.add(question.getMyQues());
                }
            }
        } 
        catch (Exception e) {
            setErrorCause(e.toString());
            addActionError("FAILED : LIST : ALL QUESTIONS : CAUSE :" + getErrorCause());
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }  
    }   

    public String sortQuestions(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                setAdminAction("sortQuestions");
                sql = "SELECT * FROM questions where question=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setString(1, getSortQues());
                rs = preparedStatement.executeQuery();
                while(rs.next()){        
                    Question question = new Question();
                    question.setExamID(rs.getInt(1));
                    question.setMyQuesID(rs.getInt(2));
                    question.setMyQues(rs.getString(3));
                    question.setMyChoiceA(rs.getString(4));
                    question.setMyChoiceB(rs.getString(5));
                    question.setMyChoiceC(rs.getString(6));
                    question.setMyChoiceD(rs.getString(7));
                    question.setMyAnsKey(rs.getString(8));
                    question.setMyExp(rs.getString(9));
                    questions.add(question);
                }
                if ( getCurrentStatus().equals(SUCCESS)) { addActionMessage("SORT EXAM : " + getCurrentStatus().toUpperCase());}
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

    public ArrayList<Question> getQuestions() {
        return questions;
    }
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public int getExamID() {
        return examID;
    }
    public void setExamID(int examID) {
        this.examID = examID;
    }
    public int getMyQuesID() {
        return myQuesID;
    }
    public void setMyQuesID(int myQuesID) {
        this.myQuesID = myQuesID;
    }
    public String getMyQues() {
        return myQues;
    }
    public void setMyQues(String myQues) {
        this.myQues = myQues;
    }
    public String getMyChoiceA() {
        return myChoiceA;
    }
    public void setMyChoiceA(String myChoiceA) {
        this.myChoiceA = myChoiceA;
    }
    public String getMyChoiceB() {
        return myChoiceB;
    }
    public void setMyChoiceB(String myChoiceB) {
        this.myChoiceB = myChoiceB;
    }
    public String getMyChoiceC() {
        return myChoiceC;
    }
    public void setMyChoiceC(String myChoiceC) {
        this.myChoiceC = myChoiceC;
    }
    public String getMyChoiceD() {
        return myChoiceD;
    }
    public void setMyChoiceD(String myChoiceD) {
        this.myChoiceD = myChoiceD;
    }

    public String getMyAnsKey() {
        return myAnsKey;
    }

    public void setMyAnsKey(String myAnsKey) {
        this.myAnsKey = myAnsKey;
    }

    public String getMyExp() {
        return myExp;
    }
    public void setMyExp(String myExp) {
        this.myExp = myExp;
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

    public String getAdminAction() {
        return adminAction;
    }

    public void setAdminAction(String adminAction) {
        this.adminAction = adminAction;
    }
    public String getSortQues() {
        return sortQues;
    }

    public void setSortQues(String sortQues) {
        this.sortQues = sortQues;
    }
}