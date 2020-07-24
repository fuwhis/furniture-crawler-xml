<%-- 
    Document   : navbar
    Created on : Jun 15, 2020, 9:18:02 PM
    Author     : steve
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="assets/stylesheet.css" rel="stylesheet" type="text/css"/>
    </head>
    <div style="padding: 15px">       
        <a href="OrderServlet" style="color: black">
            <ul id="logo">
                <li><img src="assets/ic_logo_fs.png" width="80" height="80"></li>
                <li>
                    <table style="margin-top: 13px">
                        <tr>
                            <td style="font-family: sans-serif">The</td>                   
                        </tr>
                        <tr>
                            <th style="font-family: sans-serif; font-size: 25px;">Best Match Fishing Gear</th>
                        </tr>
                    </table>
                </li>
                <c:if test="${sessionScope.USER == null}">
                    <li style="width: 90%">
                        <a href="login.jsp" class="btnLogin login" id="login">Sign-In</a>
                    </li>
                    <li style="width: 82%">
                        <a href="login.jsp" class="btnLogin login" id="login" style="margin-top: -37px">Sign-Up</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.USER != null}">
                    <li style="width: 85%">
                        <p style="font-family: sans-serif; color: #4da0dc">Welcome, <span style="font-style: italic">${sessionScope.USER.fullName}</span></p>
                    </li>
                    <li style="width: 100%">
                        <c:url var="signoutLink" value="OrderServlet" >
                            <c:param name="action" value="SignOut"/>                          
                        </c:url>
                        <a href="${signoutLink}" class="btnLogin login" id="login" style="margin-top: -60px">Sign-Out</a>
                    </li>
                </c:if>
            </ul> 
        </a>           
    </div>
</html>
