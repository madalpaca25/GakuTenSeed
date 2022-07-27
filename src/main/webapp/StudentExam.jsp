<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StudentExam</title>
    <link rel="stylesheet" href="CSS/mystyle.css">
</head>
<body>
    <s:set var = "token" value = "#session.token"/>
    <s:if test="%{#token != null}">
        <s:if test="%{#session.accountType == 'Student'}">
            <s:include value="Navbar.jsp"/>
            <s:actionerror theme="bootstrap"/>
            <s:actionmessage theme="bootstrap"/>
            <s:fielderror theme="bootstrap"/>
            <div class="content">
                <h3>LIST OF EXAMS</h3>
                <div style="display: flex;
                            text-align: center;
                            flex-direction: row;
                            justify-content: flex-end;">
                <s:form action="StudentSortExams" >
                    <sx:autocompleter
                    value=""
                    label="Language/Subject"
                    list="listForSortLanguages"
                    name="sortLang"
                    />
                    <s:submit value="Sort" cssClass="myButton" />
                </s:form>
                <s:if test = "%{studentAction == 'studentSortExams'}">
                    <s:form action="StudentCancelExam" >
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
                                <s:form method="post" action="StudentTakeExam">
                                    <s:hidden name="examID" value="%{examID}" id = "examID" />
                                    <s:hidden name="myAccountBatch" value="%{myAccountBatch}" id = "myLang" />
                                    <s:hidden name="myLang" value="%{myLang}" id = "myLang" />
                                    <s:hidden name="myTerm" value="%{myTerm}" id = "myTerm" />
                                    <s:hidden name="myDesc" value="%{myDesc}" id = "myDesc" />
                                    <s:submit value="Take" cssClass="myButton"/>
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
          This page is only for Students.
        </s:else>
    </s:if>
    <s:else> 
        No Session.
    </s:else>
</body>
</html>