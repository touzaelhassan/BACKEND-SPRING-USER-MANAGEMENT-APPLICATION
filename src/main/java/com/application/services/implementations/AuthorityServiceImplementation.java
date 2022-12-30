package com.application.services.implementations;

import com.application.entities.Authority;
import com.application.repositories.AuthorityRepository;
import com.application.services.specifications.AuthorityServiceSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("authorityServiceBean")
@Transactional
public class AuthorityServiceImplementation implements AuthorityServiceSpecification {

    private AuthorityRepository authorityRepositoryBean;
    @Autowired
    public AuthorityServiceImplementation(AuthorityRepository authorityRepositoryBean) { this.authorityRepositoryBean = authorityRepositoryBean; }

    @Override
    public Authority addAuthority(Authority authority) { return this.authorityRepositoryBean.save(authority); }
    @Override
    public Authority getAuthorityByName(String name) { return this.authorityRepositoryBean.findByName(name); }
    @Override
    public List<Authority> getAuthorities() { return this.authorityRepositoryBean.findAll(); }

}
