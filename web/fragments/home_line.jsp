<%-- 
    Document   : home_line
    Created on : Jun 20, 2020, 4:27:07 PM
    Author     : steve
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="assets/product_sheet.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
    <center>
        <c:set var="result" value="${requestScope.PRODUCT}"/>
        <c:set var="num_page" value="${requestScope.NOPAGE}"/>
        <c:set var="choose" value="${requestScope.CHOOSE}"/>
        <c:set var="category" value="${requestScope.CATEGORY}"/>
        
        <c:if test="${not empty result}">
            <c:forEach var="product" items="${result}" varStatus="counter">
                <div style="display: inline-block; ">
                    <div class="container">
                        <div class="content">
                            <div style="padding: 20px 5px; background-color: white">
                                <img src="${product.image}" style="width: 300px; height: 300px; background-color: white"/>
                            </div>
                            <div style="width: 300px; padding: 5px 10px;text-align: left">
                                <h3>${product.name}</h3>
                                <p>Length (m): <strong>${product.productDetailLine.lineLength}</strong></p>
                                <p>Strength (kg): <strong>${product.productDetailLine.lineStrength}</strong></p>
                                <p>Diameter (mm): <strong>${product.productDetailLine.diameter}</strong></p>
                                <p>Type: <strong>${product.productDetailLine.lineType}</strong></p>
                            </div>
                            <div style="background-color: orange; padding: 5px 5px">
                                <strong style="color: white"> $${product.price}</strong>
                            </div> 
                        </div>
                        <div class="middle">
                            <input class="text" type="submit" value="Matching Reels" />
                        </div>
                    </div>                 
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${empty result}">
            <h3 style="width: 300px; padding-left: 5px; padding-right: 5px">No Record!</h3>
        </c:if>
        <br/>
        <c:if test="${num_page != 0}">
            <div class="pagination">
                <c:url var="firstLink" value="OrderServlet" >
                    <c:param name="action" value="Paging"/>
                    <c:param name="categoty" value="${category}"/>
                    <c:param name="page" value="1"/> 
                </c:url>
                <a href="${firstLink}">&laquo;</a>
                <c:forEach begin="1" end="${num_page}" var="page">
                    <c:url var="pagingLink" value="OrderServlet" >
                        <c:param name="action" value="Paging"/>
                        <c:param name="categoty" value="${category}"/>
                        <c:param name="page" value="${page}"/> 
                    </c:url>
                    <c:if test="${choose<10}">
                        <c:if test="${page <= 10}">
                            <c:if test="${choose != page}">
                                <a href="${pagingLink}">${page}</a>
                            </c:if>
                            <c:if test="${choose == page}">
                                <a class="active" href="${pagingLink}">${page}</a>
                            </c:if>
                        </c:if>                       
                    </c:if>
                    <c:if test="${choose>9}">
                        <c:if test="${page > (choose-6) && page < (choose+6)}">
                            <c:if test="${choose != page}">
                                <a href="${pagingLink}">${page}</a>
                            </c:if>
                            <c:if test="${choose == page}">
                                <a class="active" href="${pagingLink}">${page}</a>
                            </c:if>
                        </c:if>                       
                    </c:if>
                </c:forEach>
                <c:url var="lasttLink" value="OrderServlet" >
                    <c:param name="action" value="Paging"/>
                    <c:param name="categoty" value="${category}"/>
                    <c:param name="page" value="${num_page}"/> 
                </c:url>
                <a href="${lasttLink}">&raquo;</a>
            </div>
        </c:if>
    </center>
</body>
</html>
