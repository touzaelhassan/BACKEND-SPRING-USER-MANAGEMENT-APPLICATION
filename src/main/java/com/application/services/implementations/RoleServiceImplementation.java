package com.application.services.implementations;

import com.application.entities.Authority;
import com.application.entities.Role;
import com.application.repositories.AuthorityRepository;
import com.application.repositories.RoleRepository;
import com.application.services.specifications.RoleServiceSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleServiceBean")
@Transactional
public class RoleServiceImplementation implements RoleServiceSpecification {

    private RoleRepository roleRepositoryBean;
    private AuthorityRepository authorityRepositoryBean;

    @Autowired
    public RoleServiceImplementation(AuthorityRepository authorityRepositoryBean, RoleRepository roleRepositoryBean) {
        this.authorityRepositoryBean = authorityRepositoryBean;
        this.roleRepositoryBean = roleRepositoryBean;
    }

    @Override
    public Role addRole(Role role) { return this.roleRepositoryBean.save(role); }
    @Override
    public Role getRoleByName(String name) { return this.roleRepositoryBean.findByName(name); }
    @Override
    public List<Role> getRoles() { return this.roleRepositoryBean.findAll(); }

    @Override
    public void addAuthorityToRole(String roleName, String authorityName) {

        Authority authority = this.authorityRepositoryBean.findByName(authorityName);
        Role role = this.roleRepositoryBean.findByName(roleName);
        role.getAuthorities().add(authority);

    }

}
