package bg.nbu.medicalrecordsystem.service;

import bg.nbu.medicalrecordsystem.entity.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor saveDoctor(Doctor doctor);

    List<Doctor> getAllDoctors();

    Doctor getDoctorById(Long id);

    void deleteDoctor(Long id);

    Doctor updateDoctor(Long id, Doctor doctor);

}