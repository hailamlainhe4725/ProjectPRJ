/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.security.auth.message.callback.PrivateKeyCallback;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DbUtils;

/**
 *
 * @author admin
 */
public class UserDAO {

    

    public UserDTO getUserByUserName(String userNameID) {
        UserDTO user = null;
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement pres = conn.prepareStatement("SELECT * FROM tblUsers WHERE Username = ?");
            pres.setString(1, userNameID);
            ResultSet rs = pres.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("userName");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String role = rs.getString("role");

                user = new UserDTO(userName, name, password, role);
            }

        } catch (Exception ex) {
            System.out.println("hi" + ex);
        }
        return user;
    }
    
    public boolean login(String userName, String password){
        System.out.println("day la ten:"+userName);
        System.out.println("day la pass:"+password);
        UserDTO user = getUserByUserName(userName);
        if(user!= null){
            if(user.getPassword().equals(password)){
                System.out.println(user);
                return true;
            }
        }
        return false;
    }
    
    public static List<UserDTO> getAllUser(){
        List<UserDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        UserDTO user = null;
        try {
            conn = DbUtils.getConnection();
            prs = conn.prepareStatement("SELECT Username,Name,Password,Role FROM tblUsers");
            rs = prs.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("Username");
                String name = rs.getString("Name");
                String password = rs.getString("Password");
                String role = rs.getString("Role");

                user = new UserDTO(userName, name, password, role);
                list.add(user);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            closeResources(conn, prs, rs);
        }
        return list;
    }
    
    public boolean updatePassword(String userID, String newPassword) {
        String sql = "UPDATE tblUsers SET Password = ? WHERE Username = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, userID);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
