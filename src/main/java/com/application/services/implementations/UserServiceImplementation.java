package com.application.services.implementations;

import com.application.classes.UserPrincipal;
import com.application.entities.User;
import com.application.exceptions.classes.EmailExistException;
import com.application.exceptions.classes.UserNotFoundException;
import com.application.exceptions.classes.UsernameExistException;
import com.application.repositories.UserRepository;
import com.application.services.specifications.UserServiceSpecification;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;

import static com.application.constants.UserServiceImplementationConstants.*;
import static com.application.enums.Role.ROLE_USER;

@Service()
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImplementation implements UserServiceSpecification, UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserRepository userRepositoryBean;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository userRepositoryBean, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepositoryBean = userRepositoryBean;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositoryBean.findUserByUsername(username);
        if(user == null){
            LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        }else{
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepositoryBean.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info(FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
    }

    @Override
    public User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, EmailExistException, UsernameExistException {

        validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);

        User user = new User();
        user.setUserId(generateUserId());
        String password = generatePassword();
        String encodedPassword = encodePassword(password);

        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl());
        userRepositoryBean.save(user);

        LOGGER.info("New user password : " + password);

        return user;

    }

    @Override
    public User findUserByUsername(String username) { return userRepositoryBean.findUserByUsername(username);}
    @Override
    public User findUserByEmail(String email) {
        return userRepositoryBean.findUserByEmail(email);
    }
    @Override
    public List<User> getUsers() {
        return userRepositoryBean.findAll();
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {

        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);

        if(StringUtils.isNotBlank(currentUsername)){

            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null){ throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername); }

            if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())){ throw new UsernameExistException(USERNAME_ALREADY_EXISTS); }
            if(userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())){ throw new EmailExistException(EMAIL_ALREADY_EXISTS); }

            return currentUser;

        }else{

            if(userByNewUsername != null ){ throw new UsernameExistException(USERNAME_ALREADY_EXISTS); }
            if(userByNewEmail != null ){ throw new EmailExistException(EMAIL_ALREADY_EXISTS); }

            return null;

        }
    }

    private String generateUserId() { return RandomStringUtils.randomNumeric(10); }
    private String generatePassword() { return RandomStringUtils.randomAlphanumeric(10); }
    private String encodePassword(String password){ return bCryptPasswordEncoder.encode(password); }
    private String getTemporaryProfileImageUrl() { return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH).toUriString()   ;}

}
