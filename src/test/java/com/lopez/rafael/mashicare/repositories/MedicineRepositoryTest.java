package com.lopez.rafael.mashicare.repositories;

import com.lopez.rafael.mashicare.entities.Medicine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

// By default, @DataJpaTest uses the embedded h2 database and ignores the URL string declared in application.properties.
// Use @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) to disable this behavior, and use
// the DB configured in application.properties when running @DataJpaTest tests.
// https://stackoverflow.com/a/54686726
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class MedicineRepositoryTest {
    @Autowired
    private MedicineRepository medicineRepository;

    @Test
    public void shouldSaveMedicine() {
        String name = "My Medicine";
        String description = "Description";
        String seller = "Seller";
        double price = 25;

        Medicine medicine = new Medicine();
        medicine.setName(name);
        medicine.setDescription(description);
        medicine.setPrice(new BigDecimal(price));
        medicine.setSeller(seller);

        medicine = medicineRepository.save(medicine);

        assertNotNull(medicine.getId());
        assertTrue(medicine.getName().equalsIgnoreCase(name));
        assertTrue(medicine.getDescription().equalsIgnoreCase(description));
        assertEquals(medicine.getPrice().longValue(), price);
        assertTrue(medicine.getSeller().equalsIgnoreCase(seller));
    }
}
