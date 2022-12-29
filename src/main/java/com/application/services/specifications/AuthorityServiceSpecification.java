package com.application.services.specifications;

import com.application.entities.Authority;

import java.util.List;

public interface AuthorityServiceSpecification {

    Authority addAuthority(Authority authority);
    Authority getAuthorityByName(String name);
    List<Authority> getAuthorities();

}
