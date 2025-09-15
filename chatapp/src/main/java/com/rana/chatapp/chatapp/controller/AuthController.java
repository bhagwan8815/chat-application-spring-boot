package com.rana.chatapp.chatapp.controller;

import com.rana.chatapp.chatapp.DTO.LoginRequestDTO;
import com.rana.chatapp.chatapp.DTO.LoginResponseDTO;
import com.rana.chatapp.chatapp.DTO.RegisterRequestDTO;
import com.rana.chatapp.chatapp.DTO.UserDTO;
import com.rana.chatapp.chatapp.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;


   @PostMapping("/signup")
   public ResponseEntity<UserDTO> signup(@RequestBody RegisterRequestDTO registerRequestDTO){
        return ResponseEntity.ok(authenticationService.signup(registerRequestDTO));
    }

    @PostMapping("/login") public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        LoginResponseDTO loginResponseDTO = authenticationService.login(loginRequestDTO);

        ResponseCookie responseCookie  = ResponseCookie.from("JWT" , loginResponseDTO.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(1*60*60)
                .sameSite("strict")
                .build();


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE , responseCookie.toString())
                .body(loginResponseDTO.getUserDTO());


    }


     @PostMapping("/logout")
     public ResponseEntity<String> logout(){
        return authenticationService.logout();
     }













}

//1.34
