/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package MainController;

import Model.ExamCategoriesDAO;
import Model.ExamCategoriesDTO;
import Model.ExamsDAO;
import Model.ExamsDTO;
import Model.QuestionsDAO;
import Model.QuestionsDTO;
import Utils.AuthUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author admin
 */
@WebServlet(name = "ExamController", urlPatterns = {"/ExamController"})
public class ExamController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "";
        String action = (String) request.getParameter("action");
        System.out.println(action);
        try {
            if (action.equals("showAll")) {
                url = handleShowAll(request, response);
            } else if (action.equals("filterAndDisplay")) {
                url = handleFilterAndDisplay(request, response);
            } else if (action.equals("createExam")) {
                url = handleCreate(request, response);
            } else if (action.equals("forwardAdding")) {
                url = handleForward(request, response);
            } else if (action.equals("addQuestion")) {
                url = handleAdd(request, response);
            } else if (action.equals("forwardTakeExam")) {
                url = handleForwardTE(request, response);
            } else if (action.equals("submitExam")) {
                url = handleCheckDiem(request, response);
            }
        } catch (Exception e) {
        } finally {
            System.out.println("lan2" + action);
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

    private String handleShowAll(HttpServletRequest request, HttpServletResponse response) {
        ExamCategoriesDAO edao = new ExamCategoriesDAO();
        List<ExamCategoriesDTO> list = edao.getAll();
        System.out.println(list);
        HttpSession session = request.getSession();
        session.setAttribute("list", list);

        System.out.println((List<ExamCategoriesDTO>) session.getAttribute("list"));
        return "welcome.jsp";
    }

    private String handleFilterAndDisplay(HttpServletRequest request, HttpServletResponse response) {
        String category_name = request.getParameter("category_name");
        ExamsDAO exdao = new ExamsDAO();
        List<ExamsDTO> list = exdao.display(category_name);
        HttpSession session = request.getSession();
        session.setAttribute("list", list);
        return "welcome.jsp";
    }

    private String handleCreate(HttpServletRequest request, HttpServletResponse response) {

        String checkError = "";
        String message = "";
        if (AuthUtils.isInstructor(request)) {
            String exam_title = request.getParameter("exam_title");
            String Subject = request.getParameter("Subject");
            int category_id = Integer.parseInt(request.getParameter("category_id"));
            int total_marks = Integer.parseInt(request.getParameter("total_marks"));
            int Duration = Integer.parseInt(request.getParameter("Duration"));
            ExamsDAO esdao = new ExamsDAO();

            //bat loi
            if (exam_title == null || !exam_title.matches("^[a-zA-Z0-9\\s\\-_]+$")) {
                checkError += "<br/>Exam Title  must only contain letters, numbers, spaces, hyphens, and underscores.";
            }
            if (Subject == null || !Subject.matches("^[a-zA-Z0-9\\s\\-_]+$")) {
                checkError += "<br/>Subject  must only contain letters, numbers, spaces, hyphens, and underscores.";
            }
            if (category_id < 1 || category_id > 3) {
                checkError += "<br/>category invalid.";
            }

            if (total_marks < 0) {
                checkError += "<br/>total mark must > 0.";
            }
            if (Duration < 0) {
                checkError += "<br/>Duration must > 0.";
            }
            ExamsDTO es = null;
            if (checkError.isEmpty()) {
                es = new ExamsDTO(0, exam_title, Subject, category_id, total_marks, Duration);
                if (!esdao.create(es)) {
                    checkError += "<br/>Could not add new  to database.";
                } else {
                    message = "Add successfully.";
                    request.setAttribute("es", es);
                }
            }
        } else {
            checkError += "ko phai ins";
        }

        request.setAttribute("checkError", checkError);
        System.out.println(checkError);
        request.setAttribute("message", message);

        return "examForm.jsp";
    }

    private String handleForward(HttpServletRequest request, HttpServletResponse response) {
        if(AuthUtils.isInstructor(request)){
        int exam_id = Integer.parseInt(request.getParameter("exam_id"));
        request.setAttribute("exam_id", exam_id);
        String action = request.getParameter("action");
        request.setAttribute("action", action);}
        return "examForm.jsp";
    }

    private String handleAdd(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";
        if(AuthUtils.isInstructor(request)){
        int exam_id = Integer.parseInt(request.getParameter("exam_id"));
        String question_text = request.getParameter("question_text");
        String option_a = request.getParameter("option_a");
        String option_b = request.getParameter("option_b");
        String option_c = request.getParameter("option_c");
        String option_d = request.getParameter("option_d");
        String correct_option = request.getParameter("correct_option").toUpperCase();
        QuestionsDTO ques = null;
        QuestionsDAO quesdao = new QuestionsDAO();
        //bat loi
        if (question_text != null && question_text.length() > 255) {
            checkError += "<br/>Description must be less than 255 characters.";
        }
        if (option_a == null) {
            checkError += "<br/>option must be filled.";
        }
        if (option_b == null) {
            checkError += "<br/>option must be filled.";
        }
        if (option_c == null) {
            checkError += "<br/>option must be filled.";
        }
        if (option_d == null) {
            checkError += "<br/>option must be filled.";
        }
        if (!correct_option.matches("^[A-D]$")) {
            checkError += "<br/> invalid";
        }
        if (checkError.isEmpty()) {
            ques = new QuestionsDTO(0, exam_id, question_text, option_a, option_b, option_c, option_d, correct_option);
            if (!quesdao.addques(ques)) {
                checkError += "<br/>Could not add new  to database.";
            } else {
                message = "Add successfully.";
                        String action = request.getParameter("action");
        request.setAttribute("action", action);
            }
        }
    }else{
        checkError +="not ins";
        }
    

        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);
        return "examForm.jsp";
    }

    private String handleForwardTE(HttpServletRequest request, HttpServletResponse response) {
        if(AuthUtils.isStudent(request)){
        int exam_id = Integer.parseInt(request.getParameter("exam_id"));
        request.setAttribute("exam_id", exam_id);
        QuestionsDAO quesdao = new QuestionsDAO();
        List<QuestionsDTO> list = quesdao.deThi(exam_id);
        HttpSession session = request.getSession();
        session.setAttribute("list", list);
        String action = request.getParameter("action");
        request.setAttribute("action", action);
        }
        return "takeExam.jsp";
    }

    private String handleCheckDiem(HttpServletRequest request, HttpServletResponse response) {
        if(AuthUtils.isStudent(request)){
        HttpSession session = request.getSession();
        List<QuestionsDTO> list = (List<QuestionsDTO>) session.getAttribute("list");
        int count = 0;
        for (QuestionsDTO q : list) {
            String repThayDoi = "banChon_" + q.getQuestion_id();
            String banChon = request.getParameter(repThayDoi);
            if (banChon.equals(q.getCorrect_option())) {
                count++;
            }
        }
        request.setAttribute("count", count);
        }
        return "takeExam.jsp";
    }
}
