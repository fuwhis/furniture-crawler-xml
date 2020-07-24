<%-- 
    Document   : suggest
    Created on : Jun 21, 2020, 11:06:03 AM
    Author     : steve
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="assets/stylesheet.css" rel="stylesheet" type="text/css"/>
        <title>Suggest Page</title>
    </head>
    <body>
        <%@include file="/fragments/navbar.jsp"%>
        <c:set var="category" value="${requestScope.CATEGORY}"/>
        <div class="topnav">
            <c:url var="reelsLink" value="OrderServlet" >
                <c:param name="action" value="Reels"/>
                <c:param name="categoty" value="1"/>
                <c:param name="page" value="1"/> 
            </c:url>
            <c:if test="${category==1}">
                <a class="active" href="${reelsLink}">Reels</a>
            </c:if>
            <c:if test="${category!=1}">
                <a href="${reelsLink}">Reels</a>
            </c:if>  
            <c:url var="rodsLink" value="OrderServlet" >
                <c:param name="action" value="Rods"/>
                <c:param name="categoty" value="2"/>   
                <c:param name="page" value="1"/> 
            </c:url>
            <c:if test="${category==2}">
                <a class="active" href="${rodsLink}">Rods</a>
            </c:if>
            <c:if test="${category!=2}">
                <a href="${rodsLink}">Rods</a>
            </c:if> 
            <c:url var="lineLink" value="OrderServlet" >
                <c:param name="action" value="Line"/>
                <c:param name="categoty" value="3"/>  
                <c:param name="page" value="1"/> 
            </c:url>
            <c:if test="${category==3}">
                <a class="active" href="${lineLink}">Line</a>
            </c:if>
            <c:if test="${category!=3}">
                <a href="${lineLink}">Line</a>
            </c:if> 
            <div style="margin-top:12px; margin-bottom: 12px;margin-left: 50px">
                <input type="text" name="txtSearch" value="" id="searchForm"/>
                <input type="submit" value="Search" name="btnAction" class="btnSearch search"/>
            </div>
        </div>
        <br/>
        <c:set var="matchings" value="${requestScope.MATCHINGPRODUCT}"/>
        <c:if test="${not empty matchings}">
            <%@include file="/fragments/suggest_rod.jsp" %>    
        </c:if>
    </body>
</html>
