<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.UserDTO" %>
<%@page import="Utils.AuthUtils" %>
<%@page import="Model.ExamCategoriesDTO" %>
<%@page import="Model.ExamsDTO" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome Page</title>
    <link rel="stylesheet" href="assets/welcome.css">
</head>
<body>
<%
    if(AuthUtils.isLoggin(request)) {
        UserDTO user = AuthUtils.getCurrentUser(request);
        String action = request.getParameter("action");
%>
    <h1>Welcome <%= user.getName() %>!</h1>
    <hr>
    <a href="MainController?action=logout">Logout</a> |
    <a href="MainController?action=showAll">Show Categories</a>

    <br/><br/>

    <%
        List list = null;
        if("showAll".equals(action)){
            list = (List<ExamCategoriesDTO>) session.getAttribute("list");
        } else if("filterAndDisplay".equals(action)){
            list = (List<ExamsDTO>) session.getAttribute("list");
        }

        if (list != null && list.isEmpty()) {
    %>
        <p>No Exam Categories match with the keyword.</p>
    <%
        } else if (list != null && !list.isEmpty()) {
    %>
        <table>
            <thead>
                <tr>
                    <% if ("filterAndDisplay".equals(action)) { %>
                        <th>Title</th>
                        <th>Subject</th>
                        <th>Total Marks</th>
                        <th>Duration</th>
                        <th>Action</th>
                    <% } else { %>
                        <th>Category Name</th>
                        <th>Description</th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
                <% for(Object obj : list) {
                    if("filterAndDisplay".equals(action)) {
                        ExamsDTO exam = (ExamsDTO) obj;
                %>
                    <tr>
                        <td><%= exam.getExam_title() %></td>
                        <td><%= exam.getSubject() %></td>
                        <td><%= exam.getTotal_marks() %></td>
                        <td><%= exam.getDuration() %></td>
                        <td>
                            <form action="MainController" method="post" style="margin:0;">
                                <input type="hidden" name="exam_id" value="<%= exam.getExam_id() %>"/>
                                <% if (AuthUtils.isInstructor(request)) { %>
                                    <input type="hidden" name="action" value="forwardAdding"/>
                                    <input type="submit" value="Add Question"/>
                                <% } else if (AuthUtils.isStudent(request)) { %>
                                    <input type="hidden" name="action" value="forwardTakeExam"/>
                                    <input type="submit" value="Take Exam"/>
                                <% } %>
                            </form>
                        </td>
                    </tr>
                <% 
                    } else {
                        ExamCategoriesDTO cat = (ExamCategoriesDTO) obj;
                %>
                    <tr>
                        <td><%= cat.getCategory_name() %></td>
                        <td><%= cat.getDescription() %></td>
                    </tr>
                <% } } %>
            </tbody>
        </table>
    <% } %>

    <br/>
    <form action="MainController" method="post">
        <input type="hidden" name="action" value="filterAndDisplay"/>
        <label for="category">Choose an exam category:</label>
        <select name="category_name" id="category">
            <option value="Quiz">Quiz</option>
            <option value="Midterm">Midterm</option>
            <option value="Final">Final</option>
        </select>
        <input type="submit" value="View Exams"/>
    </form>

<% } else {
    response.sendRedirect("login.jsp");
} %>
</body>
</html>
