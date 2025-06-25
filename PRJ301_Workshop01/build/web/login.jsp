<%-- 
    Document   : login
    Created on : Jun 20, 2025, 11:34:19 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import ="model.UserDTO" %>
<%@page import ="util.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
        if(AuthUtils.isLoggin(request)){
            response.sendRedirect("welcome.jsp");
        }else{
            Object objMS = request.getAttribute("message");
            String msg = (objMS == null)?"":(objMS+"");
        %>
        <form action="mainController" method="post">
            <input type="hidden" name ="action" value="login"/>
            <label for="userName">username:</label>
            <input type="text" name="userName" /> <br/>

            <label for="password">password:</label>
            <input type="password" name="password"/> <br/> 
            
            <input type="submit" value ="login"/>
        </form>
        
        <span style="color: red"><%=msg%></span>
        <%}%>
    </body>
</html>
