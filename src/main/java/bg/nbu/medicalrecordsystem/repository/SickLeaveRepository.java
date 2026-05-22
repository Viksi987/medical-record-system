package bg.nbu.medicalrecordsystem.repository;

import bg.nbu.medicalrecordsystem.entity.SickLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SickLeaveRepository extends JpaRepository<SickLeave, Long> {
    @Query("""
       SELECT MONTH(s.startDate), COUNT(s)
       FROM SickLeave s
       GROUP BY MONTH(s.startDate)
       ORDER BY COUNT(s) DESC
       """)
    List<Object[]> getSickLeavesByMonth();

    @Query("""
       SELECT s.doctor.fullName, COUNT(s)
       FROM SickLeave s
       GROUP BY s.doctor.fullName
       ORDER BY COUNT(s) DESC
       """)
    List<Object[]> getDoctorsWithMostSickLeaves();
}