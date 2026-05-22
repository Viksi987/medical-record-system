package bg.nbu.medicalrecordsystem.service;

import bg.nbu.medicalrecordsystem.entity.SickLeave;

import java.util.List;

public interface SickLeaveService {

    SickLeave saveSickLeave(SickLeave sickLeave);

    List<SickLeave> getAllSickLeaves();

    SickLeave getSickLeaveById(Long id);

    void deleteSickLeave(Long id);
    List<Object[]> getSickLeavesByMonth();

    List<Object[]> getDoctorsWithMostSickLeaves();

}