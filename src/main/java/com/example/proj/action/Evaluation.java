package com.example.proj.action;



import com.example.proj.model.Teacher;
import com.example.proj.model.Person;

import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import com.opensymphony.xwork2.ActionSupport;
public class Evaluation extends ActionSupport {

    Login login = new Login();
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    String sql = null;
    ResultSet rs = null;
    private String currentStatus = "error";

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
    Date date = new Date();
    String currentDate = formatter.format(date);

    private int userTeacherID;
    private String userTeacherName;
    private String userTeacherFName;
    private String userTeacherLName;
 
    private String userEval;
    private int userStar;
    private int currentStudentID;
    private int currentTeacherID;



    ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    ArrayList<Person> Pteachers = new ArrayList<Person>();
    ArrayList<String> listOfTeachers = new ArrayList<String>();
    ArrayList<Integer> listOfTeachersID = new ArrayList<Integer>();
    ArrayList<Integer> listOfTeachersAccountID = new ArrayList<Integer>();
    ArrayList<String> listOfTeachersName = new ArrayList<String>();





    public String execute() throws Exception{
        
        studentIDGetter();
        listTeachers();
        return SUCCESS;
    }

    public String evaluateTeacher(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                studentIDGetter();
                listTeachers();
                selectedTeacherIDFinder();
                statement = login.connection.createStatement();
                sql = "INSERT INTO evaluations(teacherID, studentID, evaluation, stars, evalDate) VALUES('"+getUserTeacherID()+"','"+getCurrentStudentID()+"','"+getUserEval()+"','"+getUserStar()+"','"+currentDate+"')";
                statement.executeUpdate(sql);
                System.out.println("Evaluation Checkpoint");
                currentStatus = SUCCESS;
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

    
    public void removeDupli(){ 
        Set<Integer> listWithoutDuplicates2 = new LinkedHashSet<Integer>(listOfTeachersID);
        listOfTeachersID.clear();
        listOfTeachersID.addAll(listWithoutDuplicates2);
    }
    
    public void studentIDGetter(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "select STUDENTS.studentID from accounts RIGHT JOIN STUDENTS ON Accounts.accountID = STUDENTS.accountID where Accounts.accountID =?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setString(1, Login.getSession().get("accountID").toString()); 
                ResultSet rs= preparedStatement.executeQuery();
                while(rs.next()){        
                    setCurrentStudentID(rs.getInt(1));
                    System.out.println("Current S ID GETTER: " + getCurrentStudentID());
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
            currentStatus = ERROR; 
        }
    }

    public void listTeachers(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT teacherID FROM duo WHERE studentID=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, getCurrentStudentID());
                rs= preparedStatement.executeQuery();
                while(rs.next()){  
                    Teacher teacher = new Teacher();
                    teacher.setMyTeacherID(rs.getInt(1)); 
                    teachers.add(teacher);
                    listOfTeachersID.add(teacher.getMyTeacherID());
                }

                removeDupli();

                int listSize = listOfTeachersID.size();
                System.out.println("------");
                System.out.println("\n");
                System.out.println("# of Teachers: " + listOfTeachersID.size());
                System.out.println("\n");
                for(int i = 0; i < listSize; i++) {    
                    sql = "SELECT teachers.accountID FROM duo RIGHT JOIN teachers ON teachers.teacherID = duo.teacherID where duo.teacherID=?";
                    preparedStatement = login.connection.prepareStatement(sql);
                    preparedStatement.setInt(1, listOfTeachersID.get(i)); 
                    rs= preparedStatement.executeQuery();
                    if(rs.next()){
                        Teacher teacher = new Teacher();
                        System.out.println("Teacher's Teacher ID: "+ listOfTeachersID.get(i));
                        teacher.setMyTeacherID(listOfTeachersID.get(i));
                        teacher.setMyAccountID(rs.getInt(1));
                        listOfTeachersAccountID.add(teacher.getMyAccountID());
                        System.out.println("Teacher's Account ID: " +listOfTeachersAccountID.get(i));
                        sql = "SELECT firstName, lastName FROM accounts where accountID=?";
                        preparedStatement = login.connection.prepareStatement(sql);
                        preparedStatement.setInt(1, rs.getInt(1));
                        rs= preparedStatement.executeQuery();
                        if(rs.next()){
                            teacher.setMyFirstName(rs.getString(1));
                            teacher.setMyLastName(rs.getString(2));
                            listOfTeachersName.add(teacher.getMyFirstName() + " " + teacher.getMyLastName());
                            System.out.println("Teacher's Name: " +listOfTeachersName.get(i));
                            System.out.println("\n");
                        } 
                    }
                } 
            }
        }
        catch (Exception e) {
            System.out.println(e);
            currentStatus = ERROR;
        } 
        finally { }
    }

    public void selectedTeacherIDFinder(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {

                String input = getUserTeacherName();
                String[] names = input.split(" ");
                for (String s : names) {
                        setUserTeacherFName(s);
                        break;
                    }
                for (String s : names) {
                    setUserTeacherLName(s);
                }
            }

            sql = "select teachers.teacherID from accounts RIGHT JOIN teachers ON accounts.accountID = teachers.accountID where accounts.firstName=? AND accounts.lastName=?";
            preparedStatement = login.connection.prepareStatement(sql);
            preparedStatement.setString(1, getUserTeacherFName()); 
            preparedStatement.setString(2, getUserTeacherLName()); 
            rs= preparedStatement.executeQuery();
            while(rs.next()){        
                    setUserTeacherID(rs.getInt(1));
                    System.out.println("Current Teacher ID: " + rs.getString(1));
                
            }
        }
        catch (Exception e) {
            System.out.println(e);
            currentStatus = ERROR;
        } 
        finally { }
    }
    public int getUserTeacherID() {
        return userTeacherID;
    }

    public void setUserTeacherID(int userTeacherID) {
        this.userTeacherID = userTeacherID;
    }

    public String getUserEval() {
        return userEval;
    }

    public void setUserEval(String userEval) {
        this.userEval = userEval;
    }

    public int getUserStar() {
        return userStar;
    }

    public void setUserStar(int userStar) {
        this.userStar = userStar;
    }

    public int getCurrentStudentID() {
        return currentStudentID;
    }

    public void setCurrentStudentID(int currentStudentID) {
        this.currentStudentID = currentStudentID;
    }

    public ArrayList<Integer> getListOfTeachersID() {
        return listOfTeachersID;
    }

    public void setListOfTeachersID(ArrayList<Integer> listOfTeachersID) {
        this.listOfTeachersID = listOfTeachersID;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }

    public int getCurrentTeacherID() {
        return currentTeacherID;
    }

    public void setCurrentTeacherID(int currentTeacherID) {
        this.currentTeacherID = currentTeacherID;
    }

    public ArrayList<String> getListOfTeachersName() {
        return listOfTeachersName;
    }

    public void setListOfTeachersName(ArrayList<String> listOfTeachersName) {
        this.listOfTeachersName = listOfTeachersName;
    }

    public String getUserTeacherName() {
        return userTeacherName;
    }

    public void setUserTeacherName(String userTeacherName) {
        this.userTeacherName = userTeacherName;
    }

    public String getUserTeacherFName() {
        return userTeacherFName;
    }

    public void setUserTeacherFName(String userTeacherFName) {
        this.userTeacherFName = userTeacherFName;
    }

    public String getUserTeacherLName() {
        return userTeacherLName;
    }

    public void setUserTeacherLName(String userTeacherLName) {
        this.userTeacherLName = userTeacherLName;
    }






}


