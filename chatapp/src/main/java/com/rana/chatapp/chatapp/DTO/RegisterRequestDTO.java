package com.rana.chatapp.chatapp.DTO;

import lombok.Builder;
import lombok.Data;

@Data
public class RegisterRequestDTO {
private String userName;
private String password;
private String email;


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
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
}
