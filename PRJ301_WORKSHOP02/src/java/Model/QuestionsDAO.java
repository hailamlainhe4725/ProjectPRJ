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
public class QuestionsDAO {

    public static boolean addques(QuestionsDTO ques) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            prs = conn.prepareStatement("INSERT INTO tblQuestions (exam_id,question_text ,option_a ,option_b ,option_c ,option_d ,correct_option) VALUES (?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            System.out.println(ques);
            prs.setInt(1,ques.getExam_id());
            prs.setString(2, ques.getQuestion_text());
            prs.setString(3, ques.getOption_a());
            prs.setString(4, ques.getOption_b());
            prs.setString(5, ques.getOption_c());
            prs.setString(6, ques.getOption_d());
            prs.setString(7, ques.getCorrect_option());
            
            int count = prs.executeUpdate();
            System.out.println(count);
             rs = prs.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                ques.setQuestion_id(generatedId);
            }
            success= (count>0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            closeResources(conn, prs, rs);
        }
        return success;
    }
    
    public static List<QuestionsDTO> deThi(int exam_id){
        List<QuestionsDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        QuestionsDTO q = null;
        try {
            conn = DbUtils.getConnection();
            prs = conn.prepareStatement("SELECT question_id,question_text ,option_a ,option_b ,option_c ,option_d ,correct_option FROM tblQuestions WHERE exam_id = ?");
            prs.setInt(1,exam_id);
            rs = prs.executeQuery();
            while(rs.next()){
                int question_id = rs.getInt("question_id");
                String question_text = rs.getString("question_text");
                String option_a = rs.getString("option_a");
                String option_b = rs.getString("option_b");
                String option_c = rs.getString("option_c");
                String option_d = rs.getString("option_d");
                String correct_option = rs.getString("correct_option");
            
                q = new QuestionsDTO(question_id, exam_id, question_text, option_a, option_b, option_c, option_d, correct_option);
                list.add(q);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            closeResources(conn, prs, rs);
        }
        return list;
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
