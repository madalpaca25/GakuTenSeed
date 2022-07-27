<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="s" uri="/struts-tags" %> 

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student</title>
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
                <h1> STUDENT </h1>
                <s:form action="StudentExam">
                    <s:submit value="Exams" cssClass="myButton"/>
                </s:form>
                <s:form action="EvaluationOfTeacher">
                    <s:submit value="Evaluate Teacher" cssClass="myButton"/>
                </s:form>
                <br>
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