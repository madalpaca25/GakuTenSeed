package com.example.proj.action;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;                
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.proj.model.Person;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.interceptor.SessionAware;

public class Login extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 1L;   
    String sql = null; 
    Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;
    private String myStatus = "None";
    private String myStatusInfo;
    // private boolean currentSession = false;
    private String currentStatus = "error";

    static Person person = new Person();

    private String userName;
    private String passWord;
    private String protpassWord;
    private String encryptedText;
    private int activeAccountID;
    private String activeUserName;
    private String activeFirstName;
    private String activeLastName;
    private String activeEMail;
    private String activeAccountType;
    private int activeAccountBatch;
    private int activeAccountTypeID;
    private String activeToken;
    private String token = null;
    private String logoutToken;

    private boolean myValidator = true;





    private static Map<String, Object> userSession;


 
    public String execute() throws Exception {
        try {
            fieldValidator();
            if (myValidator) {
                ConnectionChecker();
                if (connection != null) {
                    sql = " SELECT * FROM accounts WHERE userName=? AND passWord=?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, userName);
                    //setProtpassWord(MD5(passWord));
                    //preparedStatement.setString(2, getProtpassWord());
                    preparedStatement.setString(2, passWord); 
                    rs = preparedStatement.executeQuery();
                    while (rs.next()){ 
                        activeAccountID = rs.getInt(1);
                        activeUserName = rs.getString(2);
                        activeFirstName = rs.getString(4); 
                        activeLastName = rs.getString(5); 
                        activeEMail = rs.getString(6);  
                        activeAccountType = rs.getString(7); 
                        activeAccountBatch = rs.getInt(8);
                        currentStatus = SUCCESS;
                    }
                    if (currentStatus == SUCCESS){
                        statement = connection.createStatement();
                        sql = "INSERT INTO sessions(accountID, userName, tokenValue) VALUES('"+getActiveAccountID()+"','"+getActiveUserName()+"','"+getToken()+"')";
                        statement.executeUpdate(sql);
                        System.out.println("LOG IN TOKEN: " + getToken());
                        setActiveToken(getToken());
                        userSession.put("accountID", getActiveAccountID());
                        userSession.put("userName", getUserName());
                        userSession.put("accountType", getActiveAccountType());
                        userSession.put("accountBatch", getActiveAccountBatch());
                        userSession.put("token", getToken());
                        switch(activeAccountType) {
                            case "Admin":
                                adminIDGetter();
                                userSession.put("accountTypeID", getActiveAccountTypeID());
                                return "Admin";

                            case "Teacher":
                                teacherIDGetter();
                                userSession.put("accountTypeID", getActiveAccountTypeID());
                                return "Teacher";

                            case "Student":
                                studentIDGetter();
                                userSession.put("accountTypeID", getActiveAccountTypeID());
                                return "Student";
                        }
                    }
                    else {
                        currentStatus = ERROR;
                        setMyStatus("ERROR");
                        addActionError("Username / Password is incorrect.");
                    }
                }
            }   
         } catch (Exception e) {
            setMyStatus("");
            setMyStatusInfo("");
            currentStatus = ERROR;
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
         return currentStatus;
    }

    public void studentIDGetter(){
        try {
            ConnectionChecker();
            if (connection != null) {
                sql = "select STUDENTS.studentID from accounts RIGHT JOIN STUDENTS ON Accounts.accountID = STUDENTS.accountID where Accounts.accountID =?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, getSession().get("accountID").toString()); 
                rs = preparedStatement.executeQuery();
                while(rs.next()){        
                    setActiveAccountTypeID(rs.getInt(1));
                    System.out.println("Current Student ID: " +rs.getString(1));
                }
            }
        } catch (Exception e) {
            setMyStatus("");
            setMyStatusInfo("");
            currentStatus = ERROR;
        } finally {
           if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
           if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
    }

    public void teacherIDGetter(){
        try {
            ConnectionChecker();
            if (connection != null) {
                String sql = "select teachers.teacherID from accounts RIGHT JOIN teachers ON accounts.accountID = teachers.accountID where accounts.accountID =?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, getSession().get("accountID").toString()); 
                ResultSet rs= preparedStatement.executeQuery();
                while(rs.next()){        
                    setActiveAccountTypeID(rs.getInt(1));
                    System.out.println("Current Teacher ID: " + rs.getString(1));
                }
            }
        } catch (Exception e) {
            setMyStatus("");
            setMyStatusInfo("");
            currentStatus = ERROR;
        } finally {
           if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
           if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
    }

    public void adminIDGetter(){
        try {
            ConnectionChecker();
            if (connection != null) {
                String sql = "SELECT admins.adminID FROM accounts RIGHT JOIN admins ON accounts.accountID = admins.accountID WHERE accounts.accountID =?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, getSession().get("accountID").toString()); 
                ResultSet rs= preparedStatement.executeQuery();
                while(rs.next()){        
                    setActiveAccountTypeID(rs.getInt(1));
                    System.out.println("Current Admin ID: " +rs.getString(1));
                }
            }
        } catch (Exception e) {
            setMyStatus("");
            setMyStatusInfo("");
            currentStatus = ERROR;
        } finally {
           if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
           if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
           if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
    }

    public void ConnectionChecker() throws Exception {
        String URL = "jdbc:mysql://localhost:3306/mydb?useTimezone=true&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, "root", "madmad123");
    }

   

    public String logout() throws Exception {    
        logoutToken = (String) userSession.get("token");
        logoutDB(logoutToken);
        userSession.clear();
        if (logoutToken != ""){
            System.out.println("log out success!");
            currentStatus = SUCCESS;
        } else {
            currentStatus = ERROR; 
        }
        return currentStatus;
        
    }

    public void logoutDB(String logoutToken) throws Exception {
        try {
            ConnectionChecker();
            if (connection != null) {
                sql = " DELETE FROM sessions where tokenValue=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, logoutToken); 
                preparedStatement.execute();
                System.out.println("Deleted Token from DB: " +  logoutToken); 
            }
        } catch (Exception e) {
            setMyStatus("");
            setMyStatusInfo("");
            currentStatus = ERROR;
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
    }

    // public boolean SessionChecker() {
    //     try {
    //         ConnectionChecker();
    //         if (connection != null) {
    //             sql = " SELECT userName FROM sessions where tokenValue=?";
    //             preparedStatement = connection.prepareStatement(sql);
    //             setToken(userSession.get("token").toString());
    //             preparedStatement.setString(1, getToken()); 
    //             rs = preparedStatement.executeQuery();
    //             System.out.println(getToken());
    //             while (rs.next()){ 
    //                 System.out.println("SUCCESS! username: " +  rs.getString(1)); 
    //                 currentSession = true;
    //             }
    //         }
    //         else {
    //             setMyStatus(ERROR);
    //             setMyStatusInfo("No session from Database.");
    //         }
    //     }
    //     catch (Exception e) {
    //         setMyStatus("");
    //         setMyStatusInfo("");
    //         currentStatus = ERROR;
    //     } finally {
    //         if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
    //         if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
    //     }
    //     return currentSession;
    // }
    
    public String MD5(String text){
        try{
            /* MessageDigest instance for hashing using SHA256 */
            MessageDigest md = MessageDigest.getInstance("MD5");
            /* digest() method called to calculate message digest of ’text’ and return array of byte */
            byte[] hash = md.digest(text.getBytes(StandardCharsets.UTF_8));
            /* The bytes array has bytes in decimal form. Convert it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for(int i=0; i< hash.length; i++)
                {
                s.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
                }
            /* Complete hashed text in hexadecimal format */
            encryptedText = s.toString();
        }
    catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }
    return encryptedText;
    }

    public void fieldValidator(){
        if (getUserName().length() == 0) {
            addActionError("Username is required.");
            myValidator = false;
        }

        if (getPassWord().length() == 0) {
            addActionError("Password is required.");
            myValidator = false;
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public String getActiveFirstName() {
        return activeFirstName;
    }

    public void setActiveFirstName(String activeFirstName) {
        this.activeFirstName = activeFirstName;
    }

    public String getActiveUserName() {
        return activeUserName;
    }

    public void setActiveUserName(String activeUserName) {
        this.activeUserName = activeUserName;
    }

    public String getActiveLastName() {
        return activeLastName;
    }

    public void setActiveLastName(String activeLastName) {
        this.activeLastName = activeLastName;
    }

    public String getActiveAccountType() {
        return activeAccountType;
    }

    public void setActiveAccountType(String activeAccountType) {
        this.activeAccountType = activeAccountType;
    }

    public String getProtpassWord() {
        return protpassWord;
    }

    public void setProtpassWord(String protpassWord) {
        this.protpassWord = protpassWord;
    }

    public int getActiveAccountID() {
        return activeAccountID;
    }

    public void setActiveAccountID(int activeAccountID) {
        this.activeAccountID = activeAccountID;
    }

    public String getLogoutToken() {
        return logoutToken;
    }

    public void setLogoutToken(String logoutToken) {
        this.logoutToken = logoutToken;
    }

    public String getActiveEMail() {
        return activeEMail;
    }

    public void setActiveEMail(String activeEMail) {
        this.activeEMail = activeEMail;
    }

    public int getActiveAccountBatch() {
        return activeAccountBatch;
    }

    public void setActiveAccountBatch(int activeAccountBatch) {
        this.activeAccountBatch = activeAccountBatch;
    }

    public int getActiveAccountTypeID() {
        return activeAccountTypeID;
    }

    public void setActiveAccountTypeID(int activeAccountTypeID) {
        this.activeAccountTypeID = activeAccountTypeID;
    }

    public String getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(String myStatus) {
        this.myStatus = myStatus;
    }

    public String getMyStatusInfo() {
        return myStatusInfo;
    }

    public void setMyStatusInfo(String myStatusInfo) {
        this.myStatusInfo = myStatusInfo;
    }

    public String getActiveToken() {
        return activeToken;
    }

    public void setActiveToken(String activeToken) {
        this.activeToken = activeToken;
    }

    public boolean isMyValidator() {
        return myValidator;
    }

    public void setMyValidator(boolean myValidator) {
        this.myValidator = myValidator;
    }


    @Override
    public void setSession(Map<String, Object> session){
        userSession = session;
    }

    public static Map<String, Object> getSession() {
        return userSession;
    }



}