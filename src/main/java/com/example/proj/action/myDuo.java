package com.example.proj.action;

import com.example.proj.model.Student;
import com.example.proj.model.Teacher;

import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;

import com.opensymphony.xwork2.ActionSupport;

public class myDuo extends ActionSupport {
    private static final long serialVersionUID = 1L;   
    Login login = new Login();
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    String sql = null;
    ResultSet rs = null;

    private String myCatch;
    private String myStatus = "None";
    private String myStatusInfo;
    private String currentStatus = "error";
    private boolean myValidator = true;

    //from user
    private String selectStudentName;
    private String selectStudentFName;
    private String selectStudentLName;
    private String selectTeacherName;
    private String selectTeacherFName;
    private String selectTeacherLName;
    private int selectStudentsID;
    private int selectTeachersID;
    private String selectLang;
    //student
    ArrayList<Student> students = new ArrayList<Student>();
    public ArrayList<String> listOfSfullName = new ArrayList<String>();
    public ArrayList<Integer> listOfStudentsID = new ArrayList<Integer>();
    public ArrayList<Integer> listOfStudentsAccountID = new ArrayList<Integer>();
    //teacher
    ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    public ArrayList<String> listOfTfullName = new ArrayList<String>();
    public ArrayList<Integer> listOfTeachersID = new ArrayList<Integer>();
    public ArrayList<Integer> listOfTeachersAccountID = new ArrayList<Integer>();
    //language
    public ArrayList<String> listOfLanguages = new ArrayList<String>();
    private boolean duplicateLanguage = false;;
    //gen
    private int listSize;
    private boolean hasRecord;


    public String execute() throws Exception{

        myData();
        return SUCCESS;
    }

    public String updateDuo()  {
        try {
            fieldValidator();
            if (myValidator) {
                selectedStudentIDGetter();
                selectedTeacherIDGetter();
                duplicateLanguageChecker();
                if (duplicateLanguage) {  
                    setMyStatus("ERROR");
                    setMyStatusInfo("Language for that student already exists");
                }
                else{
                    login.ConnectionChecker();
                    if (login.connection != null) {
                        statement = login.connection.createStatement();
                        String sql = "INSERT INTO duo (studentID, teacherID, language) VALUES('"+getSelectStudentsID()+"','"+getSelectTeachersID()+"', '"+getSelectLang()+"')";
                        statement.executeUpdate(sql);
                        setMyStatus("SUCCESS");
                        setMyStatusInfo("Successfully added to Duo");
                        currentStatus = SUCCESS;
                    }
                }
            }
            myData();
        }
        catch (Exception e) {
            System.out.println(e);
            currentStatus = ERROR;
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }

        return currentStatus;
    }
    
