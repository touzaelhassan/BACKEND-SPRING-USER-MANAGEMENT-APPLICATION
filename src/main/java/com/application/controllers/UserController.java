package com.application.controllers;

import com.application.dtos.RegisterRequest;
import com.application.entities.User;
import com.application.exceptions.classes.*;
import com.application.exceptions.ExceptionHandlingController;
import com.application.services.specifications.UserServiceSpecification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping( "/api")
public class UserController extends ExceptionHandlingController {

    private final UserServiceSpecification userServiceBean;

    @Autowired
    public UserController(UserServiceSpecification userServiceBean) { this.userServiceBean = userServiceBean; }

    @PostMapping("/register")
    public ResponseEntity<User > register(@RequestBody RegisterRequest registerRequest) throws UserNotFoundException, EmailExistException, UsernameExistException {
        String firstname = registerRequest.getFirstname();
        String lastname = registerRequest.getLastname();
        String username = registerRequest.getUsername();
        String email = registerRequest.getEmail();
        User registeredUser =   userServiceBean.register(firstname, lastname, username, email);
        return  new ResponseEntity<>(registeredUser, OK);
    }
}
