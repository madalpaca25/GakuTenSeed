<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="s" uri="/struts-tags" %> <%@ taglib
prefix="sx" uri="/struts-dojo-tags" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/mystyle.css">
    <title>Questions</title>
</head>
<body>
    <s:set var = "token" value = "#session.token"/>
    <s:if test="%{#token != null}">
        <s:if test="%{#session.accountType == 'Admin'}">
            <s:include value="Navbar.jsp"/>
            <s:actionerror theme="bootstrap"/>
            <s:actionmessage theme="bootstrap"/>
            <s:fielderror theme="bootstrap"/>
            <div class="content">
                <h1> QUESTIONS </h1>
                <br>
                <br>
                <hr>
                <br>
                <div class="switch">
                    <s:if test="%{adminAction == 'adminCreateQuestion'}">
                        <s:form action="CreateQuestion">
                            <s:select
                                label="Select Exam ID"
                                list="listOfExams"
                                name="ExamID"/>
                            <s:textarea  name = "myQues" label="Question" cols="40" rows="10"/>
                            <s:textfield name = "myChoiceA" label = "a)" />
                            <s:textfield name = "myChoiceB" label = "b)" />
                            <s:textfield name = "myChoiceC" label = "c)" />
                            <s:textfield name = "myChoiceD" label = "d)" />
                            <s:radio label="Answer" 
                                name="myAnsKey" 
                                list="#{'a':'a', 'b':'b', 'c':'c', 'd':'d'}" 
                                cssClass="myNews"/>
                            <s:textarea  name = "myExp" label = "Explanation" cols="40" rows="10" /> 
                            <s:submit value="Create Question" cssClass="myButton"/>
                        </s:form>
                        <s:form action="AdminCancelQuestion" >
                            <s:submit value="Cancel" cssClass="myButton"/>
                        </s:form>    
                    </s:if>
                    <s:elseif test="%{adminAction == 'adminUpdateQuestion'}">
                        <s:form action="UpdateQuestion">
                            <s:hidden name="myQuesID"/>
                            <s:property value="myQuesID"/>
                            <s:select
                                label="Select Exam ID"
                                list="listOfExams"
                                name="examID"/>
                            <br>
                            <s:textarea  name = "myQues" label="Question" cols="40" rows="10"/>
                            <s:textfield name = "myChoiceA" label = "a)" />
                            <s:textfield name = "myChoiceB" label = "b)" />
                            <s:textfield name = "myChoiceC" label = "c)" />
                            <s:textfield name = "myChoiceD" label = "d)" />
                            <s:radio label="Answer" 
                                name="myAnsKey" 
                                list="#{'a':'a', 'b':'b', 'c':'c', 'd':'d'}" 
                                cssClass="myNews"/>
                            <s:textarea  name = "myExp" label = "Explanation" cols="40" rows="10" /> 
                            <s:submit value="Update Question" cssClass="myButton"/>
                        </s:form>
                        <s:form action="AdminCancelQuestion" >
                            <s:submit value="Cancel" cssClass="myButton"/>
                        </s:form> 
                    </s:elseif>
                    <s:elseif test = "%{adminAction == 'adminDeleteQuestion'}">
                        <s:form action="DeleteQuestion">   
                            <s:hidden name="myQuesID"/>
                                ExamID: <s:property value ="examID"/><br>
                                Question: <s:property value="myQues"/><br>
                                A: <s:property value ="myChoiceA"/><br>
                                B: <s:property value="myChoiceB"/><br>
                                C: <s:property value="myChoiceC"/><br>
                                D: <s:property value ="myChoiceD"/><br>
                                AnswerKey: <s:property value="myAnsKey"/><br>
                                Explanation: <s:property value="myExp"/><br>
                            <s:submit value="Delete Question" cssClass="myButton"/>
                        </s:form>
                        <s:form action="AdminCancelQuestion" >
                            <s:submit value="Cancel" cssClass="myButton"/>
                        </s:form>    
                    </s:elseif>
                    <s:else>
                        <s:form action="AdminCreateQuestion" style="text-align:right;">
                            <s:submit value="Create Question" cssClass="myButton"/>
                        </s:form>
                    </s:else>
                </div>
                <div style="display: flex;
                    text-align: center;
                    flex-direction: row;
                    justify-content: flex-end;">
                    <s:form action="SortQuestions" >
                        <sx:autocompleter
                        value=""
                        label="Questions"
                        list="listOfQuestions"
                        name="sortQues"
                        />
                        <s:submit value="Sort" cssClass="myButton" />
                    </s:form>
                    <s:if test = "%{adminAction == 'sortQuestions'}">
                        <s:form action="AdminCancelExam" >
                            <s:submit value="Show All Questions" cssClass="myButton"/>
                        </s:form> 
                    </s:if>
                </div>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Exam ID</th>
                            <th scope="col">Question</th>
                            <th scope="col">A)</th>
                            <th scope="col">B)</th>
                            <th scope="col">C)</th>
                            <th scope="col">D)</th>
                            <th scope="col">Answer Key</th>
                            <th scope="col">Explanation</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="questions">
                        <tr>
                            <th scope="row"> <s:property value="examID"/> </th>
                            <td> <s:property value="myQues"/> </td>
                            <td> <s:property value="myChoiceA"/> </td>
                            <td> <s:property value="myChoiceB"/> </td>
                            <td> <s:property value="myChoiceC"/> </td>
                            <td> <s:property value="myChoiceD"/> </td>
                            <td> <s:property value="myAnsKey"/> </td>
                            <td> <s:property value="myExp"/> </td>
                            <td>
                            <div style="display:flex">  
                                <s:form method="post" action="AdminUpdateQuestion">
                                    <s:hidden name="examID" value="%{examID}" id = "examID"/>
                                    <s:hidden name="myQuesID" value="%{myQuesID}" id = "myQuesID"/>
                                    <s:hidden name="myQues" value="%{myQues}" id = "myQues" />
                                    <s:hidden name="myChoiceA" value="%{myChoiceA}" id = "myChoiceA" />
                                    <s:hidden name="myChoiceB" value="%{myChoiceB}" id = "myChoiceB" />
                                    <s:hidden name="myChoiceC" value="%{myChoiceC}" id = "myChoiceC" />
                                    <s:hidden name="myChoiceD" value="%{myChoiceD}" id = "myChoiceD" />
                                    <s:hidden name="myAnsKey" value="%{myAnsKey}" id = "myAnsKey" />
                                    <s:hidden name="myExp" value="%{myExp}" id = "myExp" />
                                    <s:submit value="Update" cssClass="myButton"/>
                                </s:form>
                                <s:form method="post" action="AdminDeleteQuestion">
                                    <s:hidden name="examID" value="%{examID}" id = "examID"/>
                                    <s:hidden name="myQuesID" value="%{myQuesID}" id = "myQuesID"/>
                                    <s:hidden name="myQues" value="%{myQues}" id = "myQues" />
                                    <s:hidden name="myChoiceA" value="%{myChoiceA}" id = "myChoiceA" />
                                    <s:hidden name="myChoiceB" value="%{myChoiceB}" id = "myChoiceB" />
                                    <s:hidden name="myChoiceC" value="%{myChoiceC}" id = "myChoiceC" />
                                    <s:hidden name="myChoiceD" value="%{myChoiceD}" id = "myChoiceD" />
                                    <s:hidden name="myAnsKey" value="%{myAnsKey}" id = "myAnsKey" />
                                    <s:hidden name="myExp" value="%{myExp}" id = "myExp" />
                                    <s:submit value="Delete" cssClass="myButton"/>
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
          This page is only for Admins.
        </s:else>
    </s:if>
    <s:else> 
        No Session.
    </s:else>
</body>
</html>