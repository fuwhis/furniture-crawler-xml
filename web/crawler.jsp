<%-- 
    Document   : Crawler
    Created on : Jun 16, 2020, 10:26:46 AM
    Author     : steve
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">\
        <link href="assets/stylesheet.css" rel="stylesheet" type="text/css"/>
        <title>Crawl Page</title>
    </head>
    <body>
        <%@include file="/fragments/navbar.jsp"%>
        <div class="topnav">
            <a class="crawlAction" href="#news">Crawl NhaXinh category</a>
            <a class="crawlAction" href="#contact">Crawl NoiThat5C</a>
            <a class="crawlAction" href="#about">Crawl Berkley's line</a>
        </div>
        <c:set var="result" value="${requestScope.PRODUCTS}"/>
        <c:if test="${not empty result}">
            <table border="1" style="text-align:center; width: 100%" >
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Id</th>                       
                        <th>Product name</th>   
                        <th>Price</th>  
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${result}" varStatus="counter" >
                    <form action="DispathController">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${dto.id}</td>                          
                            <td>${dto.name}</td>
                            <td>
                                ${dto.price}                                       
                            </td>                                  
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>                
    </c:if>
</body>
</html>
