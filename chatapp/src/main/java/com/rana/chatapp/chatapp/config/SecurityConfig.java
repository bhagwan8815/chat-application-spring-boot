package com.rana.chatapp.chatapp.config;

import com.rana.chatapp.chatapp.JWT.JwtAuthenticationFilter;
import com.rana.chatapp.chatapp.services.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private  JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    //  1. UserDetailsService Bean
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetails();
    }

    //2. Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //  3. Custom AuthenticationManager (without DaoAuthenticationProvider)
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        return authentication -> {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();

            var userDetails = userDetailsService.loadUserByUsername(username);

            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                return new UsernamePasswordAuthenticationToken(
                        username,
                        password,
                        userDetails.getAuthorities()
                );
            } else {
                throw new BadCredentialsException("Invalid username or password");
            }
        };
    }

    //  4. Security Filter Chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // for H2 console
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // JWT filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //  5. CORS Configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOriginPatterns(Arrays.asList("*"));
        cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cors.setAllowedHeaders(Arrays.asList("*"));
        cors.setAllowCredentials(true);
        cors.setExposedHeaders(Arrays.asList("Set-Cookie"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }
}













//
//
//package com.rana.chatapp.chatapp.config;
//
//import com.rana.chatapp.chatapp.JWT.JwtAuthenticationFilter;
//import com.rana.chatapp.chatapp.services.CustomUserDetails;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//   @Autowired
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//   @Bean
//   public SecurityFilterChain configure(HttpSecurity http) throws Exception{
//       http.csrf(csrf->csrf.disable())
//               .headers(header->header.frameOptions(frameOption-> frameOption.disable()))
//               .cors(cors -> cors.configurationSource(addConfigurationSource()))
//               .authorizeHttpRequests(auth -> auth
//                       .requestMatchers("/auth/**").permitAll()
//                       .requestMatchers("/h2-console/**").permitAll()
//                       .requestMatchers("/api/**").permitAll()
//                       .requestMatchers("/ws/**").permitAll().anyRequest().authenticated())
//               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//               .authenticationProvider(authenticationProvider())
//               .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//
//       return http.build();
//
//   }
//
//   @Bean
//   public UserDetailsService userDetailsService(){
//       return new CustomUserDetails();
//   }
//
//   @Bean
//   public PasswordEncoder passwordEncoder(){
//       return new BCryptPasswordEncoder();
//   }
//
//@Bean
//public AuthenticationProvider authenticationProvider(){
//       DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//       daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//       daoAuthenticationProvider.setUserDetailsService(userDetailsService());
//       return daoAuthenticationProvider;
//   }
//
//
//
////@Bean
////public AuthenticationManager authenticationManager(
////        UserDetailsService userDetailsService,
////        PasswordEncoder passwordEncoder) {
////
////    return authentication -> {
////        String username = authentication.getName();
////        String password = authentication.getCredentials().toString();
////
////        var userDetails = userDetailsService.loadUserByUsername(username);
////
////        if (passwordEncoder.matches(password, userDetails.getPassword())) {
////            return new UsernamePasswordAuthenticationToken(
////                    username, password, userDetails.getAuthorities());
////        } else {
////            throw new BadCredentialsException("Invalid username or password");
////        }
////    };
////}
//
//   @Bean
//   public CorsConfigurationSource addConfigurationSource(){
//       CorsConfiguration corsConfiguration = new CorsConfiguration();
//       corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
//       corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT" , "DELETE","OPTIONS"));
//       corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
//       corsConfiguration.setAllowCredentials(true);
//       corsConfiguration.setExposedHeaders(Arrays.asList("Set-cookie"));
//
//       UrlBasedCorsConfigurationSource  source = new UrlBasedCorsConfigurationSource();
//       source.registerCorsConfiguration("/**" , corsConfiguration);
//       return source;
//   }
//
//
//
//}
