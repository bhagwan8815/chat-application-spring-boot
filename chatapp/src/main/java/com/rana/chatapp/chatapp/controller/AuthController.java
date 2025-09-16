package com.rana.chatapp.chatapp.controller;

import com.rana.chatapp.chatapp.DTO.LoginRequestDTO;
import com.rana.chatapp.chatapp.DTO.LoginResponseDTO;
import com.rana.chatapp.chatapp.DTO.RegisterRequestDTO;
import com.rana.chatapp.chatapp.DTO.UserDTO;
import com.rana.chatapp.chatapp.entity.User;
import com.rana.chatapp.chatapp.repository.UserRepository;
import com.rana.chatapp.chatapp.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;


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



    @GetMapping("/getcurrentuser") public ResponseEntity<?> getCurrentUser(Authentication authentication){
       if(authentication==null){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not Authorized");
       }

       String userName =  authentication.getName();
       User user = userRepository.findByUserName(userName).orElseThrow(()-> new RuntimeException("USer not found ."));

       return ResponseEntity.ok(convertToUserDTO(user));
    }


    public UserDTO convertToUserDTO(User user){
       UserDTO userDTO = new UserDTO();

       userDTO.setId(user.getId());
       userDTO.setUserName(user.getUserName());
       userDTO.setEmail(user.getEmail());

       return userDTO;
    }











}

//1.34
