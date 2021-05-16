package com.lopez.rafael.mashicare.controllers;

import com.lopez.rafael.mashicare.entities.Medicine;
import com.lopez.rafael.mashicare.services.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicineController {
    private MedicineService medicineService;

    @Autowired
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping(path = "/medicines")
    public List<Medicine> getAllMedicines() {
        return medicineService.findAll();
    }

    @GetMapping(path = "/medicine/{id}")
    public Medicine getMedicineById(@PathVariable Integer id) {
        return medicineService.findById(id);
    }

    @PostMapping(path = "/medicine")
    public Medicine addMedicine(@RequestBody Medicine medicine) {
        return medicineService.save(medicine);
    }
}
