/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import Utils.DbUtils;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class userDAO {
    public static final String CREATE_USER = "INSERT INTO tblUsers (userID ,fullName, password,phoneNumber, roleID, status) VALUES (? , ? , ? , ? ,? ,?)";
    public static userDTO getUserByID(String id) {
        userDTO user = null;
        try {
            String sql = "SELECT * FROM tblUser Where userID = ?";
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement("");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String userID = rs.getString("userID");
                String fullName = rs.getString("fullName");
                String password = rs.getString("password");
                String phoneNumber = rs.getString("phoneNumber");
                String roleID = rs.getString("roleID");
                boolean status = rs.getBoolean("status");

                user = new userDTO(userID, fullName, password, phoneNumber, roleID, status);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
    public static boolean login (String userID,String password){
        userDTO user = getUserByID(userID);
        if(user != null){
            if(password.equals(user.getPassword())){
                if(user.isStatus()){
                    return true;
                }
            }
        }
        return false;
        
    }
    
    public static boolean create(userDTO user){
        boolean success = false;
        try {
            Connection conn = null;
            PreparedStatement ps = null;
            
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_USER);
            ps.setString(1, user.getUserID());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getRoleID());
            ps.setBoolean(6, user.isStatus());
            
            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected>0);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
        
    }
}
