package com.application.controllers;

import com.application.classes.HttpResponse;
import com.application.classes.UserPrincipal;
import com.application.dtos.LoginRequest;
import com.application.entities.User;
import com.application.exceptions.ExceptionHandlingController;
import com.application.security.jwt.JWTTokenProvider;
import com.application.services.specifications.UserServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static com.application.constants.SecurityConstants.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class UserController extends ExceptionHandlingController {

    private final UserServiceSpecification userServiceBean;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserServiceSpecification userServiceBean, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
        this.userServiceBean = userServiceBean;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<User > login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        authentication(username, password);
        User loggedUser = userServiceBean.findUserByUsername(loginRequest.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loggedUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return  new ResponseEntity<>(loggedUser, jwtHeader, OK);
    }

    private void authentication(String username, String password) { authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)); }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJWTToken(userPrincipal));
        return headers;
    }

}
