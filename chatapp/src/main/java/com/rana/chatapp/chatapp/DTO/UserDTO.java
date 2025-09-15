package com.rana.chatapp.chatapp.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

}
