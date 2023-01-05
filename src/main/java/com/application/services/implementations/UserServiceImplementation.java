package com.application.services.implementations;

import com.application.entities.User;
import com.application.exceptions.classes.*;
import com.application.repositories.UserRepository;
import com.application.services.specifications.UserServiceSpecification;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;

import static com.application.constants.FileConstants.*;
import static com.application.constants.UserServiceImplementationConstants.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static com.application.enums.Role.ROLE_USER;

@Service("userServiceBean")
@Transactional
public class UserServiceImplementation implements UserServiceSpecification, UserDetailsService {

    private final UserRepository userRepositoryBean;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserServiceImplementation(UserRepository userRepositoryBean, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepositoryBean = userRepositoryBean;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { return null; }

    @Override
    public User register(String firstname, String lastname, String username, String email) throws UserNotFoundException, EmailExistException, UsernameExistException {
        validateNewUsernameAndEmail(EMPTY, username, email);
        User user = new User();
        user.setUserId(generateUserId());
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        userRepositoryBean.save(user);
        logger.info("The User Password : {}", password);
        return user;
    }

    @Override
    public User findUserByUsername(String username) { return userRepositoryBean.findUserByUsername(username); }
    @Override
    public User findUserByEmail(String email) {
        return userRepositoryBean.findUserByEmail(email);
    }
    @Override
    public User addUser(String firstname, String lastname, String username, String email, String role, boolean isNotLocked, boolean isActive, MultipartFile profileImage) { return null; }
    @Override
    public User updateUser(String currentUsername, String newFirstname, String newLastname, String newUsername, String newEmail, String role, boolean isNotLocked, boolean isActive, MultipartFile profileImage) { return null; }
    @Override
    public User updateProfileImage(String username, MultipartFile newProfileImage) { return null; }
    @Override
    public void resetPassword(String email) { }
    @Override
    public List<User> getUsers() { return null; }
    @Override
    public void deleteUser(String username) { }

    private User validateNewUsernameAndEmail(String currentUsername, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException {

        User userByUsername = this.findUserByUsername(username);
        User userByEmail = this.findUserByEmail(email);

        if (StringUtils.isNotBlank(currentUsername)) {

            User userByCurrentUsername = findUserByUsername(currentUsername);
            if(userByCurrentUsername == null){ throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername); }
            if(userByUsername != null && !userByCurrentUsername.getId().equals(userByUsername.getId())){ throw new UsernameExistException(USERNAME_ALREADY_EXISTS); }
            if(userByEmail != null && !userByCurrentUsername.getId().equals(userByEmail.getId())){ throw new EmailExistException(EMAIL_ALREADY_EXISTS); }
            return userByCurrentUsername;

        }else{

            if(userByUsername != null ){ throw new UsernameExistException(USERNAME_ALREADY_EXISTS); }
            if(userByEmail != null ){ throw new EmailExistException(EMAIL_ALREADY_EXISTS); }
            return null;

        }
    }

    private String generateUserId() { return RandomStringUtils.randomNumeric(10); }
    private String generatePassword() { return RandomStringUtils.randomAlphanumeric(10); }
    private String encodePassword(String password){ return bCryptPasswordEncoder.encode(password); }
    private String getTemporaryProfileImageUrl(String username) {return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString(); }

}
