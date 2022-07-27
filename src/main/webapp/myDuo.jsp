<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<!DOCTYPE html>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Register</title>
        <link rel="stylesheet" href="CSS/mystyle.css">
        <sx:head />
</head>
    <body>
        <s:set var = "token" value = "#session.token"/>
        <s:if test="%{#token != null}">
            <s:if test="%{#session.accountType == 'Admin'}">
                <s:include value="Navbar.jsp"/>
                <div class="content">
                    <s:if test="%{myStatus == 'ERROR'}">
                        Failed: <s:property value = "myStatusInfo"/>
                        <br>
                        <br>
                    </s:if>
                    <s:elseif test="%{myStatus =='SUCCESS'}">
                        <s:property value = "myStatusInfo"/>
                        <br>
                        <br>
                    </s:elseif>
                    <s:form action="UpdateDuo">
                        <sx:autocompleter
                        value=""
                        label="Student's Name"
                        list="listOfSfullName"
                        name="selectStudentName"
                        />
                        <sx:autocompleter
                        value=""
                        label="Teacher's Name"
                        list="listOfTfullName"
                        name="selectTeacherName"
                        />
                        <sx:autocompleter
                        value=""
                        label="Language/Subject"
                        list="listOfLanguages"
                        name="selectLang"
                        />
                        <s:submit value="Update Duo" cssClass="myButton"/>
                    </s:form>
                </div>
            </s:if>
            <s:else> 
                This page is only for Admin.
            </s:else>
        </s:if>
        <s:else> 
            No Session.
        </s:else>
    </body>
</html>