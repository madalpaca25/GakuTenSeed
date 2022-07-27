<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/mystyle.css">
    <title>Login</title>
    <sb:head />

</head>
<body>
    <s:set var = "token" value = "#session.token"/>
    <s:if test="%{#token != null}">
        <s:include value="Navbar.jsp"/>
            <div Class="content">
            <br>
            <br>
            <s:form action="logout">
                <s:submit value="Logout" cssClass="myButton"/>
            </s:form>
        </div>
    </s:if>
    <s:else>
        <s:form action="Login">
            <div class="content">
                <s:token value="#session.token"/>
                <s:actionerror theme="bootstrap"/>
                <s:actionmessage theme="bootstrap"/>
                <s:fielderror theme="bootstrap"/>
                <s:textfield name="userName" cssClass="myText" placeholder="Username"/>
                <br>
                <s:textfield name="passWord" cssClass="myText" placeholder="Password"/>
                <br>
                <s:submit value="Login" cssClass="myButton"/>
            </div>
        </s:form>
    </s:else>
    
</body>
</html>