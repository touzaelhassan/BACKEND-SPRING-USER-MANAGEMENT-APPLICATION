package com.application.services.specifications;

import com.application.entities.User;
import com.application.exceptions.classes.EmailExistException;
import com.application.exceptions.classes.UserNotFoundException;
import com.application.exceptions.classes.UsernameExistException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserServiceSpecification {

    User register(String firstname, String lastname, String username, String email) throws UserNotFoundException, EmailExistException, UsernameExistException;
    User addUser(String firstname, String lastname, String username, String email, String role, boolean isNotLocked, boolean isActive, MultipartFile profileImage);
    User updateUser(String currentUsername, String newFirstname, String newLastname, String newUsername, String newEmail, String role, boolean isNotLocked, boolean isActive, MultipartFile profileImage);
    User updateProfileImage(String username, MultipartFile newProfileImage);
    void resetPassword(String email);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    List<User> getUsers();
    void deleteUser(Long id);

}
