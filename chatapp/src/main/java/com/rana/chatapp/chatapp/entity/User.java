package com.rana.chatapp.chatapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
//@Data
//@AllArgsConstructor     // generates constructor with all fields
//@NoArgsConstructor      // generates default constructor
//@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


public void setId(Long id){
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
