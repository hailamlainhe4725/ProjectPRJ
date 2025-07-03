/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Utils.DbUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ExamCategoriesDAO {
    public static List<ExamCategoriesDTO> getAll(){
        Connection conn = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        ExamCategoriesDTO var = null;
        List<ExamCategoriesDTO> a = new ArrayList<>();
        try {
            conn = DbUtils.getConnection();
            prs = conn.prepareStatement("SELECT category_id, category_name,description FROM tblExamCategories");
            rs =prs.executeQuery();
            
            while(rs.next()){
                int category_id = rs.getInt("category_id");
                String category_name = rs.getString("category_name");
                String description = rs.getString("description");
                
                var = new ExamCategoriesDTO(category_id, category_name, description);
                a.add(var);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            closeResources(conn,prs,rs);
        }
        return a;
    }


    private static void closeResources(Connection conn, PreparedStatement prs, ResultSet rs) {
       try{
        if(rs!=null){
            rs.close();
        }
        if(prs!=null){
            prs.close();
        }
        if(conn != null){
            conn.close();
        }
       }catch(Exception e){
           System.err.println("Error closing resources:" +e.getMessage());
           e.printStackTrace();
       }
    }
}
