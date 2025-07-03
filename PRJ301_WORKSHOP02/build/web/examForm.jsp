<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.ExamsDTO" %>
<%@page import="Utils.AuthUtils" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exam Form</title>
    <link rel="stylesheet" href="assets/examForm.css">
</head>
<body>

<%
    String checkError = (String) request.getAttribute("checkError");
    String message = (String) request.getAttribute("message");
    ExamsDTO es = (ExamsDTO) session.getAttribute("es");
    Integer exam_idObj = (Integer) request.getAttribute("exam_id");
    int exam_id = (exam_idObj != null) ? exam_idObj : -1;
    String action = (String) request.getAttribute("action");
%>

<% if (AuthUtils.isInstructor(request)) { %>
    <h2>Create New Exam</h2>
    <form action="MainController" method="post">
        <input type="hidden" name="action" value="createExam"/>

        <label>Exam Title:</label>
        <input type="text" name="exam_title" value="<%= (es != null) ? es.getExam_title() : "" %>"/><br/>

        <label>Subject:</label>
        <input type="text" name="Subject" value="<%= (es != null) ? es.getSubject() : "" %>"/><br/>

        <label>Category:</label>
        <select name="category_id">
            <option value="1">Quiz</option>
            <option value="2">Midterm</option>
            <option value="3">Final</option>
        </select><br/>

        <label>Total Marks:</label>
        <input type="text" name="total_marks"/><br/>

        <label>Duration (minutes):</label>
        <input type="text" name="Duration"/><br/>

        <input type="submit" value="Create Exam"/>
    </form>
    
<% } %>

<% if ("forwardAdding".equals(action) || "addQuestion".equals(action)) { %>
    <h2>Add Question to Exam #<%= exam_id %></h2>
    <form action="MainController" method="post">
        <input type="hidden" name="action" value="addQuestion"/>
        <input type="hidden" name="exam_id" value="<%= exam_id %>"/>

        <label>Question:</label>
        <input type="text" name="question_text"/><br/>

        <label>Option A:</label>
        <input type="text" name="option_a"/><br/>

        <label>Option B:</label>
        <input type="text" name="option_b"/><br/>

        <label>Option C:</label>
        <input type="text" name="option_c"/><br/>

        <label>Option D:</label>
        <input type="text" name="option_d"/><br/>

        <label>Correct Option:</label>
        <input type="text" name="correct_option"/><br/>

        <input type="submit" value="Add Question"/>
    </form>
    
<% } %>
<p style="color:red"><%= (checkError != null) ? checkError : "" %></p>
    <p style="color:green"><%= (message != null) ? message : "" %></p>

</body>
</html>
