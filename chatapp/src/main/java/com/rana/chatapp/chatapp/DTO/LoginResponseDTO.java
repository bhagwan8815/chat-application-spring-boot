//package com.rana.chatapp.chatapp.DTO;
//
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.AllArgsConstructor;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class LoginResponseDTO {
//    private String token;
//    private UserDTO userDTO;
//
//    public String getToken() {
//        return token;
//    }
//
//    public UserDTO getUserDTO() {
//        return userDTO;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public void setUserDTO(UserDTO userDTO) {
//        this.userDTO = userDTO;
//    }
//}
package com.rana.chatapp.chatapp.DTO;

public class LoginResponseDTO {
    private String token;
    private UserDTO userDTO;

    // No-args constructor
    public LoginResponseDTO() {
    }

    // All-args constructor
    public LoginResponseDTO(String token, UserDTO userDTO) {
        this.token = token;
        this.userDTO = userDTO;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    // Setters
    public void setToken(String token) {
        this.token = token;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    // Builder pattern implementation (manual)
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String token;
        private UserDTO userDTO;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder userDTO(UserDTO userDTO) {
            this.userDTO = userDTO;
            return this;
        }

        public LoginResponseDTO build() {
            return new LoginResponseDTO(token, userDTO);
        }
    }

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "token='" + token + '\'' +
                ", userDTO=" + userDTO +
                '}';
    }
}
