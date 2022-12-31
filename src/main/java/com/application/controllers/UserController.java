package com.application.controllers;

import com.application.exceptions.ExceptionHandlingController;
import com.application.exceptions.classes.UserNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = {"/", "/api"})
public class UserController extends ExceptionHandlingController {

    @GetMapping("/users")
    public String test() throws UserNotFoundException {
       // return "Hello From The User Endpoint";
        throw new UserNotFoundException("The user was not found !!.");
    }

}
