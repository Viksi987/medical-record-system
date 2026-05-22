package bg.nbu.medicalrecordsystem.service;

import bg.nbu.medicalrecordsystem.entity.Patient;
import bg.nbu.medicalrecordsystem.repository.PatientRepository;
import bg.nbu.medicalrecordsystem.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    public PatientServiceImplTest() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePatient() {

        Patient patient = new Patient();

        patient.setFullName("Ivan Ivanov");

        when(patientRepository.save(patient))
                .thenReturn(patient);

        Patient savedPatient =
                patientService.savePatient(patient);

        assertNotNull(savedPatient);

        assertEquals(
                "Ivan Ivanov",
                savedPatient.getFullName()
        );
    }

    @Test
    void testGetPatientById() {

        Patient patient = new Patient();

        patient.setId(1L);

        when(patientRepository.findById(1L))
                .thenReturn(Optional.of(patient));

        Patient foundPatient =
                patientService.getPatientById(1L);

        assertNotNull(foundPatient);

        assertEquals(1L, foundPatient.getId());
    }

    @Test
    void testDeletePatient() {

        patientService.deletePatient(1L);

        verify(patientRepository,
                times(1))
                .deleteById(1L);
    }
}