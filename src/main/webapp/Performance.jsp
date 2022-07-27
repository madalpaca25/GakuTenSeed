<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="s" uri="/struts-tags" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Performance Remarks</title>
    <link rel="stylesheet" href="CSS/mystyle.css">
</head>
    <body>
        <div Class="content">
            <h1> (performance)</h1>
            <br>
            <hr>
            <s:form action="PerformanceRemarks"> 
                <s:select
                label="Select Student"
                list="listOfStudentsID"
                name="userStudentID"
                />
                <br>
                <s:textarea  name = "userRemarks" label = "Evaluation" cols="40" rows="10" />
                <s:radio label="Star" 
                name="userStar" 
                list="#{'1':'1Star', '2':'2stars', '3':'3Stars', '4':'4Stars', '5':'5Stars'}" 
                cssClass="myNews"/>
                <s:submit value="Submit" cssClass="myButton"/>
            </s:form>
        </div>
    </body>
</html>