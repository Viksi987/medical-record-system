package bg.nbu.medicalrecordsystem.repository;

import bg.nbu.medicalrecordsystem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PatientRepository
        extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUserUsername(
            String username
    );

    List<Patient> findByPersonalDoctorId(
            Long doctorId
    );

    @Query("""
       SELECT COUNT(p)
       FROM Patient p
       WHERE p.personalDoctor.id = :doctorId
       """)
    Long countPatientsByDoctor(
            @Param("doctorId") Long doctorId
    );
}