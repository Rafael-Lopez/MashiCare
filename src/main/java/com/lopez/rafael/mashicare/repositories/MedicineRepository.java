package com.lopez.rafael.mashicare.repositories;

import com.lopez.rafael.mashicare.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
}
