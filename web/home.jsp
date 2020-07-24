<%-- 
    Document   : home
    Created on : Jun 15, 2020, 1:25:11 AM
    Author     : MSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="assets/stylesheet.css" rel="stylesheet" type="text/css"/>
        <title>Home Page</title>        
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
            <c:if test="${sessionScope.USER.roleId == 2}">
                <div style="float: right; margin-top: -51px">
                    <a class="crawl" href="CrawlerPageServlet">My Matching</a>
                </div>
            </c:if>
            <c:if test="${sessionScope.USER.roleId == 1}">
                <div style="float: right; margin-top: -50px">
                    <a class="crawl" href="CrawlerPageServlet">My Matching</a>
                </div>
                <div style="float: right; margin-top: -50px">
                    <a class="crawl" href="CrawlerPageServlet">Crawl Product</a>
                </div>
            </c:if>

        </div>

        <c:if test="${category==1}">
            <%@include file="/fragments/home_reel.jsp" %>            
        </c:if>
        <c:if test="${category==2}">
            <%@include file="/fragments/home_rod.jsp" %>            
        </c:if>
        <c:if test="${category==3}">
            <%@include file="/fragments/home_line.jsp" %>            
        </c:if>
    </body>
</html>
