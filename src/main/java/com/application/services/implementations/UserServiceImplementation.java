package com.application.services.implementations;

import com.application.classes.UserPrincipal;
import com.application.entities.Role;
import com.application.entities.User;
import com.application.repositories.RoleRepository;
import com.application.repositories.UserRepository;
import com.application.services.specifications.UserServiceSpecification;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service()
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImplementation implements UserServiceSpecification, UserDetailsService {

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
    public User getUserByUsername(String username) { return this.userRepositoryBean.findUserByUsername(username); }
    @Override
    public List<User> getUsers() { return this.userRepositoryBean.findAll(); }
    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepositoryBean.findUserByUsername(username);
        Role role = roleRepositoryBean.findRoleByName(roleName);
        user.getRoles().add(role);
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
}
