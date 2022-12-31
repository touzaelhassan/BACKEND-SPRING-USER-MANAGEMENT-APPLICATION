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
            throw new UsernameNotFoundException("User not found !!.");
        }else{
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepositoryBean.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
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
    public User findUserByUsername(String username) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {

        if(StringUtils.isNotBlank(currentUsername)){

            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null){ throw new UserNotFoundException("No user found by current username : " + currentUsername); }

            User newUserByNewUsername = findUserByUsername(newUsername);
            if(newUserByNewUsername != null && !currentUser.getId().equals(newUserByNewUsername.getId())){ throw new UsernameExistException("Username has already taken !!."); }

            User newUserByNewEmail = findUserByEmail(newEmail);
            if(newUserByNewEmail != null && !currentUser.getId().equals(newUserByNewEmail.getId())){ throw new EmailExistException("Email has already taken !!."); }

            return currentUser;

        }else{

            User newUserByNewUsername = findUserByUsername(newUsername);
            if(newUserByNewUsername != null ){ throw new UsernameExistException("Username has already taken !!."); }

            User newUserByNewEmail = findUserByEmail(newEmail);
            if(newUserByNewEmail != null ){ throw new EmailExistException("Email has already taken !!."); }

            return null;

        }

    }

    private String generateUserId() { return RandomStringUtils.randomNumeric(10); }
    private String generatePassword() { return RandomStringUtils.randomAlphanumeric(10); }
    private String encodePassword(String password){ return bCryptPasswordEncoder.encode(password); }
    private String getTemporaryProfileImageUrl() { return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/image/profile/temp").toUriString()   ;}

}
