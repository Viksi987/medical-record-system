package bg.nbu.medicalrecordsystem.dto;

public class DoctorDTO {

    private Long id;

    private String fullName;

    private String specialty;

    private Boolean canBePersonalDoctor;

    private Long examinationsCount;

    private Long patientsCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Boolean getCanBePersonalDoctor() {
        return canBePersonalDoctor;
    }

    public void setCanBePersonalDoctor(
            Boolean canBePersonalDoctor) {

        this.canBePersonalDoctor =
                canBePersonalDoctor;
    }

    public Long getExaminationsCount() {
        return examinationsCount;
    }

    public void setExaminationsCount(
            Long examinationsCount) {

        this.examinationsCount =
                examinationsCount;
    }

    public Long getPatientsCount() {
        return patientsCount;
    }

    public void setPatientsCount(
            Long patientsCount) {

        this.patientsCount =
                patientsCount;
    }
}