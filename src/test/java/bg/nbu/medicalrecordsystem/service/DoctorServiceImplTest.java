package bg.nbu.medicalrecordsystem.service;

import bg.nbu.medicalrecordsystem.entity.Doctor;
import bg.nbu.medicalrecordsystem.repository.DoctorRepository;
import bg.nbu.medicalrecordsystem.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private ExaminationService examinationService;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDoctor() {

        Doctor doctor = new Doctor();

        doctor.setFullName("Dr Ivanov");

        when(doctorRepository.save(doctor))
                .thenReturn(doctor);

        Doctor savedDoctor =
                doctorService.saveDoctor(doctor);

        assertNotNull(savedDoctor);

        assertEquals(
                "Dr Ivanov",
                savedDoctor.getFullName()
        );
    }

    @Test
    void testGetDoctorById() {

        Doctor doctor = new Doctor();

        doctor.setId(1L);

        when(doctorRepository.findById(1L))
                .thenReturn(Optional.of(doctor));

        Doctor foundDoctor =
                doctorService.getDoctorById(1L);

        assertNotNull(foundDoctor);

        assertEquals(
                1L,
                foundDoctor.getId()
        );
    }

    @Test
    void testDeleteDoctor() {

        doctorService.deleteDoctor(1L);

        verify(doctorRepository,
                times(1))
                .deleteById(1L);
    }
}