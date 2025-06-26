<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="util.AuthUtils" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Project Form</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            display: flex;
            justify-content: center;
            padding-top: 50px;
        }

        form {
            background: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            width: 400px;
        }

        div {
            margin-bottom: 15px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        input[type="text"],
        input[type="date"],
        select,
        textarea {
            width: 100%;
            padding: 10px;
            border-radius: 6px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px;
            width: 100%;
            border-radius: 6px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #218838;
        }

        .message {
            text-align: center;
            color: green;
            font-weight: bold;
        }
    </style>
</head>
<body>
<%
    Boolean isUpdateAttr = (Boolean) request.getAttribute("isUpdate");
    boolean isUpdate = (isUpdateAttr != null) ? isUpdateAttr : false;

    if (!isUpdate && AuthUtils.isFounder(request) ) {
%>
    <form action="mainController" method="post">
        <input type="hidden" name="action" value="register"/>

        <div>
            <label for="projectName">Project Name</label>
            <input type="text" id="projectName" name="projectName"/>
        </div>

        <div>
            <label for="description">Description</label>
            <textarea id="description" name="description" rows="4"></textarea>
        </div>

        <div>
            <label for="status">Status</label>
            <select id="status" name="status">
                <option value="Ideation">Ideation</option>
                <option value="Development">Development</option>
                <option value="Launch">Launch</option>
                <option value="Scaling">Scaling</option>
            </select>
        </div>

        <div>
            <label for="estimated_launch">Estimated Launch</label>
            <input type="date" id="estimated_launch" name="estimated_launch"/>
        </div>

        <div>
            <input type="submit" value="Add Project"/>
        </div>
    </form>
<% } else if (isUpdate) { %>
    <div class="message">Update successfully</div>
    <a href="mainController?action=login">backTowelcome</a>
    <% } %>
</body>
</html>
