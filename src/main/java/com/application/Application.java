package com.application;

import com.application.entities.User;
import com.application.enums.Role;
import com.application.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static com.application.constants.FileConstants.USER_FOLDER;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        new File(USER_FOLDER).mkdirs();
    }

    @Bean
    CommandLineRunner run(
            UserRepository userRepositoryBean,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ){

        return args -> {
            User user = new User();
            user.setUserId("645389");
            user.setFirstname("El Hassan");
            user.setLastname("Touza");
            user.setJoinDate(new Date());
            user.setUsername("touzaelhassan");
            user.setEmail("hassan@gmail.com");
            user.setPassword(bCryptPasswordEncoder.encode("123456"));
            user.setActive(true);
            user.setNotLocked(true);
            user.setRole(Role.ROLE_SUPER_ADMIN.name());
            user.setAuthorities(getRoleEnumName(Role.ROLE_SUPER_ADMIN.name()).getAuthorities());
            userRepositoryBean.save(user);
        };
    }

    private Role getRoleEnumName(String role) { return Role.valueOf(role.toUpperCase()); }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
