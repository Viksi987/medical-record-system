package bg.nbu.medicalrecordsystem.service;

import bg.nbu.medicalrecordsystem.entity.Examination;
import bg.nbu.medicalrecordsystem.entity.Patient;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExaminationService {

    Examination saveExamination(Examination examination);

    List<Examination> getAllExaminations();

    Examination getExaminationById(Long id);

    void deleteExamination(Long id);

    List<Examination> getExaminationsByPatient(Long patientId);

    Long countExaminationsByDoctor(Long doctorId);

    java.math.BigDecimal getTotalRevenue();

    java.math.BigDecimal getRevenueByDoctor(Long doctorId);

    Page<Examination> getAllExaminations(Pageable pageable);

    List<Examination> getExaminationsBetweenDates(
            java.time.LocalDate startDate,
            java.time.LocalDate endDate
    );

    String getMostCommonDiagnosis();

    long countByDiagnosis(String diagnosis);

    Examination updateExamination(Long id, Examination examination);

    List<Patient> getPatientsByDiagnosis(
            String diagnosis
    );
    List<Object[]> getVisitsCountByDoctor();

    List<Examination> filterExaminations(
            Long doctorId,
            java.time.LocalDate startDate,
            java.time.LocalDate endDate
    );
}