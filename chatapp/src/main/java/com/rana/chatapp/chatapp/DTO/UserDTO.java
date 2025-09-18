package com.rana.chatapp.chatapp.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
public class UserDTO {
    private Long id ;
    @Column(unique = true , nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false , unique = true)
    private String email;
    @Column(nullable = false , name="is_online")
    private boolean isOnline;


    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
