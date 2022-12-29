package com.application.repositories;

import com.application.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("authorityRepositoryBean")
public interface AuthorityRepository extends JpaRepository<Authority, Long> { }
