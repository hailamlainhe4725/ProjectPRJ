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
        <title>JSP Page</title>
    </head>
    <body>
        <%
        if(AuthUtils.isLoggin(request)){
         UserDTO user = AuthUtils.getCurrentUser(request);
         String keyword = (String)request.getParameter("keyword");
        %>
        <h1>Welcome <%= user.getName() %> ! </h1>
        <a href="mainController?action=logout">Logout</a>

        <hr/>
        <form action="mainController" method="post">
            <input type="hidden" name="action" value="searchName"/>
            Search product by name:
            <input type="text" name="keyword" value="<%=keyword!=null?keyword:""%>"/>
            <input type="submit" value="Search"/>
        </form>
        <a href="mainController?action=showAll">showAll</a>
        <br/>  
        <%    
            List<ProjectDTO> list =(List<ProjectDTO>) request.getAttribute("list");
            if(list!=null && list.isEmpty()){
        %>
        No products have name match with the keyword.
        <%= "kk"%>
        <% }else if(list!= null && !list.isEmpty()){
        %>
        <table>
            <thead>
            <th>Id</th>
            <th>Name</th>
            <th>Description</th>
            <th>Status</th>
            <th>Time</th>
            <th>Update Status</th>
        </thead>
        <tbody>
            <% for(ProjectDTO p:list) {%>
            <tr>
                <td><%=p.getProject_id()%></td>
                <td><%=p.getProject_name()%></td>
                <td><%=p.getDescription()%></td>
                <td><%=p.getStatus()%></td>
                <td><%=p.getEstimated_launch()%></td>
                <td>
                    <form action="mainController" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type ="hidden" name="id" value="<%=p.getProject_id()%>"/>
                        <select name="newStatus">
                            <option value="Ideation">Ideation</option>
                            <option value="Development">Development</option>
                            <option value="Launch">Launch</option>
                            <option value="Scaling">Scaling</option>
                        </select>
                        <input type="submit" value="update_status"/>
                    </form>
                </td>
            </tr>
            <%}%>
        </tbody>
    </table>
    <%
    }
    %>
    <%} else { %>
    <%=AuthUtils.getAccessDeniedMessage("welcome.jsp")%> <br/>
    <%= "cc" %>
    (Or <a href="<%=AuthUtils.getLoginURL()%>">Login</a>)
    <%}%>

</body>
</html>
