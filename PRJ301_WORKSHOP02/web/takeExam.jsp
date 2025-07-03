<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.QuestionsDTO" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Take Exam</title>
    <link rel="stylesheet" href="assets/takeExam.css">
</head>
<body>

<%
    List<QuestionsDTO> list = (List<QuestionsDTO>) session.getAttribute("list");
    Integer count = (Integer) request.getAttribute("count");
    int diem = (count != null) ? count : -1;
%>

<h2>Exam</h2>
<form action="MainController" method="post">
    <input type="hidden" name="action" value="submitExam"/>
    
    <table>
        <thead>
            <tr>
                <th>Question</th>
                <th>Your Answer</th>
            </tr>
        </thead>
        <tbody>
        <%
            for(QuestionsDTO q : list) {
        %>
            <tr>
                <td><%= q.getQuestion_text() %></td>
                <td>
                    <select name="banChon_<%= q.getQuestion_id() %>">
                        <option value="A"><%= q.getOption_a() %></option>
                        <option value="B"><%= q.getOption_b() %></option>
                        <option value="C"><%= q.getOption_c() %></option>
                        <option value="D"><%= q.getOption_d() %></option>
                    </select>
                </td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <br/>
    <input type="submit" value="Submit"/>
</form>

<% if (diem != -1) { %>
    <h2>Your Score: <%= diem  %></h2>
<% } %>

</body>
</html>
