package com.application.services.implementations;

import com.application.entities.Role;
import com.application.repositories.RoleRepository;
import com.application.services.specifications.RoleServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleServiceBean")
public class RoleServiceImplementation implements RoleServiceSpecification {

    private RoleRepository roleRepositoryBean;

    @Autowired
    public RoleServiceImplementation(RoleRepository roleRepositoryBean) { this.roleRepositoryBean = roleRepositoryBean; }

    @Override
    public Role addRole(Role role) { return this.roleRepositoryBean.save(role); }
    @Override
    public Role getRoleByName(String name) { return this.roleRepositoryBean.finByName(name); }
    @Override
    public List<Role> getRoles() { return this.roleRepositoryBean.findAll(); }

}
