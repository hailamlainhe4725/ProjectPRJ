<%-- 
    Document   : login
    Created on : Jun 10, 2025, 6:15:35 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="mainController" method="post">
            <input type="hidden" name ="action" value ="login"/>
            userID :<input type ="text" name="strUserID"/>
            password :<input type="password" name="strPassword"/>
            phoneNumber :<input type="text" name="strPhone" required="required"/>
            <input type ="submit" value ="login"/>
        </form>
    </body>
</html>
