/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Utils.DbUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class ExamsDAO {

    public static List<ExamsDTO> display(String categories_name) {
        List<ExamsDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        ExamsDTO var = null;
        try {
            conn = DbUtils.getConnection();
            prs = conn.prepareStatement("SELECT e.exam_id, e.exam_title, e.Subject, e.category_id, e.total_marks, e.Duration FROM dbo.tblExams e INNER JOIN dbo.tblExamCategories c ON e.category_id = c.category_id WHERE  c.category_name = ?;");
            prs.setString(1, categories_name);
            rs = prs.executeQuery();

            while (rs.next()) {
                int exam_id = rs.getInt("exam_id");
                String exam_title = rs.getString("exam_title");
                String Subject = rs.getString("Subject");
                int category_id = rs.getInt("category_id");
                int total_marks = rs.getInt("total_marks");
                int Duration = rs.getInt("Duration");

                var = new ExamsDTO(exam_id, exam_title, Subject, category_id, total_marks, Duration);
                list.add(var);
                System.out.println(var);
                
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            closeResources(conn, prs, rs);
            System.out.println(list);
        }

        return list;
    }
    
    public static boolean create(ExamsDTO exams){
        boolean success = false;
        Connection conn = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            prs = conn.prepareStatement("INSERT INTO tblExams (exam_title ,Subject ,category_id ,total_marks ,Duration) VALUES (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            prs.setString(1, exams.getExam_title());
            prs.setString(2, exams.getSubject());
            prs.setInt(3, exams.getCategory_id());
            prs.setInt(4, exams.getTotal_marks());
            prs.setInt(5, exams.getDuration());
            

            int count = prs.executeUpdate();
             rs = prs.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                exams.setExam_id(generatedId);
            }
            success= (count>0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            closeResources(conn, prs, rs);
        }
        
        return success;
    }


    private static void closeResources(Connection conn, PreparedStatement prs, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.err.println("Error closing resources:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
