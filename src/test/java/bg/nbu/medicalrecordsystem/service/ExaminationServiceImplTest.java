package bg.nbu.medicalrecordsystem.service;

import bg.nbu.medicalrecordsystem.entity.Examination;
import bg.nbu.medicalrecordsystem.repository.ExaminationRepository;
import bg.nbu.medicalrecordsystem.service.impl.ExaminationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import bg.nbu.medicalrecordsystem.entity.Patient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import bg.nbu.medicalrecordsystem.repository.PatientRepository;

public class ExaminationServiceImplTest {

    @Mock
    private ExaminationRepository examinationRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private ExaminationServiceImpl examinationService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveExamination() {

        Patient patient = new Patient();

        patient.setId(1L);

        Examination examination =
                new Examination();

        examination.setDiagnosis("Flu");

        examination.setPatient(patient);

        when(patientRepository.findById(1L))
                .thenReturn(Optional.of(patient));

        when(examinationRepository.save(examination))
                .thenReturn(examination);

        Examination savedExamination =
                examinationService.saveExamination(
                        examination
                );

        assertNotNull(savedExamination);

        assertEquals(
                "Flu",
                savedExamination.getDiagnosis()
        );
    }

    @Test
    void testGetExaminationById() {

        Examination examination =
                new Examination();

        examination.setId(1L);

        when(examinationRepository.findById(1L))
                .thenReturn(Optional.of(examination));

        Examination foundExamination =
                examinationService
                        .getExaminationById(1L);

        assertNotNull(foundExamination);

        assertEquals(
                1L,
                foundExamination.getId()
        );
    }

    @Test
    void testDeleteExamination() {

        examinationService.deleteExamination(1L);

        verify(examinationRepository,
                times(1))
                .deleteById(1L);
    }
}