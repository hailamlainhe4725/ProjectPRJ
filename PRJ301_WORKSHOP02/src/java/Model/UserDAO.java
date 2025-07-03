/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

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
import Utils.DbUtils;

/**
 *
 * @author admin
 */
public class UserDAO {

    

    public UserDTO getUserByUserName(String usernameid) {
        UserDTO user = null;
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement pres = conn.prepareStatement("SELECT * FROM tblUsers WHERE username = ?");
            pres.setString(1, usernameid);
            ResultSet rs = pres.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String Name = rs.getString("Name");
                String password = rs.getString("password");
                String Role = rs.getString("Role");

                user = new UserDTO(username, Name, password, Role);
            }

        } catch (Exception ex) {
            System.out.println("hi" + ex);
        }
        return user;
    }
    
    public boolean login(String username, String password){
        UserDTO user = getUserByUserName(username);
        if(user!= null){
            if(user.getPassword().equals(password)){
                return true;
                
            }
        }

        return false;
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
