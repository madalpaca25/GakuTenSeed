<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="s" uri="/struts-tags" %> 

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- <link rel="stylesheet" href="CSS/style.css"> -->
    <title>Books</title>
    <style>
    *{
        margin: 0;
        padding: 0;
        font-family: sans-serif;
    }    
    .banner{
        width: 100%;
        height: 100vh;
        background-image:url(CSS/background2.jpg);
        background-size: cover;
        background-position: center;
    }
    .myButton{
        width: 200px;
        padding: 15px 0;
        text-align: center;
        margin: 20px 10px;
        border-radius: 25px;
        font-weight: bold;
        border: 2px solid #329600;
        background: transparent;
        color: rgb(22, 20, 20);
        cursor: pointer;
        position: relative;
        overflow: hidden;  
    }

    .myBooks{
        text-align: center;
        border-radius: 5px;
        margin: auto;
        font-weight: bold;
    }
    .content{
        
        position: relative;
        text-align: center;
        border-radius: 5px;
        padding: 45px;
        margin: auto;
        font-weight: bold;
    }
    </style>
    
</head>
<body>
    <div class="banner">
    <s:include value="Navbar.jsp"/>

    <div class="content">
        <s:form action="Books">
            <hr>
            <br>
            <s:textfield key="myQuery" cssClass="myBooks" label="TITLE"/>
            <br>
            <s:submit value="Search" cssClass="myButton"/>
            <hr>
        </s:form>
    </div>
        <s:iterator  value="booksResponse.docs">  
            <fieldset>
                <div class="books-image">
                    <image src="<s:property value="cover_i"/>" alt="No cover">
                </div>
                <h2>"<s:property value="title"/> </h3>
                <h4>by <s:property value="author_name"/></h4>
                <br>
                <hr>
            </fieldset>  
        </s:iterator>
    </div>
</body>
</html>