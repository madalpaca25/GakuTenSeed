<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    </head>
<style>
    .navbar{
        text-align: center;
        padding: 60px 0;
        align-items: center;
        justify-content: space-between;
        font-size:xx-large;
    }

    .navbar ul li{
    list-style: none;
    display: inline-block;
    margin: 0 20px;
    position: relative;
    
    }

    .navbar ul li a{
        text-decoration: none;
        color: rgb(24, 23, 23);
        text-transform: uppercase;
        
    }

    .navbar ul li::after{
        content: '';
        height: 5px;
        width: 0%;
        background: #009688;
        position: absolute;
        left: 0;
        bottom: -10px;
        transition: 0.5s;
    }
    
    .navbar ul li:hover::after{
        width: 100%;
    }
</style>
    <div class="navbar">
        <s:set var = "accountType" value = "#session.accountType"/>
        <ul>
            <li><a href="<s:url action='index' />">HOME</a></li>
            <s:if test="%{#accountType == 'Admin'}">
                <li><a href="<s:url action='Admin' />">Admin</a></li>
            </s:if>
            <s:elseif test="%{#accountType == 'Teacher'}">
                <li><a href="<s:url action='Teacher' />">Teacher</a></li>
            </s:elseif>
            <s:elseif test="%{#accountType == 'Student'}">
                <li><a href="<s:url action='Student' />">Student</a></li>
            </s:elseif>
            <li><a href="<s:url action='FAQs' />">FAQ's</a></li>
        </ul>
    </div>
</html>