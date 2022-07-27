package com.example.proj.action;

import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;


import com.example.proj.model.Gits;
import com.example.proj.model.Gitlinks;

import com.opensymphony.xwork2.ActionSupport;
public class GITS extends ActionSupport {
    private static final long serialVersionUID = 1L;   
    Login login = new Login();
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    String sql = null;
    ResultSet rs = null;
    private String currentStatus = ERROR;
    private String errorCause;

    private int gitID;
    private int myActivityNumber;
    private String myActivity;
    private String myDesc;

    

    
    private String teachersAction;

    ArrayList<Gits> gits = new ArrayList<Gits>();
    ArrayList<Gitlinks> gitlinks = new ArrayList<Gitlinks>();

    public String execute() throws Exception{
        myData();
        return SUCCESS;
    }



    public String teacherCreateGit(){
        setTeachersAction("teacherCreateGit");
        myData();
        return SUCCESS;
    }

    public String createGit(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "INSERT INTO gits(activityNumber, activity, description)" + " VALUES(?,?,?)";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, getMyActivityNumber());
                preparedStatement.setString(2, getMyActivity());
                preparedStatement.setString(3, getMyDesc());
                preparedStatement.executeUpdate();
                setCurrentStatus(SUCCESS);
                addActionMessage(" CREATE GIT : " + getCurrentStatus().toUpperCase());
            }
        } catch (Exception e) {
            setCurrentStatus(ERROR);
            setErrorCause(e.toString());
            addActionError("CREATE EXAM : FAILED : CAUSE: " + getErrorCause());
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
            myData();
        }
        return currentStatus;
    }

    public String teacherCancelGit(){
        setTeachersAction("teacherCancelGit");
        myData();
        return SUCCESS;
    }

    public void myData()  {
            listGits();
        }

    public String teacherShowGit(){
        setTeachersAction("teacherShowGit");
        listGitLinks();
        return SUCCESS;
    }

    public void listGits(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT * FROM gits";
                statement = login.connection.createStatement();
                rs = statement.executeQuery(sql);
                while(rs.next()){  
                    Gits git = new Gits();
                    git.setGitID(rs.getInt(1));
                    git.setMyActivityNumber(rs.getInt(2));
                    git.setMyActivity(rs.getString(3));
                    git.setMyDesc(rs.getString(4));
                    gits.add(git);
                }
            }
        }
        catch (Exception e) {
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
    }

    public String listGitLinks(){
        try{ login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT * FROM gitlinks where gitID=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setInt(1, getGitID());
                rs = preparedStatement.executeQuery();
                while(rs.next()){  
                    Gitlinks gitlink = new Gitlinks();
                    gitlink.setGitID(rs.getInt(1));
                    gitlink.setMyGitLinkID(rs.getInt(2));
                    gitlink.setMyStudentID(rs.getInt(3));
                    gitlink.setMyGitLink(rs.getString(4));
                    gitlink.setMyDesc(rs.getString(5));
                    gitlinks.add(gitlink);
                }
            }
        }
        catch (Exception e) {
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
            myData();
        }
        return SUCCESS;
    }

    public ArrayList<Gits> getGits() {
        return gits;
    }

    public void setGits(ArrayList<Gits> gits) {
        this.gits = gits;
    }

    public ArrayList<Gitlinks> getGitlinks() {
        return gitlinks;
    }

    public void setGitlinks(ArrayList<Gitlinks> gitlinks) {
        this.gitlinks = gitlinks;
    }

    public String getTeachersAction() {
        return teachersAction;
    }

    public void setTeachersAction(String teachersAction) {
        this.teachersAction = teachersAction;
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
    public int getGitID() {
        return gitID;
    }

    public void setGitID(int gitID) {
        this.gitID = gitID;
    }

    public int getMyActivityNumber() {
        return myActivityNumber;
    }

    public void setMyActivityNumber(int myActivityNumber) {
        this.myActivityNumber = myActivityNumber;
    }

    public String getMyActivity() {
        return myActivity;
    }

    public void setMyActivity(String myActivity) {
        this.myActivity = myActivity;
    }

    public String getMyDesc() {
        return myDesc;
    }

    public void setMyDesc(String myDesc) {
        this.myDesc = myDesc;
    }

}
