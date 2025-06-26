<%-- 
    Document   : welcome
    Created on : Jun 21, 2025, 6:12:59 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="util.AuthUtils" %>
<%@page import="model.UserDTO" %>
<%@page import="java.util.List" %>
<%@page import="model.ProjectDTO" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 20px;
            color: #333;
        }

        h1 {
            color: #2c3e50;
        }

        a {
            text-decoration: none;
            color: #2980b9;
            margin-right: 10px;
        }

        form {
            margin-top: 15px;
            margin-bottom: 15px;
        }

        input[type="text"] {
            padding: 5px;
            width: 200px;
        }

        input[type="submit"] {
            background-color: #27ae60;
            color: white;
            border: none;
            padding: 6px 12px;
            cursor: pointer;
            border-radius: 4px;
        }

        input[type="submit"]:hover {
            background-color: #219150;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
        }

        th {
            background-color: #ecf0f1;
            text-align: left;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        select {
            padding: 5px;
        }

        .no-results {
            color: red;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<%
    if (AuthUtils.isLoggin(request)) {
        UserDTO user = AuthUtils.getCurrentUser(request);
        String keyword = (String) request.getParameter("keyword");
        boolean isFounder = AuthUtils.isFounder(request);
%>
    <h1>Welcome <%= user.getName() %>!</h1>
    <a href="mainController?action=logout">Logout</a>
    <hr/>

    <% if (isFounder) { %>
        <form action="mainController" method="post">
            <input type="hidden" name="action" value="searchName"/>
            Search project by name:
            <input type="text" name="keyword" value="<%= keyword != null ? keyword : "" %>"/>
            <input type="submit" value="Search"/>
        </form>
    <% } %>

    <a href="mainController?action=showAll">Show All</a>

<%
    List<ProjectDTO> list = (List<ProjectDTO>) request.getAttribute("list");
    if (list != null && list.isEmpty()) {
%>
    <div class="no-results">No products match the keyword.</div>
<%
    } else if (list != null && !list.isEmpty()) {
%>
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Status</th>
                <th>Time</th>
                <% if (isFounder) { %>
                <th>Update Status</th>
                <% } %>
            </tr>
        </thead>
        <tbody>
        <% for (ProjectDTO p : list) { %>
            <tr>
                <td><%= p.getProject_name() %></td>
                <td><%= p.getDescription() %></td>
                <td><%= p.getStatus() %></td>
                <td><%= p.getEstimated_launch() %></td>
                <% if (isFounder) { %>
                <td>
                    <form action="mainController" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="<%= p.getProject_id() %>"/>
                        <select name="newStatus">
                            <option value="Ideation">Ideation</option>
                            <option value="Development">Development</option>
                            <option value="Launch">Launch</option>
                            <option value="Scaling">Scaling</option>
                        </select>
                        <input type="submit" value="Update"/>
                    </form>
                </td>
                <% } %>
            </tr>
        <% } %>
        </tbody>
    </table>
<%
    }
%>
<% } else { %>
    <%= AuthUtils.getAccessDeniedMessage("welcome.jsp") %> <br/>
    (Or <a href="<%= AuthUtils.getLoginURL() %>">Login</a>)
<% } %>
</body>
</html>