    public void duplicateLanguageChecker() {
        try { 
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT language FROM duo WHERE studentID=? and language=? ";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, getSelectStudentsID());
                preparedStatement.setString(2, getSelectLang());   
                rs= preparedStatement.executeQuery();
                while(rs.next()){ 
                    duplicateLanguage = true;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
            setMyCatch(e.toString());
            currentStatus = ERROR;
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
    }
        
    public void listStudents() {
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT * FROM students";
                preparedStatement = login.connection.prepareStatement(sql);
                rs= preparedStatement.executeQuery();
                while(rs.next()){  
                    Student student = new Student();
                    student.setMyStudentID(rs.getInt(1)); 
                    student.setMyAccountID(rs.getInt(2)); 
                    listOfStudentsID.add(student.getMyStudentID()); 
                    listOfStudentsAccountID.add(student.getMyAccountID());
                }
                listSize = listOfStudentsID.size();
                for(int i = 0; i < listSize; i++) {    
                    Student student = new Student();
                    sql = "SELECT firstName, lastName FROM accounts where accountID=?";
                    preparedStatement = login.connection.prepareStatement(sql);
                    preparedStatement.setInt(1, listOfStudentsAccountID.get(i));
                    rs= preparedStatement.executeQuery();
                    if(rs.next()){
                        student.setMyFirstName(rs.getString(1));
                        student.setMyLastName(rs.getString(2));
                        listOfSfullName.add(student.getMyFirstName() + " " + student.getMyLastName());

                    }
                }
            } 
        } 
        catch (Exception e) {
            System.out.println(e);
            setMyCatch(e.toString());

            currentStatus = ERROR;
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
    }

    public void listTeachers(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT * FROM teachers";
                preparedStatement = login.connection.prepareStatement(sql);
                rs= preparedStatement.executeQuery();
                while(rs.next()){  
                    Teacher teacher = new Teacher();
                    teacher.setMyTeacherID(rs.getInt(1)); 
                    teacher.setMyAccountID(rs.getInt(2)); 
                    listOfTeachersID.add(teacher.getMyTeacherID());  
                    listOfTeachersAccountID.add(teacher.getMyAccountID()); 
                }
                listSize = listOfTeachersID.size();
                for(int i = 0; i < listSize; i++) {    
                    Teacher teacher = new Teacher();
                    sql = "SELECT firstName, lastName FROM accounts where accountID=?";
                    preparedStatement = login.connection.prepareStatement(sql);
                    preparedStatement.setInt(1, listOfTeachersAccountID.get(i));
                    rs = preparedStatement.executeQuery();
                    if(rs.next()){
                        teacher.setMyFirstName(rs.getString(1));
                        teacher.setMyLastName(rs.getString(2));
                        listOfTfullName.add(teacher.getMyFirstName() + " " + teacher.getMyLastName());
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
            currentStatus = ERROR;
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
    }
    public void listLanguages() {
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT language FROM duo";
                preparedStatement = login.connection.prepareStatement(sql);
                rs = preparedStatement.executeQuery();
                while(rs.next()){  
                    setSelectLang(rs.getString(1)); 
                    listOfLanguages.add(getSelectLang());  
                }
            }
        } 
        catch (Exception e) {
            System.out.println(e);
            currentStatus = ERROR;
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
    }

    public void selectedStudentIDGetter(){
        try {
            login.ConnectionChecker();
                if (login.connection != null) {
                    String input = getSelectStudentName();
                    String[] names = input.split(" ");
                    for (String s : names) {
                        setSelectStudentFName(s);
                        break;
                        }
                    for (String s : names) {
                        setSelectStudentLName(s);
                    }
                }
                sql = "SELECT students.studentID FROM accounts RIGHT JOIN students ON accounts.accountID = students.accountID where accounts.firstName=? AND accounts.lastName=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setString(1, getSelectStudentFName()); 
                preparedStatement.setString(2, getSelectStudentLName()); 
                rs= preparedStatement.executeQuery();
                while(rs.next()){        
                        setSelectStudentsID(rs.getInt(1));

                }
        }
        catch (Exception e) {
            System.out.println(e);
            setMyCatch(e.toString());
            currentStatus = ERROR;
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
    }

    public void selectedTeacherIDGetter(){
        try{
            login.ConnectionChecker();
                if (login.connection != null) {
                    String input = getSelectTeacherName();
                    String[] names = input.split(" ");
                    for (String s : names) {
                        setSelectTeacherFName(s);
                        break;
                        }
                    for (String s : names) {
                        setSelectTeacherLName(s);
                    }
                }
                sql = "SELECT teachers.teacherID FROM accounts RIGHT JOIN teachers ON accounts.accountID = teachers.accountID where accounts.firstName=? AND accounts.lastName=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setString(1, getSelectTeacherFName()); 
                preparedStatement.setString(2, getSelectTeacherLName()); 
                rs = preparedStatement.executeQuery();
                while(rs.next()){        
                        setSelectTeachersID(rs.getInt(1));
                }
            }

        catch (Exception e) {
            System.out.println(e);
            setMyCatch(e.toString());
            currentStatus = ERROR;
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
    }

    public void removeDupli(){ 
        Set<String> listWithoutDuplicates1 = new LinkedHashSet<String>(listOfSfullName);
        listOfSfullName.clear();
        listOfSfullName.addAll(listWithoutDuplicates1);
        Set<String> listWithoutDuplicates2 = new LinkedHashSet<String>(listOfLanguages);
        listOfLanguages.clear();
        listOfLanguages.addAll(listWithoutDuplicates2);
        Set<String> listWithoutDuplicates3 = new LinkedHashSet<String>(listOfTfullName);
        listOfTfullName.clear();
        listOfTfullName.addAll(listWithoutDuplicates3);
    }

    public String myData()  {
        setSelectStudentName(getSelectStudentName());
        setSelectTeacherName(getSelectTeacherName());
        setSelectLang(getSelectLang());
        listStudents();
        listTeachers();
        listLanguages();
        removeDupli();
        return SUCCESS;
    }

    public void fieldValidator(){
        if (getSelectStudentName().length() == 0) {
            addFieldError("selectStudentName", "Student's name is required.");
            myValidator = false;
        }

        if (getSelectTeacherName().length() == 0) {
            addFieldError("selectTeacherName", "Teacher's name is required.");
            myValidator = false;
        }

        if (getSelectLang().length() == 0) {
            addFieldError("selectLang", "Language is required.");
            myValidator = false;
        }
        
        else{
            studentInputChecker();
        }
    }

    public void studentInputChecker(){
        listStudents();
        hasRecord = false;
        for(int i = 0; i < listSize; i++) { 
            if (getSelectStudentName().equals(listOfSfullName.get(i))) {
                hasRecord = true;
            }
        }
        if (hasRecord){
            teacherInputChecker();
        }
        else {
            setMyStatus("ERROR");
            setMyStatusInfo("No Student Name Found");
            myValidator = false;
        }
    }
    public void teacherInputChecker(){
        listTeachers();
        hasRecord = false;
        for(int i = 0; i < listSize; i++) { 
            if (getSelectTeacherName().equals(listOfTfullName.get(i))) {
                hasRecord = true;
            }
        }
        if (hasRecord){

        }
        else {
            setMyStatus("ERROR");
            setMyStatusInfo("No Teacher Name Found");
            myValidator = false;
        }
    }
    
    public String getSelectLang() {
        return selectLang;
    }

    public void setSelectLang(String selectLang) {
        this.selectLang = selectLang;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }


    public ArrayList<String> getListOfLanguages() {
        return listOfLanguages;
    }

    public void setListOfLanguages(ArrayList<String> listOfLanguages) {
        this.listOfLanguages = listOfLanguages;
    }
    public ArrayList<Integer> getListOfStudentsID() {
        return listOfStudentsID;
    }

    public void setListOfStudentsID(ArrayList<Integer> listOfStudentsID) {
        this.listOfStudentsID = listOfStudentsID;
    }

    public ArrayList<Integer> getListOfTeachersID() {
        return listOfTeachersID;
    }

    public void setListOfTeachersID(ArrayList<Integer> listOfTeachersID) {
        this.listOfTeachersID = listOfTeachersID;
    }

    public int getSelectStudentsID() {
    return selectStudentsID;
    }

    public void setSelectStudentsID(int selectStudentsID) {
        this.selectStudentsID = selectStudentsID;
    }

    public int getSelectTeachersID() {
        return selectTeachersID;
    }

    public void setSelectTeachersID(int selectTeachersID) {
        this.selectTeachersID = selectTeachersID;
    }

    public String getSelectStudentName() {
        return selectStudentName;
    }

    public void setSelectStudentName(String selectStudentName) {
        this.selectStudentName = selectStudentName;
    }

    public String getSelectTeacherName() {
        return selectTeacherName;
    }

    public void setSelectTeacherName(String selectTeacherName) {
        this.selectTeacherName = selectTeacherName;
    }

    public String getSelectStudentFName() {
        return selectStudentFName;
    }

    public void setSelectStudentFName(String selectStudentFName) {
        this.selectStudentFName = selectStudentFName;
    }

    public String getSelectStudentLName() {
        return selectStudentLName;
    }

    public void setSelectStudentLName(String selectStudentLName) {
        this.selectStudentLName = selectStudentLName;
    }

    public String getSelectTeacherFName() {
        return selectTeacherFName;
    }

    public void setSelectTeacherFName(String selectTeacherFName) {
        this.selectTeacherFName = selectTeacherFName;
    }

    public String getSelectTeacherLName() {
        return selectTeacherLName;
    }

    public void setSelectTeacherLName(String selectTeacherLName) {
        this.selectTeacherLName = selectTeacherLName;
    }

    public ArrayList<String> getListOfSfullName() {
        return listOfSfullName;
    }

    public void setListOfSfullName(ArrayList<String> listOfSfullName) {
        this.listOfSfullName = listOfSfullName;
    }

    public ArrayList<String> getListOfTfullName() {
        return listOfTfullName;
    }

    public void setListOfTfullName(ArrayList<String> listOfTfullName) {
        this.listOfTfullName = listOfTfullName;
    }

    public String getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(String myStatus) {
        this.myStatus = myStatus;
    }

    public String getMyCatch() {
        return myCatch;
    }

    public void setMyCatch(String myCatch) {
        this.myCatch = myCatch;
    }

    public String getMyStatusInfo() {
        return myStatusInfo;
    }

    public void setMyStatusInfo(String myStatusInfo) {
        this.myStatusInfo = myStatusInfo;
    }

    public boolean isMyValidator() {
        return myValidator;
    }

    public void setMyValidator(boolean myValidator) {
        this.myValidator = myValidator;
    }


    

}