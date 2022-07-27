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
    <title>Admin</title>
</head>
    <body> 
        <s:set var = "token" value = "#session.token"/>
        <s:if test="%{#token != null}">
            <s:if test="%{#session.accountType == 'Admin'}">
                <s:include value="Navbar.jsp"/>
                <div class="content">
                    <h1> ADMIN PAGE </h1>
                    <br>
                    <br>
                    <br>
                    <hr>
                    <br>
                    <br>
                    <br>
                    <!-- <s:form action="AdminAction">
                        <input type="hidden" name="buttonValue" id="buttonValue" value=""/>

                        <s:select name ="adminList"
                        list = "#{'Registrations':'Registrations', 'Exams':'Exams', 'Questions':'Questions'}" />
                        <br>
                        <br>
                        <br>
                        <s:submit value="CREATE" cssClass="myButton" onclick="document.getElementById('buttonValue').value='create';"/>
                        <s:submit value="UPDATE" cssClass="myButton" onclick="document.getElementById('buttonValue').value='update';"/>
                        <s:submit value="DELETE" cssClass="myButton" onclick="document.getElementById('buttonValue').value='delete';"/>

                    </s:form> -->
                    <s:form action="myRegistrations">
                        <s:submit value="My Registrations" cssClass="myButton"/>
                    </s:form>

                    
                    <s:form action="myDuo">
                        <s:submit value="My Duo" cssClass="myButton"/>
                    </s:form>

                    <s:form action="myExams">
                        <s:submit value="My Exams" cssClass="myButton"/>
                    </s:form>

                    <s:form action="myQuestions">
                        <s:submit value="My Questions" cssClass="myButton"/>
                    </s:form>
                    <s:form action="GITS">
                        <s:submit value="GITS" cssClass="myButton"/>
                    </s:form>
                    <s:form action="AFO">
                        <s:submit value="AFO" cssClass="myButton"/>
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