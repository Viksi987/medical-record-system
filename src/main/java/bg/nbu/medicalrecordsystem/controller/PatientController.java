package bg.nbu.medicalrecordsystem.controller;

import bg.nbu.medicalrecordsystem.entity.Patient;
import bg.nbu.medicalrecordsystem.service.PatientService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import bg.nbu.medicalrecordsystem.dto.PatientDTO;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public Patient createPatient(@Valid @RequestBody Patient patient) {
        return patientService.savePatient(patient);
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @GetMapping("/delete-patient/{id}")
    public String deletePatient(@PathVariable Long id) {

        patientService.deletePatient(id);

        return "redirect:/patients-page";
    }

    @GetMapping("/dto/{id}")
    public PatientDTO getPatientDTO(@PathVariable Long id) {

        Patient patient = patientService.getPatientById(id);

        PatientDTO dto = new PatientDTO();

        dto.setId(patient.getId());
        dto.setFullName(patient.getFullName());
        dto.setEgn(patient.getEgn());
        dto.setInsured(patient.getInsured());

        if (patient.getPersonalDoctor() != null) {
            dto.setPersonalDoctorName(
                    patient.getPersonalDoctor().getFullName()
            );
        }

        return dto;
    }
    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id,
                                 @RequestBody Patient patient) {

        return patientService.updatePatient(id, patient);
    }

}