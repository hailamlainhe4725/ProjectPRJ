/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.util.logging.resources.logging;

/**
 *
 * @author admin
 */
public class DbUtils {
    private static final String DB_NAME = "PRJ301_WORKSHOP01a";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "12345";
    
    public static Connection getConnection() throws ClassNotFoundException,SQLException{
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME;
        conn = DriverManager.getConnection(url,DB_USER,DB_PASSWORD);
        return conn;
    }
    public static void main(String[] args) {
        try {
            System.out.println(getConnection());
        } catch (ClassNotFoundException e) {
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE, null, e);
        }catch(SQLException e){
            Logger.getLogger(DbUtils.class.getName()).log(Level.SEVERE,null, e);
        }
    }
}
