/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class UserDTO {
    private String username;
    private String Name;
    private String password;
    private String Role;

    public UserDTO() {
    }

    public UserDTO(String username, String Name, String password, String Role) {
        this.username = username;
        this.Name = Name;
        this.password = password;
        this.Role = Role;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "username=" + username + ", Name=" + Name + ", password=" + password + ", Role=" + Role + '}';
    }

    
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }
    
    
}
