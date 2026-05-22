package bg.nbu.medicalrecordsystem.dto;

import java.time.LocalDate;

public class SickLeaveDTO {

    private Long id;

    private LocalDate startDate;

    private Integer days;

    private String patientName;

    private String doctorName;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public LocalDate getStartDate() {

        return startDate;
    }

    public void setStartDate(
            LocalDate startDate) {

        this.startDate = startDate;
    }

    public Integer getDays() {

        return days;
    }

    public void setDays(Integer days) {

        this.days = days;
    }

    public String getPatientName() {

        return patientName;
    }

    public void setPatientName(
            String patientName) {

        this.patientName = patientName;
    }

    public String getDoctorName() {

        return doctorName;
    }

    public void setDoctorName(
            String doctorName) {

        this.doctorName = doctorName;
    }
}