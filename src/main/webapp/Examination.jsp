<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Examination</title>
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
                <h1>EXAMINATION</h1>
                <br>
                <hr>
                <s:if test = "%{studentAction != 'studentStartExam'}">
                    <s:form action="StartExam">
                        <s:submit value="START" cssClass="myButton"/> 
                    </s:form>
                </s:if>
                <s:elseif test = "%{studentAction == 'studentStartExam'}">
                    <s:form action="SubmitExam" >
                        <s:iterator  value="questions" status="stat">
                        <div>
                            <s:hidden name="studAns[%{#stat.index}].studentQuestionID"/>
                            <s:property value="%{#stat.count}"/>) 
                            <s:property value="myQues"/><br/>
                            <s:radio 
                            name="studAns[%{#stat.index}].studentAns" value ="myChoiceA"
                            list="#{'a':'a) ' + myChoiceA, 'b':'b) ' + myChoiceB, 'c':'c) ' + myChoiceC, 'd':'d) ' + myChoiceD}"
                            cssClass="myNews"/>
                            <br>
                            </div>
                        </s:iterator>
                        <s:submit value="SUBMIT YOUR EXAM" cssClass="myButton"/> 
                    </s:form>
                </s:elseif>
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