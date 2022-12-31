package com.application;

import com.application.entities.Authority;
import com.application.entities.Role;
import com.application.entities.User;
import com.application.services.specifications.AuthorityServiceSpecification;
import com.application.services.specifications.RoleServiceSpecification;
import com.application.services.specifications.UserServiceSpecification;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class SpringUserManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringUserManagementApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){return new BCryptPasswordEncoder(); }

    @Bean
    CommandLineRunner run(
            AuthorityServiceSpecification authorityServiceBean,
            RoleServiceSpecification roleServiceBean,
            UserServiceSpecification userServiceBean
    ){

        return args -> {

            // ADD AUTHORITIES
            Authority authority1 = authorityServiceBean.addAuthority(new Authority("CREATE"));
            Authority authority2 = authorityServiceBean.addAuthority(new Authority("READ"));
            Authority authority3 = authorityServiceBean.addAuthority(new Authority("UPDATE"));
            Authority authority4 = authorityServiceBean.addAuthority(new Authority("DELETE"));

            // ADD ROLES
            Role role1 =  roleServiceBean.addRole(new Role("ADMIN"));
            Role role2 =  roleServiceBean.addRole(new Role("MANAGER"));
            Role role3 =  roleServiceBean.addRole(new Role("USER"));

            // ADD AUTHORITY TO ROLE
            roleServiceBean.addAuthorityToRole(role1.getName(), authority1.getName());
            roleServiceBean.addAuthorityToRole(role1.getName(), authority2.getName());
            roleServiceBean.addAuthorityToRole(role1.getName(), authority3.getName());
            roleServiceBean.addAuthorityToRole(role1.getName(), authority4.getName());
            roleServiceBean.addAuthorityToRole(role2.getName(), authority1.getName());
            roleServiceBean.addAuthorityToRole(role2.getName(), authority2.getName());
            roleServiceBean.addAuthorityToRole(role2.getName(), authority3.getName());
            roleServiceBean.addAuthorityToRole(role3.getName(), authority2.getName());

            // ADD USERS
            User admin = userServiceBean.addUser(new User("a1a1a1", "adminF", "adminL", "admin", "admin@gmail.com", "123456", "Admin Profile Image URL", new Date(), new Date(), new Date(), true, false));
            User manager = userServiceBean.addUser(new User("m1m1m1", "managerF", "managerL", "manager", "manager@gmail.com", "123456", "Manager Profile Image URL", new Date(), new Date(), new Date(), true, false));
            User user1 = userServiceBean.addUser(new User("u1u1u1", "user1F", "user1L", "user1", "user1@gmail.com", "123456", "Manager Profile Image URL", new Date(), new Date(), new Date(), true, false));
            User user2 = userServiceBean.addUser(new User("u2u2u2", "user2F", "user2L", "user2", "user2@gmail.com", "123456", "Manager Profile Image URL", new Date(), new Date(), new Date(), true, false));
            User user3 = userServiceBean.addUser(new User("u3u3u3", "user3F", "user3L", "user3", "user3@gmail.com", "123456", "Manager Profile Image URL", new Date(), new Date(), new Date(), true, false));

            // ADD ROLE TO USER
            userServiceBean.addRoleToUser(admin.getUsername(), role1.getName());
            userServiceBean.addRoleToUser(manager.getUsername(), role2.getName());
            userServiceBean.addRoleToUser(user1.getUsername(), role3.getName());
            userServiceBean.addRoleToUser(user2.getUsername(), role3.getName());
            userServiceBean.addRoleToUser(user3.getUsername(), role3.getName());

        };

    }

}
