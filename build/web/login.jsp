<%-- 
    Document   : newjsplogin
    Created on : Jun 15, 2020, 9:22:48 PM
    Author     : steve
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="assets/stylesheet.css" rel="stylesheet" type="text/css"/>
    </head>
    <body >
        <%@include file="/fragments/navbar.jsp"%>
        <fieldset style="border-color: #4da0dc; width: 15%; margin-left: 100px">
            <legend style="font-family: sans-serif; font-size: 20px; color: #4da0dc">Login form</legend>
            <form action="OrderServlet" method="POST">
                <table>
                    <tr>
                        <td style="font-family: sans-serif">Username </td>
                        <td>
                            <input type="text" name="username" value="" />
                        </td>                       
                    </tr>
                    <tr>
                        <td style="font-family: sans-serif">Password </td>
                        <td>
                            <input type="password" name="password" value="" />
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <br/>
                            <input type="submit" value="Login" name="action" class="btnLogin login" id="login"/>
                        </td>
                    </tr>
                </table>
            </form>
        </fieldset>
    </body>
</html>
