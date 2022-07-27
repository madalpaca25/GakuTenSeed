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
    <title>GITS</title>
    <!-- <sx:head /> -->
</head>
<body> 
    <s:include value="Navbar.jsp"/>
        <div class="content">
            <h1> GITS </h1>
            <br>
            <br>
                <s:if test="%{teachersAction == 'teacherCreateGit'}">
                    <s:form action="CreateGit">
                       <s:textfield name ="myActivityNumber"/>
                       <s:textfield name ="myActivity"/>
                       <s:textfield name ="myDesc"/>
                        <s:submit value="Create Git" cssClass="myButton"/>
                    </s:form>
                    <s:form action="TeacherCancelGit" >
                        <s:submit value="Cancel" cssClass="myButton"/>
                    </s:form>    
                </s:if>
                <s:elseif test="%{teachersAction == 'teacherShowGit'}">
                    <table class="table1">
                        <thead>
                            <tr>
                                <th scope="col">gitID</th>
                                <th scope="col">studentID</th>
                                <th scope="col">gitLinks</th>
                                <th scope="col">Activity</th>
                                <th scope="col">Description</th>
                            </tr>
                        </thead>
                        <tbody>
                            <s:iterator value="gitlinks">
                            <tr>
                                <th scope="row"> <s:property value="gitID"/> </th>
                                <td> <s:property value="myGitLinkID"/> </td>
                                <td> <s:property value="myStudentID"/> </td>
                                <td> <s:property value="myGitLink"/> </td>
                                <td> <s:property value="myDesc"/> </td>
                                <td>
                                </td>
                            </tr>
                            </s:iterator>  
                        </tbody>
                    </table>
                </s:elseif>
                <s:else>
                    <s:form action="TeacherCreateGit" style="text-align:right;">
                        <s:submit value="Create Git" cssClass="myButton"/>
                    </s:form>
                </s:else>
            <br>
            <hr>
            <br>
            <br>
            <br>
            <table class="table2">
                <thead>
                    <tr>
                        <th scope="col">gitID</th>
                        <th scope="col">Activity#</th>
                        <th scope="col">Activity</th>
                        <th scope="col">Description</th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="gits">
                    <tr>
                        <th scope="row"> <s:property value="gitID"/> </th>
                        <td> <s:property value="myActivityNumber"/> </td>
                        <td> <s:property value="myActivity"/> </td>
                        <td> <s:property value="myDesc"/> </td>
                        <td>
                        <div style="display:flex">  
                            <s:form method="post" action="TeacherShowGit">
                                <s:hidden name="gitID" value="%{gitID}" id = "gitID" />
                                <s:hidden name="myActivityNumber" value="%{myActivityNumber}" id = "myActivityNumber" />
                                <s:hidden name="myActivity" value="%{myActivity}" id = "myActivity" />
                                <s:hidden name="myDesc" value="%{myDesc}" id = "myDesc" />
                                <s:submit value="Show" cssClass="myButton"/>
                            </s:form>
                        </div>
                        </td>
                    </tr>
                    </s:iterator>  
                </tbody>
            </table>
        </div>
</body>
</html>