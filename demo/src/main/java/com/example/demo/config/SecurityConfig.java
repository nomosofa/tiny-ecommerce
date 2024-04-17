package com.example.demo.config;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 揭程
 * @version 1.0
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    @Qualifier("jwtRequestFilter")
    private Filter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF protection is typically not needed for API requests.
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session will be created or used by Spring Security.
                .and()
                .authorizeRequests()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/**").permitAll()
                .requestMatchers("/**").permitAll()
                .requestMatchers("/api/users/login", "/api/users/register").permitAll() // Allows access to any user.
//                .requestMatchers("/api/admin/**").hasRole("ADMIN") // 如果角色是"ROLE_ADMIN"
//                .requestMatchers("/api/user/**").hasRole("USER") // 如果角色是"ROLE_USER"
                .anyRequest().authenticated(); // Every other request requires authentication.

        // 确保使用了我们的自定义JWT过滤器
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }
}



//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // Disable CSRF protection as an example; adjust based on your requirements
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .antMatchers("/api/public/**").permitAll() // Allow public access to certain endpoints
//                                .anyRequest().authenticated() // Require authentication for any other request
//                )
//                .httpBasic(Customizer.withDefaults()); // Example: Basic Authentication
//        return http.build();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}