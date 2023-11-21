package io.javabrains.SpringBootSecurity.UserService;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CustomUser {

    @Id
    private int id;
    public int getId() {
        return id;
    }
    public CustomUser(int i, String username, String password, String roles) {
        this.id = i;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRoles() {
        return roles;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }
    private String username;
    private String password;
    private String roles; // Assuming roles as a comma-separated string
    public CustomUser orElseThrow(Object object) {
        return null;
    }

    // Getters and setters
}

