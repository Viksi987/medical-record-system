package bg.nbu.medicalrecordsystem.service.impl;

import bg.nbu.medicalrecordsystem.dto.DoctorDTO;
import bg.nbu.medicalrecordsystem.entity.Doctor;
import bg.nbu.medicalrecordsystem.repository.DoctorRepository;
import bg.nbu.medicalrecordsystem.service.DoctorService;
import bg.nbu.medicalrecordsystem.service.ExaminationService;
import bg.nbu.medicalrecordsystem.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl
        implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final ExaminationService examinationService;

    private final PatientService patientService;

    public DoctorServiceImpl(
            DoctorRepository doctorRepository,
            ExaminationService examinationService,
            PatientService patientService) {

        this.doctorRepository = doctorRepository;
        this.examinationService = examinationService;
        this.patientService = patientService;
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {

        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getAllDoctors() {

        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(Long id) {

        return doctorRepository.findById(id)
                .orElse(null);
    }

    @Override
    public void deleteDoctor(Long id) {

        doctorRepository.deleteById(id);
    }

    @Override
    public Doctor updateDoctor(
            Long id,
            Doctor updatedDoctor) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found"
                        ));

        doctor.setFullName(
                updatedDoctor.getFullName());

        doctor.setSpecialty(
                updatedDoctor.getSpecialty());

        doctor.setCanBePersonalDoctor(
                updatedDoctor.getCanBePersonalDoctor());

        doctor.setUser(
                updatedDoctor.getUser());

        return doctorRepository.save(doctor);
    }

    private DoctorDTO mapToDTO(
            Doctor doctor) {

        DoctorDTO dto =
                new DoctorDTO();

        dto.setId(doctor.getId());

        dto.setFullName(
                doctor.getFullName());

        dto.setSpecialty(
                doctor.getSpecialty());

        dto.setCanBePersonalDoctor(
                doctor.getCanBePersonalDoctor());

        dto.setExaminationsCount(
                examinationService
                        .countExaminationsByDoctor(
                                doctor.getId()
                        )
        );

        dto.setPatientsCount(
                patientService
                        .countPatientsByDoctor(
                                doctor.getId()
                        )
        );

        return dto;
    }
}