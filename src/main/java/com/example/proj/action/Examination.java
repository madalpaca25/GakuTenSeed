package com.example.proj.action;


import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

import com.example.proj.model.Question;
import com.example.proj.model.StudentAnswer;

import com.opensymphony.xwork2.ActionSupport;

public class Examination extends ActionSupport {
    Login login = new Login();
    Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    String sql;
    ResultSet rs;
    private String currentStatus = ERROR;
    private String errorCause;

    private String studentAction;
    private int studScore = 0;
    private String studPercScore;
    private String examStatus;

    private int curExamID;
    private int curScoreID;
    private int curIndex;  
    private String curStudAns;

    DecimalFormat df = new DecimalFormat("#.##");
    ArrayList<Question> questions = new ArrayList<Question>();
    ArrayList<Question> questions2 = new ArrayList<Question>();
    ArrayList<StudentAnswer> studAns = new ArrayList<StudentAnswer>();

    public String execute() throws Exception{
        
        startExam();
        return currentStatus;
    }

    public String startExam(){
        try{
            login.ConnectionChecker();
            if (login.connection != null) {
                myData();
                setStudentAction("studentStartExam");
                sql = "SELECT * FROM questions WHERE examID=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, getCurExamID());
                rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Question question = new Question();
                    StudentAnswer studAn = new StudentAnswer();
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
                    studAn.setStudentQuestionID(rs.getInt(2));
                    studAn.setStudentAns(rs.getString(5));
                    studAns.add(studAn);
                    setCurrentStatus(SUCCESS);
                }
            }
        }  catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("START EXAM2 : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
        return currentStatus;
    }
    
    public String submitExam(){
        try{
            login.ConnectionChecker();
            if (login.connection != null) {
                myData();
                sql = "SELECT * FROM questions WHERE examID=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, getCurExamID());
                rs = preparedStatement.executeQuery();
                setCurIndex(0);
                while(rs.next()){
                    System.out.println("\n");
                    System.out.println("-----");
                    System.out.println("Question (db): "+ rs.getString(3));
                    System.out.println("USER ANSWER (web): " + studAns.get(curIndex).getStudentAns());
                    System.out.println("AnswerKey: " + rs.getString(8));
                    System.out.println("Explanation: " + rs.getString(9));
                    if (studAns.get(curIndex).getStudentAns().equals(rs.getString(8))) {
                        System.out.println("CORRECT!");
                        setStudScore(getStudScore() + 1 );
                        System.out.println("current Score: " + studScore);
                    }
                    else  {
                        System.out.println("WRONG!");
                        System.out.println("current Score: " + studScore);
                    }
                    System.out.println("-----");
                    curIndex+= 1;
                }

            }
        }  catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("START EXAM1 : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
            updateScoreID();
        }
        return currentStatus;
    }
            
    public void updateScoreID(){
        try{
            login.ConnectionChecker();
            if (login.connection != null) {
                scorePercentage(getStudScore());
                Login.getSession().put("examStatus", "Closed");
                sql = "UPDATE scores SET examID=?, duoID=?, score=?, examStatus=? where scoreID=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, Integer.parseInt(Login.getSession().get("examID").toString()));
                preparedStatement.setInt(2, Integer.parseInt(Login.getSession().get("duoID").toString()));
                preparedStatement.setString(3, Login.getSession().get("studPercScore").toString());
                preparedStatement.setString(4, Login.getSession().get("examStatus").toString());
                preparedStatement.setString(5, Login.getSession().get("scoreID").toString());
                preparedStatement.executeUpdate();
                System.out.println("SUPER CHECKPOINT");
                setStudentAction("submitExam");
            }

            sql = "DELETE FROM scores WHERE examID=? AND duoID=? AND examStatus=?";
                System.out.print("EXAM ID CHECK " + Integer.parseInt(Login.getSession().get("examID").toString()));
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, Integer.parseInt(Login.getSession().get("examID").toString()));
                preparedStatement.setInt(2, Integer.parseInt(Login.getSession().get("duoID").toString()));
                preparedStatement.setString(3, "Open");
                preparedStatement.executeUpdate();
                System.out.println("SUPER CHECKPOINT2");
                setCurrentStatus(SUCCESS);
        } catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("CHECK EXAM : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
    }

    public void scorePercentage(double score) {
        System.out.println("%INSIDE");
        questionSizeGetter();
        System.out.println("%SIZES " + questions2.size());
        double secondRes = 100 / (questions2.size() / score);
        System.out.println("%SCORE :" + secondRes);
        System.out.println("%SCORE2 :" + Double.parseDouble(df.format(secondRes)));
        System.out.println("FORMAT" + df.format(secondRes));
        Login.getSession().put("studPercScore", df.format(secondRes));
    }

    public void questionSizeGetter(){
        try{
            myData();
            setStudentAction("studentStartExam");
            sql = "SELECT * FROM questions where examID=?";
            preparedStatement = login.connection.prepareStatement(sql);
            preparedStatement.setInt(1, getCurExamID());
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                Question question2 = new Question();
                question2.setMyQues(rs.getString(3)); 
                questions2.add(question2);
            }
        }  catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("START EXAM3 : FAILED : CAUSE: " + getErrorCause());
        } finally { }
    }
    
    public void myData(){
        setCurExamID(Integer.parseInt(Login.getSession().get("examID").toString()));
        setCurScoreID(Integer.parseInt(Login.getSession().get("scoreID").toString()));
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<StudentAnswer> getStudAns() {
        return studAns;
    }

    public void setStudAns(ArrayList<StudentAnswer> studAns) {
        this.studAns = studAns;
    }

    public int getStudScore() {
        return studScore;
    }

    public void setStudScore(int studScore) {
        this.studScore = studScore;
    }

    public String getCurStudAns() {
        return curStudAns;
    }

    public void setCurStudAns(String curStudAns) {
        this.curStudAns = curStudAns;
    }
    public int getCurIndex() {
        return curIndex;
    }

    public void setCurIndex(int curIndex) {
        this.curIndex = curIndex;
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

    public int getCurExamID() {
        return curExamID;
    }

    public void setCurExamID(int curExamID) {
        this.curExamID = curExamID;
    }

    public int getCurScoreID() {
        return curScoreID;
    }

    public void setCurScoreID(int curScoreID) {
        this.curScoreID = curScoreID;
    }

    public String getStudentAction() {
        return studentAction;
    }

    public void setStudentAction(String studentAction) {
        this.studentAction = studentAction;
    }

    public String getStudPercScore() {
        return studPercScore;
    }

    public void setStudPercScore(String studPercScore) {
        this.studPercScore = studPercScore;
    }

    public String getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }



    
}
