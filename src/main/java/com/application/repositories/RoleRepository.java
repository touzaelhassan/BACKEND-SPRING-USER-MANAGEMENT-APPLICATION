package com.application.repositories;

import com.application.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepositoryBean")
public interface RoleRepository extends JpaRepository<Role, Long> { }
