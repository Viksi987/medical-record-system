package bg.nbu.medicalrecordsystem.service.impl;

import bg.nbu.medicalrecordsystem.dto.PatientDTO;
import bg.nbu.medicalrecordsystem.entity.Patient;
import bg.nbu.medicalrecordsystem.repository.PatientRepository;
import bg.nbu.medicalrecordsystem.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {

        this.patientRepository = patientRepository;
    }

    @Override
    public Patient savePatient(Patient patient) {

        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {

        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Long id) {

        return patientRepository.findById(id)
                .orElse(null);
    }

    @Override
    public void deletePatient(Long id) {

        patientRepository.deleteById(id);
    }

    @Override
    public Patient updatePatient(Long id,
                                 Patient updatedPatient) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        patient.setFullName(updatedPatient.getFullName());
        patient.setEgn(updatedPatient.getEgn());
        patient.setInsured(updatedPatient.getInsured());
        patient.setPersonalDoctor(updatedPatient.getPersonalDoctor());
        patient.setUser(updatedPatient.getUser());

        return patientRepository.save(patient);
    }

    @Override
    public Patient findByUsername(String username) {

        return patientRepository
                .findByUserUsername(username)
                .orElse(null);
    }

    private PatientDTO mapToDTO(Patient patient) {

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
    @Override
    public List<Patient> getPatientsByDoctor(
            Long doctorId) {

        return patientRepository
                .findByPersonalDoctorId(
                        doctorId
                );
    }
    @Override
    public Long countPatientsByDoctor(Long doctorId) {

        return patientRepository
                .countPatientsByDoctor(doctorId);
    }
}