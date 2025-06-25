/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.DbUtils;
import model.UserDTO;

/**
 *
 * @author admin
 */
public class ProjectDAO {

    public static List<ProjectDTO> getAllProject() {
       List<ProjectDTO> list = new ArrayList<>();
       Connection conn = null;
       PreparedStatement prs = null;
       ResultSet rs = null;
        try {
            
            conn = DbUtils.getConnection();
            prs = conn.prepareStatement("SELECT project_id,project_name,Description,Status, estimated_launch FROM tblStartupProjects");
            rs = prs.executeQuery();

            while (rs.next()) {
                int project_id =rs.getInt("project_id");
                String project_name = rs.getString("project_name");
                String Description= rs.getString("Description");
                String Status = rs.getString("Status");
                Date estimated_launch = rs.getDate("estimated_launch");
                ProjectDTO project = new ProjectDTO(project_id,project_name, Description, Status, estimated_launch);
                list.add(project);
                System.out.println("ok");
            }
        } catch (Exception e) {
           System.err.println("error in getALL:"+e.getMessage());
           e.printStackTrace();
        }finally{
            closeResources(conn,prs,rs);
        }
        return list;
    }

    public static boolean createProject(ProjectDTO project){
        boolean success = false;
        Connection conn = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        try{
            conn = DbUtils.getConnection();
            prs = conn.prepareStatement("INSERT INTO tblStartupProjects (project_name,Description,Status,estimated_launch) VALUES (?,?,?,?)"
            ,Statement.RETURN_GENERATED_KEYS);
            
           
            prs.setString(1, project.getProject_name());
            prs.setString(2, project.getDescription());
            prs.setString(3, project.getStatus());
            prs.setDate(4, project.getEstimated_launch());
            
            int count = prs.executeUpdate();
             rs = prs.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                project.setProject_id(generatedId);
            }
            success= (count>0);
        }catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }finally{
            closeResources(conn, prs, null);
        }
        return success;
    }
    
    public static List<ProjectDTO> getProjectByName(String projectName){
        List<ProjectDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            prs = conn.prepareStatement("SELECT project_id,project_name,Description,Status, estimated_launch FROM tblStartupProjects WHERE project_name LIKE ?");
            prs.setString(1, "%"+projectName+"%");
            rs = prs.executeQuery();
            while(rs.next()){
                int project_id =rs.getInt("project_id");
                String project_name = rs.getString("project_name");
                String Description= rs.getString("Description");
                String Status = rs.getString("Status");
                Date estimated_launch = rs.getDate("estimated_launch");
                ProjectDTO project = new ProjectDTO(project_id,project_name, Description, Status, estimated_launch);
                list.add(project);
                
                System.out.println(project.toString());
                System.out.println(list);
            }
            System.out.println("xinChaoToiday");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, prs, rs);
        }
        return list;
    }
    
    public static ProjectDTO getProjectByID(int id) {
        ProjectDTO pro = null;
        Connection conn = null;
        PreparedStatement prs = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            prs = conn.prepareStatement("SELECT project_id, project_name,Description,Status,estimated_launch FROM tblStartupProjects WHERE project_id = ? ");
            prs.setInt(1, id);
            rs = prs.executeQuery();

            if (rs.next()) {
                pro = new ProjectDTO();
                pro.setProject_id(rs.getInt("project_id"));
                pro.setProject_name(rs.getString("project_name"));
                pro.setDescription(rs.getString("Description"));
                pro.setStatus(rs.getString("Status"));
                pro.setEstimated_launch(rs.getDate("estimated_launch"));
            }
        } catch (Exception e) {
            System.err.println("Error in getProjectByID(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, prs, rs);
        }

        return pro;
    }
    public static boolean update(int id,String newStatus){
        boolean success = false;
        Connection conn = null;
        PreparedStatement prs = null;
        ProjectDTO project = getProjectByID(id);
        
        
        try {
            if(project==null) throw new Exception("ko co cai project do dau ");
            conn = DbUtils.getConnection();
            prs = conn.prepareStatement("UPDATE tblStartupProjects SET status = ? WHERE project_id = ?");
            prs.setString(1, newStatus);
            prs.setInt(2, project.getProject_id());
            
            int count = prs.executeUpdate();
            success = (count > 0);
            
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, prs, null);
        }
        return success;
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
