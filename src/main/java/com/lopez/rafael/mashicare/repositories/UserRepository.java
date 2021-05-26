package com.lopez.rafael.mashicare.repositories;

import com.lopez.rafael.mashicare.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
