package bg.nbu.medicalrecordsystem.service;

import bg.nbu.medicalrecordsystem.entity.Patient;

import java.util.List;

public interface PatientService {

    Patient savePatient(Patient patient);

    List<Patient> getAllPatients();

    Patient getPatientById(Long id);

    void deletePatient(Long id);

    Patient updatePatient(Long id, Patient patient);

    Patient findByUsername(String username);

    List<Patient> getPatientsByDoctor(
            Long doctorId
    );
    Long countPatientsByDoctor(Long doctorId);
}