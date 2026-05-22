package bg.nbu.medicalrecordsystem.service.impl;

import bg.nbu.medicalrecordsystem.entity.SickLeave;
import bg.nbu.medicalrecordsystem.repository.SickLeaveRepository;
import bg.nbu.medicalrecordsystem.service.SickLeaveService;
import org.springframework.stereotype.Service;
import bg.nbu.medicalrecordsystem.dto.SickLeaveDTO;
import java.util.List;

@Service
public class SickLeaveServiceImpl implements SickLeaveService {

    private final SickLeaveRepository sickLeaveRepository;

    public SickLeaveServiceImpl(SickLeaveRepository sickLeaveRepository) {
        this.sickLeaveRepository = sickLeaveRepository;
    }

    @Override
    public SickLeave saveSickLeave(SickLeave sickLeave) {
        return sickLeaveRepository.save(sickLeave);
    }

    @Override
    public List<SickLeave> getAllSickLeaves() {
        return sickLeaveRepository.findAll();
    }

    @Override
    public SickLeave getSickLeaveById(Long id) {
        return sickLeaveRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteSickLeave(Long id) {
        sickLeaveRepository.deleteById(id);
    }
    @Override
    public List<Object[]> getSickLeavesByMonth() {

        return sickLeaveRepository
                .getSickLeavesByMonth();
    }
    @Override
    public List<Object[]> getDoctorsWithMostSickLeaves() {

        return sickLeaveRepository
                .getDoctorsWithMostSickLeaves();
    }
    private SickLeaveDTO mapToDTO(
            SickLeave sickLeave) {

        SickLeaveDTO dto =
                new SickLeaveDTO();

        dto.setId(sickLeave.getId());

        dto.setStartDate(
                sickLeave.getStartDate());

        dto.setDays(
                sickLeave.getDays());

        if (sickLeave.getPatient() != null) {

            dto.setPatientName(
                    sickLeave.getPatient()
                            .getFullName()
            );
        }

        if (sickLeave.getDoctor() != null) {

            dto.setDoctorName(
                    sickLeave.getDoctor()
                            .getFullName()
            );
        }

        return dto;
    }
}