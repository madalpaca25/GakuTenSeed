package com.example.proj.action;


import com.opensymphony.xwork2.ActionSupport;                                                                                                            
                            
import java.sql.Statement;
import java.sql.SQLException;

import com.example.proj.model.Person;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.text.SimpleDateFormat;  
import java.util.Date;



public class Register extends ActionSupport {
    Login login = new Login();

    Statement statement = null;
    PreparedStatement preparedStatement = null;
    String sql = null;
    ResultSet rs = null;

    private int accountID;
    private String accountType;

    private static final long serialVersionUID = 1L;

    private Person personBean;
    private String error = "None";
    private String currentStatus = "error";
    private boolean myValidator = true;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
    Date date = new Date();
    String currentDate = formatter.format(date);

    private int currentBatch;

    public ArrayList<Integer> listOfBatches = new ArrayList<Integer>(); 
    


    public String execute() throws Exception {

        listBatches();
        return SUCCESS;

    }


    public String saveToDB() {
        try {
            fieldValidator();
            if (myValidator) {
                login.ConnectionChecker();
                if (login.connection != null) {
                    statement = login.connection.createStatement();
                    // personBean.setPassWord(login.MD5(personBean.getPassWord()));
                    String sql = "INSERT INTO accounts(userName, passWord, firstName, lastName, eMail, accountType, accountBatch, regDate, regAdminID) VALUES('"+personBean.getUserName()+"','"+personBean.getPassWord()+"','"+personBean.getFirstName()+"','"+personBean.getLastName()+"', '"+personBean.getEMail()+"', '"+personBean.getAccountType()+"','"+personBean.getAccountBatch()+"','"+currentDate+"', '"+Login.getSession().get("accountTypeID").toString()+"')";
                    statement.executeUpdate(sql);
                    insertCase(personBean.getUserName());
                    currentStatus = SUCCESS;
                }
            }
         } catch (Exception e) {
            System.out.println(e);
            currentStatus = ERROR;  
         } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
         }
        listBatches();
        return currentStatus;  
    }

    public void insertCase (String userName) throws SQLException {
        try{
            sql = "SELECT accountID, accountType FROM accounts where userName=?";
            preparedStatement = login.connection.prepareStatement(sql);
            preparedStatement.setString(1, userName); 
            rs = preparedStatement.executeQuery();
            while(rs.next()){        
                accountID = rs.getInt(1);
                accountType = rs.getString(2);
                currentStatus = SUCCESS;
            }
            switch (accountType){
                case "Student": sql = "INSERT INTO students(accountID) VALUES('"+ accountID +"')";
                                statement.executeUpdate(sql);
                                System.out.println("username : " + userName);
                                System.out.println("Account id : " + accountID);
                                System.out.println("Account Type : " + accountType);
                                break;
                
                case "Teacher": sql = "INSERT INTO teachers(accountID) VALUES('"+ accountID +"')";
                                statement.executeUpdate(sql);
                                System.out.println("username : " + userName);
                                System.out.println("Account id : " + accountID);
                                System.out.println("Account Type : " + accountType);
                                break;

                case "Admin":  sql = "INSERT INTO admins(accountID) VALUES('"+ accountID +"')";
                                statement.executeUpdate(sql);
                                System.out.println("username : " + userName);
                                System.out.println("Account id : " + accountID);
                                System.out.println("Account Type : " + accountType);
                                break;
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

    public void fieldValidator(){
        if (personBean.getUserName().length() == 0) {
            addFieldError("personBean.userName", "Username is required.");
            myValidator = false;
        }
        if (personBean.getPassWord().length() < 6) {
            addFieldError("personBean.passWord", "Password must be greater than 6.");
            myValidator = false;
        }
        if (personBean.getFirstName().length() == 0) {
            addFieldError("personBean.firstName", "First name is required.");
            myValidator = false;
        }
        if (personBean.getLastName().length() == 0) {
            addFieldError("personBean.lastName", "Last name is required.");
            myValidator = false;
        }
        if (personBean.getEMail().length() == 0) {
            addFieldError("personBean.EMail", "Email is required.");
            myValidator = false;
        }
        if (personBean.getAccountBatch() == 0) {
            addFieldError("personBean.accountBatch", "Batch is required.");
            myValidator = false;
        }
        uniqueChecker();
    }

    public void uniqueChecker(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
                sql = "SELECT userName FROM accounts where userName=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setString(1, personBean.getUserName()); 
                rs = preparedStatement.executeQuery();
                if(rs.next()) {
                        addFieldError("personBean.userName", "Username already exists");
                        myValidator = false;
                }
                sql = "SELECT eMail FROM accounts where eMail=?";
                preparedStatement = login.connection.prepareStatement(sql);
                preparedStatement.setString(1, personBean.getEMail()); 
                rs = preparedStatement.executeQuery();
                if(rs.next()) {
                        addFieldError("personBean.EMail", "Email already exists");
                        myValidator = false;
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

    public void listBatches(){
        try {
            login.ConnectionChecker();
            if (login.connection != null) {
            sql = "SELECT accountBatch FROM accounts";
            preparedStatement = login.connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while(rs.next()) {              
                    setCurrentBatch(rs.getInt(1));
                    listOfBatches.add(getCurrentBatch());  
                }
            }
            removeDupli();
        }
        catch (Exception e) {
            System.out.println("CURRENT BATCH" + e);
        } finally {            
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (login.connection != null) try { login.connection.close(); } catch (SQLException ignore) {}
        }
    }

    public void removeDupli(){ 
        Set<Integer> listWithoutDuplicates = new LinkedHashSet<Integer>(listOfBatches);
        listOfBatches.clear();
        listOfBatches.addAll(listWithoutDuplicates);
    }

    

    public String getError() {
        return error;
    }
    
    public Person getPersonBean() {
        return personBean;
    }
    
    public void setPersonBean(Person person) {
        personBean = person;
    }

    public int getCurrentBatch() {
        return currentBatch;
    }

    public void setCurrentBatch(int currentBatch) {
        this.currentBatch = currentBatch;
    }

    public ArrayList<Integer> getListOfBatches() {
        return listOfBatches;
    }

    public void setListOfBatches(ArrayList<Integer> listOfBatches) {
        this.listOfBatches = listOfBatches;
    }

    public boolean isMyValidator() {
        return myValidator;
    }

    public void setMyValidator(boolean myValidator) {
        this.myValidator = myValidator;
    }

}