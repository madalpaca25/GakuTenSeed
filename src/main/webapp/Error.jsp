<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Error...</title>
    <link rel="stylesheet" href="CSS/mystyle.css">
  <style>
     
  </style>
    
    
  </head>
  <body>
    <div class="banner">
    <s:include value="Navbar.jsp"/>
    


    <h2>Error: <s:property value = "#session.myError" /> </h2>

    


    <!-- <p><a href="<s:url action='index' />" >Return to home page</a>.</p> -->
  </div>
  </body>
</html>