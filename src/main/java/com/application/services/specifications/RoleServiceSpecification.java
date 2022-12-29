package com.application.services.specifications;

import com.application.entities.Role;

import java.util.List;

public interface RoleServiceSpecification {

    Role addRole(Role role);
    Role getRoleByName(String name);
    List<Role> getRoles();

}
