package com.application.services.implementations;

import com.application.entities.User;
import com.application.repositories.UserRepository;
import com.application.services.specifications.UserServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userServiceBean")
public class UserServiceImplementation implements UserServiceSpecification {

    private UserRepository userRepositoryBean;

    @Autowired
    public UserServiceImplementation(UserRepository userRepositoryBean) {
        this.userRepositoryBean = userRepositoryBean;
    }

    @Override
    public User addUser(User user) { return this.userRepositoryBean.save(user); }

    @Override
    public User getUserByName(String name) { return this.userRepositoryBean.findByName(name); }

    @Override
    public List<User> getUsers() { return this.userRepositoryBean.findAll(); }

}
