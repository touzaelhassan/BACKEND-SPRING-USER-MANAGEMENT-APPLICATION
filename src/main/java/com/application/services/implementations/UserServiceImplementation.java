package com.application.services.implementations;

import com.application.entities.Role;
import com.application.entities.User;
import com.application.repositories.RoleRepository;
import com.application.repositories.UserRepository;
import com.application.services.specifications.UserServiceSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userServiceBean")
@Transactional
public class UserServiceImplementation implements UserServiceSpecification {

    private UserRepository userRepositoryBean;
    private RoleRepository roleRepositoryBean;

    @Autowired
    public UserServiceImplementation(UserRepository userRepositoryBean, RoleRepository roleRepositoryBean) {
        this.userRepositoryBean = userRepositoryBean;
        this.roleRepositoryBean = roleRepositoryBean;
    }

    @Override
    public User addUser(User user) { return this.userRepositoryBean.save(user); }

    @Override
    public User getUserByUsername(String username) { return this.userRepositoryBean.findByUsername(username); }

    @Override
    public List<User> getUsers() { return this.userRepositoryBean.findAll(); }

    @Override
    public void addRoleToUser(String username, String roleName) {

        User user = userRepositoryBean.findByUsername(username);
        Role role = roleRepositoryBean.findByName(roleName);
        user.getRoles().add(role);

    }

}
