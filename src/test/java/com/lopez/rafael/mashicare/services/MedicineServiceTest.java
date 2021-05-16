package com.lopez.rafael.mashicare.services;

import com.lopez.rafael.mashicare.entities.Medicine;
import com.lopez.rafael.mashicare.repositories.MedicineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicineServiceTest {
    private static final int MEDICINE_ID = 1;
    @Mock
    private MedicineRepository medicineRepository;
    private MedicineService fixture;

    @BeforeEach
    public void setUp() {
        fixture = new MedicineService(medicineRepository);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        Exception exception = assertThrows( IllegalArgumentException.class, () -> fixture.findById(null) );

        String expectedMessage = "Id must be valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        when(medicineRepository.findById(MEDICINE_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows( EntityNotFoundException.class, () -> fixture.findById(MEDICINE_ID) );

        String expectedMessage = "Entity not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldRetrieveMedicineWhenProductWithGivenIdExists() {
        Medicine expectedResult = new Medicine();
        expectedResult.setId(MEDICINE_ID);

        when(medicineRepository.findById(MEDICINE_ID)).thenReturn(Optional.of(expectedResult));

        Medicine result = fixture.findById(MEDICINE_ID);

        assertNotNull(result);
        assertSame(result.getId(), expectedResult.getId());
    }

    @Test
    public void shouldRetrieveAllMedicineProducts() {
        List<Medicine> expectedResult = new ArrayList();

        Medicine medicine1 = new Medicine();
        medicine1.setId(1);
        expectedResult.add(medicine1);

        Medicine medicine2 = new Medicine();
        medicine2.setId(2);
        expectedResult.add(medicine2);

        when(medicineRepository.findAll()).thenReturn(expectedResult);

        List<Medicine> result = fixture.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.size() == 2);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenMedicineIsNull() {
        Exception exception = assertThrows( IllegalArgumentException.class, () -> fixture.save(null) );

        String expectedMessage = "Medicine entity cannot be null when saving";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
