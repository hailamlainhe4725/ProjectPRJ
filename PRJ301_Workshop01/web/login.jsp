<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import ="model.UserDTO" %>
<%@page import ="util.AuthUtils" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        form {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
            width: 320px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background: #0056b3;
        }

        .error-message {
            color: red;
            margin-top: 10px;
            text-align: center;
        }
    </style>
</head>
<body>
    <%
    if (AuthUtils.isLoggin(request)) {
        response.sendRedirect("welcome.jsp");
    } else {
        Object objMS = request.getAttribute("message");
        String msg = (objMS == null) ? "" : (objMS + "");
    %>
    <form action="mainController" method="post">
        <input type="hidden" name="action" value="login"/>
        
        <label for="userName">Username</label>
        <input type="text" name="userName" id="userName"/>

        <label for="password">Password</label>
        <input type="password" name="password" id="password"/>

        <input type="submit" value="Login"/>
        <% if (!msg.isEmpty()) { %>
            <div class="error-message"><%= msg %></div>
        <% } %>
    </form>
    <% } %>
</body>
</html>
