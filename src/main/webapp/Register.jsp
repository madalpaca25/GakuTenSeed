<!DOCTYPE html> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/mystyle.css">
    <title>Registration</title>
    <sx:head />
</head>
<body>
  <s:set var = "token" value = "#session.token"/>
  <s:if test="%{#token != null}">
      <s:if test="%{#session.accountType == 'Admin'}">
        <s:include value="Navbar.jsp"/>
        <div class="content">
          <h2>REGISTRATION</h2>
          <br>
          <br>
          <hr>
          <br>
          <br>
          <s:form action="Register">
              <s:textfield name="personBean.userName" cssClass="myText" label="Username" />
              <s:password name="personBean.passWord" cssClass="myText" label="Password" />
              <s:textfield name="personBean.firstName" cssClass="myText" label="First Name" />
              <s:textfield name="personBean.lastName" cssClass="myText" label="Last Name" />
              <s:textfield name="personBean.EMail" cssClass="myText" label="Email" />
              <br>
              <sx:autocompleter
              value=""   
              label="Batch"
              list="listOfBatches"
              name="personBean.accountBatch"/>
              <br>
              <br>
              <s:select
              label="Account Type"
              list="#{'Student':'Student', 'Teacher':'Teacher', 'Admin':'Admin'}" 
              name="personBean.accountType"
              />
              <s:submit value="Submit" cssClass="myButton"/>
          </s:form>
          <br>
          <hr>
          <br>
          <p><a href="<s:url action='List' />" >View All Records.</a></p>	
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