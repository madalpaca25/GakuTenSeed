<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="s" uri="/struts-tags" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ExamResult</title>
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
            <div Class="content">
                <h1> RESULTS </h1>
                <br>
                <hr>
            <h3>SCORE:<s:property value="#session.studPercScore"/></h3>
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