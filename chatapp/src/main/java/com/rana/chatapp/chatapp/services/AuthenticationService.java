package com.rana.chatapp.chatapp.services;

import com.rana.chatapp.chatapp.DTO.LoginRequestDTO;
import com.rana.chatapp.chatapp.DTO.LoginResponseDTO;
import com.rana.chatapp.chatapp.DTO.RegisterRequestDTO;
import com.rana.chatapp.chatapp.DTO.UserDTO;
import com.rana.chatapp.chatapp.JWT.JwtService;
import com.rana.chatapp.chatapp.entity.User;
import com.rana.chatapp.chatapp.repository.UserRepository;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    public UserDTO signup(RegisterRequestDTO registerRequestDTO){
        if(userRepository.findByUserName(registerRequestDTO.getUserName()).isPresent()){
            throw new RuntimeException("USer is already registed with this email ");
        }

        User user = new User();
        user.setUserName(registerRequestDTO.getUserName());
        user.setPassword(registerRequestDTO.getPassword());
        user.setEmail(registerRequestDTO.getEmail());
        User savedUser = userRepository.save(user);
        return convertToUserDTO(savedUser);
    }


    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        User user = userRepository.findByUserName(loginRequestDTO.getUserName()).orElseThrow(()-> new RuntimeException("USername is not found "));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserName() , loginRequestDTO.getPassword()));

        String jwtToken = jwtService.generateToken(user);

        return LoginResponseDTO.builder()
                .token(jwtToken)
                .userDTO(convertToUserDTO(user))
                .build();

    }

    public ResponseEntity<String> logout(){
        ResponseCookie responseCookie = ResponseCookie.from("JWT" , "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body("Logged out successfully.");
    }
    public UserDTO convertToUserDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }


}
