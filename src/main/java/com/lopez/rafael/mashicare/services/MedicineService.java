package com.lopez.rafael.mashicare.services;

import com.lopez.rafael.mashicare.entities.Medicine;
import com.lopez.rafael.mashicare.repositories.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MedicineService {
    private MedicineRepository medicineRepository;

    @Autowired
    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Medicine findById(Integer id) {
        if( id == null ) {
            throw new IllegalArgumentException("Id must be valid");
        }

        Medicine medicine = medicineRepository.findById(id).orElse(null);

        if( medicine == null ) {
            throw new EntityNotFoundException("Entity not found");
        }

        return medicine;
    }

    public List<Medicine> findAll() {
        return  medicineRepository.findAll();
    }

    public Medicine save(Medicine medicine) {
        if( medicine == null ) {
            throw new IllegalArgumentException("Medicine entity cannot be null when saving");
        }

        return medicineRepository.save(medicine);
    }
}
