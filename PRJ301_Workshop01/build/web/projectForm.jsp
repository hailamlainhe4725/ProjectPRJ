<%-- 
    Document   : projectForm
    Created on : Jun 22, 2025, 8:21:53 PM
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
        <h1>Project form</h1>
        <%
            Boolean isUpdateAttr = (Boolean) request.getAttribute("isUpdate");
    boolean isUpdate = (isUpdateAttr != null) ? isUpdateAttr : false;
    if (!isUpdate) {

        %>
        <form action="mainController" method="post">
            <input type="hidden" name ="action" value="register"/>
            <div>   
                <label for ="projectName">name</label>
                <input type="text" name ="projectName"/> 
            </div>

            <div>
                <label for="description">description</label>
                <textarea id = "description" name="description">
                    
                </textarea>
            </div>

            <div>
                <label for="status">status</label>
                <select name="status">
                    <option value="Ideation">Ideation</option>
                    <option value="Development">Development</option>
                    <option value="Launch">Launch</option>
                    <option value="Scaling">Scaling</option>
                </select>
            </div>

            <div>
                <lable name="estimated_launch">date</lable>
                <input type="date" name="estimated_launch"/>
            </div>

            <div>
                <input type="submit" value="addProject"/>
            </div>
        </form>
        <%}else{%>
        <%="update succesfully"%>
        <%}%>
    </body>
</html>
