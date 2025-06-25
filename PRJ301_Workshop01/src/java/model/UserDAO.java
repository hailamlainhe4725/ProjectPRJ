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
}
