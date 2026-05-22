package bg.nbu.medicalrecordsystem.service.impl;

import bg.nbu.medicalrecordsystem.entity.Examination;
import bg.nbu.medicalrecordsystem.entity.Patient;
import bg.nbu.medicalrecordsystem.repository.ExaminationRepository;
import bg.nbu.medicalrecordsystem.repository.PatientRepository;
import bg.nbu.medicalrecordsystem.service.ExaminationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import bg.nbu.medicalrecordsystem.dto.ExaminationDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExaminationServiceImpl
        implements ExaminationService {

    private final ExaminationRepository examinationRepository;

    private final PatientRepository patientRepository;

    public ExaminationServiceImpl(
            ExaminationRepository examinationRepository,
            PatientRepository patientRepository) {

        this.examinationRepository = examinationRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Examination saveExamination(
            Examination examination) {

        Patient patient = patientRepository
                .findById(examination.getPatient().getId())
                .orElseThrow();

        examination.setPatient(patient);

        if (Boolean.TRUE.equals(patient.getInsured())) {

            examination.setPrice(BigDecimal.ZERO);
        }

        return examinationRepository.save(examination);
    }

    @Override
    public List<Examination> getAllExaminations() {

        return examinationRepository.findAll();
    }

    @Override
    public Examination getExaminationById(Long id) {

        return examinationRepository.findById(id)
                .orElse(null);
    }

    @Override
    public void deleteExamination(Long id) {

        examinationRepository.deleteById(id);
    }

    @Override
    public List<Examination> getExaminationsByPatient(
            Long patientId) {

        return examinationRepository
                .findByPatientId(patientId);
    }

    @Override
    public Long countExaminationsByDoctor(Long doctorId) {

        return examinationRepository
                .countExaminationsByDoctor(doctorId);
    }

    @Override
    public BigDecimal getTotalRevenue() {

        return examinationRepository.getTotalRevenue();
    }

    @Override
    public BigDecimal getRevenueByDoctor(Long doctorId) {

        return examinationRepository
                .getRevenueByDoctor(doctorId);
    }

    @Override
    public Page<Examination> getAllExaminations(
            Pageable pageable) {

        return examinationRepository.findAll(pageable);
    }

    @Override
    public List<Examination> getExaminationsBetweenDates(
            LocalDate startDate,
            LocalDate endDate) {

        return examinationRepository
                .findByExaminationDateBetween(
                        startDate,
                        endDate
                );
    }

    @Override
    public String getMostCommonDiagnosis() {

        return examinationRepository
                .getMostCommonDiagnosis();
    }

    @Override
    public long countByDiagnosis(String diagnosis) {

        return examinationRepository
                .findAll()
                .stream()
                .filter(e ->
                        e.getDiagnosis()
                                .equalsIgnoreCase(diagnosis))
                .count();
    }

    @Override
    public Examination updateExamination(
            Long id,
            Examination updatedExamination) {

        Examination examination =
                examinationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Examination not found"
                                ));

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String loggedUsername =
                authentication.getName();

        if (!examination.getDoctor()
                .getUser()
                .getUsername()
                .equals(loggedUsername)) {

            throw new RuntimeException(
                    "You can edit only your own examinations!"
            );
        }

        examination.setDiagnosis(
                updatedExamination.getDiagnosis());

        examination.setTreatment(
                updatedExamination.getTreatment());

        examination.setPrice(
                updatedExamination.getPrice());

        return examinationRepository.save(examination);
    }
    @Override
    public List<Patient> getPatientsByDiagnosis(
            String diagnosis) {

        return examinationRepository
                .getPatientsByDiagnosis(diagnosis);
    }
    @Override
    public List<Object[]> getVisitsCountByDoctor() {

        return examinationRepository
                .getVisitsCountByDoctor();
    }
    @Override
    public List<Examination> filterExaminations(
            Long doctorId,
            LocalDate startDate,
            LocalDate endDate
    ) {

        return examinationRepository.filterExaminations(
                doctorId,
                startDate,
                endDate
        );
    }
    private ExaminationDTO mapToDTO(
            Examination examination) {

        ExaminationDTO dto =
                new ExaminationDTO();

        dto.setId(examination.getId());

        dto.setDiagnosis(
                examination.getDiagnosis());

        dto.setTreatment(
                examination.getTreatment());

        dto.setPrice(
                examination.getPrice());

        dto.setExaminationDate(
                examination.getExaminationDate());

        if (examination.getPatient() != null) {

            dto.setPatientName(
                    examination.getPatient()
                            .getFullName()
            );
        }

        if (examination.getDoctor() != null) {

            dto.setDoctorName(
                    examination.getDoctor()
                            .getFullName()
            );
        }

        return dto;
    }
}