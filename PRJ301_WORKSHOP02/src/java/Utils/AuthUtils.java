/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import Model.UserDTO;

/**
 *
 * @author admin
 */
public class AuthUtils {
    
    public static UserDTO getCurrentUser(HttpServletRequest request){
    HttpSession session = request.getSession();
    if(session!=null){
        UserDTO user = (UserDTO) session.getAttribute("user");
        return (UserDTO) session.getAttribute("user");
    }
        return null;
    }
    
    public static boolean isLoggin(HttpServletRequest request){
        return getCurrentUser(request)!=null;
    }
    
    
    public static boolean hasRole(HttpServletRequest request, String role){
        UserDTO user = getCurrentUser(request);
        if(user!=null){
            return  user.getRole().equals(role);
        }
        return false;
    }
    
    public static boolean isInstructor(HttpServletRequest request){
        return hasRole(request,"Instructor");
    }
    
    public static boolean isStudent(HttpServletRequest request){
        return hasRole(request,"Student");
    }
    
    public static  String getLoginURL(){
        return "mainController";
    }
    
    public static String getAccessDeniedMessage(String action){
        return "You can not access to " + action+".please contact adminstator.";
    }
    
    
    
}
