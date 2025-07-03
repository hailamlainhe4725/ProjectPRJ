<%-- 
    Document   : login
    Created on : Jun 27, 2025, 11:38:27 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Utils.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/login.css">

    </head>
    <body>
         <%
        if(AuthUtils.isLoggin(request)){
            response.sendRedirect("welcome.jsp");
        }else{
        String checkError = (String) request.getAttribute("checkError");
        %>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value ="login"/>
            Username: <input type="text" name="username"/>
            Password: <input type="password" name="password"/>
            <input type="submit" value="login"/>
        </form>
        <h1><%=checkError!=null?checkError:""%></h1>
        <%}%>
    </body>
</html>
