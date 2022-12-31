package com.application.services.specifications;

import com.application.entities.User;
import com.application.exceptions.classes.EmailExistException;
import com.application.exceptions.classes.UserNotFoundException;
import com.application.exceptions.classes.UsernameExistException;

import java.util.List;

public interface UserServiceSpecification {

    User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, EmailExistException, UsernameExistException;
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    List<User> getUsers();

}
