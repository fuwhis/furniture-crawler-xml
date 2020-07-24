<%-- 
    Document   : suggest_rod
    Created on : Jun 21, 2020, 2:14:16 PM
    Author     : steve
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="assets/matching_sheet.css" rel="stylesheet" type="text/css"/>
        <style>
            .infoTable {
                border: 1px solid black;
                border-collapse: collapse;
                margin-left: 20px
            }
            .infoTh{
                border: 1px solid black;
                border-collapse: collapse;
                padding: 5px 20px;
                font-family: sans-serif
            }
            .infoTd{
                border: 1px solid black;
                border-collapse: collapse;
                padding: 1px 30px;
                font-family: sans-serif
            }
        </style>
    </head>
    <body>
        <c:set var="matchings" value="${requestScope.MATCHINGPRODUCT}"/>
        <c:forEach var="match" items="${matchings}" varStatus="counter">
            <div style="padding: 10px 20px; margin: 0 50px">
                <div class="grid-container">
                    <div class="itemi" style="background-color: white ;width: 350px; height: 280px">
                        <img src="${match.data.image}" style="width: 250px; height: 250px; padding: 10px 0"/>
                    </div>
                    <div class="itemc" style="background-color: whitesmoke">
                        <div style="display: inline-block; font-family: sans-serif; padding: 10px;">
                            <strong style="font-size: 30px; color: #4da0dc">${match.data.name}</strong>
                            <strong style="color: slategrey; margin-left: 20px; margin-right: 10px;font-size: 30px; font-style: italic">$${match.data.price}</strong><img src="assets/price.png" width="20px" height="20px"/>
                        </div>
                        <table class="infoTable">
                            <tr>
                                <th class="infoTh"> Rod Type</th>
                                <th class="infoTh"> Rod Length</th>
                                    <c:if test="${not empty match.data.productDetailRod.rodAction}">
                                    <th class="infoTh"> Rod Action</th>
                                    </c:if>
                                    <c:if test="${not empty match.data.productDetailRod.rodPower}">     
                                    <th class="infoTh"> Rod Power</th>
                                    </c:if>
                                <th class="infoTh"> Line Rating</th>
                            </tr>
                            <tr>
                                <td class="infoTd">${match.data.productDetailRod.rodType}</td>
                                <td class="infoTd">${match.data.productDetailRod.rodLength}m</td>
                                <c:if test="${not empty match.data.productDetailRod.rodAction}">
                                    <td class="infoTd">${match.data.productDetailRod.rodAction}</td>
                                </c:if>
                                <c:if test="${not empty match.data.productDetailRod.rodPower}">     
                                    <td class="infoTd">${match.data.productDetailRod.rodPower}</td>
                                </c:if>
                                <c:if test="${match.data.productDetailRod.lineRatingMin != 0}">
                                    <td class="infoTd">${match.data.productDetailRod.lineRatingMin}lb - ${match.data.productDetailRod.lineRatingMax}lb</td>
                                </c:if>
                                <c:if test="${match.data.productDetailRod.lineRatingMin == 0}">
                                    <td class="infoTd">${match.data.productDetailRod.lineRatingMax}lb</td>
                                </c:if>                            
                        </table> 

                        <div style="font-family: sans-serif; margin-left: 20px">
                            <c:if test="${not empty match.decription}">
                                <p><span style="white-space: pre-line">${match.decription}<span></p>
                            </c:if>

                            <c:if test="${not empty match.place}">
                                <img src="assets/location-fishing.png"> Common Applications: <strong>${match.place}</strong></img>
                            </c:if>
                                <br/>
                            <c:if test="${not empty match.fishes}">
                                <img src="assets/ic-po-fish.png" style="margin-top: 10px"> Popular for fish type: <strong>${match.fishes}</strong></img>
                            </c:if>
                        </div>
                        </tr>
                        <span class="ribbon1">
                            <span>Point<br/><br/>
                                <strong style="font-size: 25px">${match.point}</strong>
                            </span>                          
                        </span>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:if test="${empty matchings}">
            <h2>This reel has't rods to match!</h2>
        </c:if>
    </body>
</html>
