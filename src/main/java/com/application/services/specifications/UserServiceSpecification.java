package com.application.services.specifications;

import com.application.entities.User;

import java.util.List;

public interface UserServiceSpecification {

    User addUser(User user);
    User getUserByName(String name);
    List<User> getUsers();

}
