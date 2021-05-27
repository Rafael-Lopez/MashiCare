package com.lopez.rafael.mashicare.repositories;

import com.lopez.rafael.mashicare.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
