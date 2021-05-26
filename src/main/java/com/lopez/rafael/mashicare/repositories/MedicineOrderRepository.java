package com.lopez.rafael.mashicare.repositories;

import com.lopez.rafael.mashicare.entities.MedicineOrder;
import com.lopez.rafael.mashicare.entities.MedicineOrderKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineOrderRepository extends JpaRepository<MedicineOrder, MedicineOrderKey> {
}
