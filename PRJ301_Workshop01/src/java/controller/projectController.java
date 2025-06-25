/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import model.ProjectDAO;
import model.ProjectDTO;
import util.AuthUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "projectController", urlPatterns = {"/projectController"})
public class projectController extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        System.out.println(action);
        String url = "";
        try{
        if (action.equals("register")) {
            url = handleRegister(request, response);
        } else if (action.equals("update")) {
            url = handleUpdate(request, response);
        } else if (action.equals("searchName")) {
            url = handleSearchName(request, response);
        }else if(action.equals("showAll")){
            url = handleDisplayAllProject(request,response);
        }
        }catch(Exception e){
            
        }finally{
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    ProjectDAO pdao = new ProjectDAO();

    
    
    private String handleRegister(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";
        String projectName = request.getParameter("projectName");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String estimated_launch = request.getParameter("estimated_launch");

        Date date = null;
        try {
            date = Date.valueOf(estimated_launch);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            checkError += "<br/> " + e;
        }
        if (!date.before(date)) {
            checkError += "<br/> date must be not before";
        }
        ProjectDTO project = new ProjectDTO(0, projectName, description, status, date);
        if (!pdao.createProject(project)) {
            checkError += "<br/> can not add new project.";
        }

        if (checkError.isEmpty()) {
            message = "add project successfully";
        }
        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);
        request.setAttribute("project", project);
        return "projectForm.jsp";

    }

    private String handleUpdate(HttpServletRequest request, HttpServletResponse response) {
         System.out.println("vanChua");
        if(AuthUtils.isFounder(request)){
             int id = Integer.parseInt(request.getParameter("id"));
             String newStatus = request.getParameter("newStatus");
             System.out.println("saproi");
            if(pdao.update(id,newStatus)){
                request.setAttribute("isUpdate", true);
                System.out.println("duocNe");
            }
        }
        System.out.println("kophai");
        return "projectForm.jsp";
        
    }

    private String handleDisplayAllProject(HttpServletRequest request, HttpServletResponse response) {
        if(AuthUtils.isFounder(request)){
            System.out.println("ok1");
            List<ProjectDTO> list = pdao.getAllProject();
            request.setAttribute("list", list);
            System.out.println("ok2");
        }
        return "welcome.jsp";
    }

    private String handleSearchName(HttpServletRequest request, HttpServletResponse response) {
        if(AuthUtils.isFounder(request)){
            String keyword = request.getParameter("keyword");
            List<ProjectDTO> list = pdao.getProjectByName(keyword);
            System.out.println(list);
            request.setAttribute("list", list);
            request.setAttribute("keyword", keyword);
        }
        return "welcome.jsp";
    }

}
