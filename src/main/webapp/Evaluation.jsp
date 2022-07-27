<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="s" uri="/struts-tags" %> <%@ taglib
prefix="sx" uri="/struts-dojo-tags" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Evaluation</title>
    <link rel="stylesheet" href="CSS/mystyle.css">
</head>
    <body>
        <div Class="content">
            <h1>(evaluation)</h1>
            <br>
            <hr>
        <s:form action="EvaluateTeacher"> 
            <s:select
            label="Select Teacher"
            list="listOfTeachersName"
            name="userTeacherName"
            />
            <br>
            <s:textarea  name = "userEval" label = "Evaluation" cols="40" rows="10" />
            <s:radio label="Star" 
            name="userStar" 
            list="#{'1':'1Star', '2':'2stars', '3':'3Stars', '4':'4Stars', '5':'5Stars'}" 
            cssClass="myNews"/>
            <s:submit value="Submit" cssClass="myButton"/>
            <br>
            <hr>
            <s:iterator  value="Pteachers">  
                <fieldset>  
                    
                    First Name:  <s:property value="firstName"/><br/>  
                    Last Name:  <s:property value="lastName"/><br/>  

                </fieldset>  
            </s:iterator>
        </s:form>
        </div>
    </body>
</html>