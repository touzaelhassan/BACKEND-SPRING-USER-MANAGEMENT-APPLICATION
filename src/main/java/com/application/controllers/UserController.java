package com.application.controllers;

import com.application.entities.User;
import com.application.exceptions.ExceptionHandlingController;
import com.application.exceptions.classes.EmailExistException;
import com.application.exceptions.classes.UserNotFoundException;
import com.application.exceptions.classes.UsernameExistException;
import com.application.services.specifications.UserServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = {"/", "/api"})
public class UserController extends ExceptionHandlingController {

    private UserServiceSpecification userDetailsService;

    @Autowired
    public UserController(UserServiceSpecification userDetailsService) { this.userDetailsService = userDetailsService; }

    @PostMapping("/register")
    public ResponseEntity<User > register(@RequestBody User user) throws UserNotFoundException, EmailExistException, UsernameExistException {
      User registeredUser =   userDetailsService.register(user.getFirstname(), user.getLastname(), user.getUsername(), user.getEmail());
      return  new ResponseEntity<>(registeredUser, OK);
    }

}
