package bg.nbu.medicalrecordsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatientDTO {

    private Long id;

    private String fullName;

    private String egn;

    private Boolean insured;

    private String personalDoctorName;

}