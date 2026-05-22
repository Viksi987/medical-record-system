package bg.nbu.medicalrecordsystem.repository;

import bg.nbu.medicalrecordsystem.entity.Doctor;
import bg.nbu.medicalrecordsystem.entity.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import bg.nbu.medicalrecordsystem.entity.Patient;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ExaminationRepository extends JpaRepository<Examination, Long> {

    List<Examination> findByDoctor(Doctor doctor);

    List<Examination> findByPatientId(Long patientId);

    @Query("""
       SELECT COUNT(e)
       FROM Examination e
       WHERE e.doctor.id = :doctorId
       """)
    Long countExaminationsByDoctor(@Param("doctorId") Long doctorId);

    @Query("""
       SELECT SUM(e.price)
       FROM Examination e
       """)
    java.math.BigDecimal getTotalRevenue();

    @Query("""
       SELECT SUM(e.price)
       FROM Examination e
       WHERE e.doctor.id = :doctorId
       """)
    java.math.BigDecimal getRevenueByDoctor(
            @Param("doctorId") Long doctorId
    );

    Page<Examination> findAll(Pageable pageable);

    List<Examination> findByExaminationDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );

    @Query("""
       SELECT e.diagnosis
       FROM Examination e
       GROUP BY e.diagnosis
       ORDER BY COUNT(e.diagnosis) DESC
       LIMIT 1
       """)
    String getMostCommonDiagnosis();
    @Query("""
       SELECT DISTINCT e.patient
       FROM Examination e
       WHERE LOWER(e.diagnosis) =
       LOWER(:diagnosis)
       """)
    List<Patient> getPatientsByDiagnosis(
            @Param("diagnosis")
            String diagnosis
    );
    @Query("""
       SELECT e.doctor.fullName, COUNT(e)
       FROM Examination e
       GROUP BY e.doctor.fullName
       """)
    List<Object[]> getVisitsCountByDoctor();

    @Query("""
       SELECT e
       FROM Examination e
       WHERE (:doctorId IS NULL OR e.doctor.id = :doctorId)
       AND (:startDate IS NULL OR e.examinationDate >= :startDate)
       AND (:endDate IS NULL OR e.examinationDate <= :endDate)
       """)
    List<Examination> filterExaminations(
            @Param("doctorId") Long doctorId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}