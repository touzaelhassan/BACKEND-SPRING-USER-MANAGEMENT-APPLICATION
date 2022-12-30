package com.application.services.specifications;

import com.application.entities.User;

import java.util.List;

public interface UserServiceSpecification {

    User addUser(User user);
    User getUserByUsername(String username);
    List<User> getUsers();
    void addRoleToUser(String username, String roleName);

}
