<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<!DOCTYPE html> 
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/mystyle.css">
    <title>Exam</title>
    <!-- <sx:head /> -->
</head>
<body>
    <s:set var = "token" value = "#session.token"/>
    <s:if test="%{#token != null}">
        <s:if test="%{#session.accountType == 'Admin'}">
            <s:include value="Navbar.jsp"/>
            <s:actionerror theme="bootstrap"/>
            <s:actionmessage theme="bootstrap"/>
            <s:fielderror theme="bootstrap"/>
            <div class="content">
                <h1> EXAMS </h1>
                <br>
                <div class="switch">
                    <s:if test="%{adminAction == 'adminCreateExam'}">
                        <s:form action="CreateExam">
                            <s:select
                                label="Select Batch"
                                value="myAccountBatch"
                                list="listOfBatches"
                                name="myAccountBatch"
                                style="display:inline-flex"/>
                            <s:select label="Term" 
                                headerKey="-1"
                                value="myTerm"
                                list="#{'Prelims':'Prelims', 'Midterms':'Midterms', 'Finals':'Finals'}" 
                                name="myTerm"
                                cssClass="myNews"/>
                            <s:select
                                label="Select Language/Subject"
                                list="listOfLanguages"
                                name="myLang"/>
                            <s:textarea label="Description" name="myDesc" cols="40" rows="10"/>
                            <s:submit value="Create Exam" cssClass="myButton"/>
                        </s:form>
                        <s:form action="AdminCancelExam" >
                            <s:submit value="Cancel" cssClass="myButton"/>
                        </s:form> 
                    </s:if>
                    <s:elseif test="%{adminAction == 'adminUpdateExam'}">
                        <s:form action="UpdateExam">
                            EXAM ID: <s:property value="examID"/>
                            <s:hidden name="examID"/>
                            <s:select
                            label="Select Batch"
                            list="listOfBatches"
                            name="myAccountBatch"
                            />
                            <s:select label="Term" 
                                headerKey="-1"
                                list="#{'Prelims':'Prelims', 'Midterms':'Midterms', 'Finals':'Finals'}" 
                                name="myTerm"
                                cssClass="myNews"/>
                            <s:select
                                label="Select Language/Subject"
                                list="listOfLanguages"
                                name="myLang"
                            />
                            <s:textarea label="Description" name="myDesc" cols="40" rows="10"/>
                            <s:submit value="UpdateExam" cssClass="myButton"/>
                        </s:form>
                        <s:form action="AdminCancelExam" >
                            <s:submit value="Cancel" cssClass="myButton"/>
                        </s:form>   
                    </s:elseif>
                    <s:elseif test = "%{adminAction == 'adminDeleteExam'}">
                        <s:form action="DeleteExam">
                            <s:hidden name="examID"/>
                            ExamID: <s:property value ="examID"/><br>
                            Batch: <s:property value ="myAccountBatch"/><br>
                            Language: <s:property value="myLang"/><br>
                            Term: <s:property value ="myTerm"/><br>
                            Description: <s:property value="myDesc"/><br>
                            <s:submit value="Delete Exam" cssClass="myButton"/>
                        </s:form>
                        <s:form action="AdminCancelExam" >
                            <s:submit value="Cancel" cssClass="myButton"/>
                        </s:form> 
                    </s:elseif>
                    <s:else>
                        <br>
                        <hr>
                        <br>
                        <s:form action="AdminCreateExam" style="text-align:right;">
                            <s:submit value="Create Exam" cssClass="myButton"/>
                        </s:form>
                    </s:else>
                </div>
                <br>
                <hr>
                <br>
                <h3>LIST OF EXAMS</h3>
                <div style="display: flex;
                            text-align: center;
                            flex-direction: row;
                            justify-content: flex-end;">
                <s:form action="AdminSortExams" >
                    <sx:autocompleter
                    value=""
                    label="Language / Subject"
                    list="listForSortLanguages"
                    name="sortLang"
                    />
                    <s:submit value="Sort" cssClass="myButton" />
                </s:form>
                <s:if test = "%{adminAction == 'adminSortExams'}">
                    <s:form action="AdminCancelExam" >
                        <s:submit value="Show All Exams" cssClass="myButton"/>
                    </s:form> 
                </s:if>
                </div>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Exam ID</th>
                            <th scope="col">Batch</th>
                            <th scope="col">Term</th>
                            <th scope="col">Language</th>
                            <th scope="col">Description</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="exams" status="stat">
                        <tr>
                            <th scope="row"> <s:property value="examID"/> </th>
                            <td> <s:property value="myAccountBatch"/> </td>
                            <td> <s:property value="myTerm"/> </td>
                            <td> <s:property value="myLang"/> </td>
                            <td> <s:property value="myDesc"/> </td>
                            <td>
                            <div style="display:flex">  
                                <s:form method="post" action="AdminUpdateExam">
                                    <s:hidden name="examID" value="%{examID}" id = "examID" />
                                    <s:hidden name="myAccountBatch" value="%{myAccountBatch}" id = "myLang" />
                                    <s:hidden name="myLang" value="%{myLang}" id = "myLang" />
                                    <s:hidden name="myTerm" value="%{myTerm}" id = "myTerm" />
                                    <s:hidden name="myDesc" value="%{myDesc}" id = "myDesc" />
                                    <s:submit value="Update" cssClass="myButton"/>
                                </s:form>
                                <s:form method="post" action="AdminDeleteExam">
                                    <s:hidden name="examID" value="%{examID}" id = "examID" />
                                    <s:hidden name="myAccountBatch" value="%{myAccountBatch}" id = "myLang" />
                                    <s:hidden name="myLang" value="%{myLang}" id = "myLang" />
                                    <s:hidden name="myTerm" value="%{myTerm}" id = "myTerm" />
                                    <s:hidden name="myDesc" value="%{myDesc}" id = "myDesc" />
                                    <s:submit value="Delete" cssClass="myButton"/>
                                </s:form>
                            </div>
                            </td>
                        </tr>
                        </s:iterator>  
                    </tbody>
                </table>
            </div>
        </s:if>
        <s:else> 
          This page is only for Admins.
        </s:else>
    </s:if>
    <s:else> 
        No Session.
    </s:else>
</body>
</html>