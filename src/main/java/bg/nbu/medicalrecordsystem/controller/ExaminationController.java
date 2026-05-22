package bg.nbu.medicalrecordsystem.controller;

import bg.nbu.medicalrecordsystem.entity.Examination;
import bg.nbu.medicalrecordsystem.service.ExaminationService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;

@RestController
@RequestMapping("/examinations")
public class ExaminationController {

    private final ExaminationService examinationService;

    public ExaminationController(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    @PostMapping
    public Examination createExamination(@Valid @RequestBody Examination examination) {
        return examinationService.saveExamination(examination);
    }

    @GetMapping
    public List<Examination> getAllExaminations() {
        return examinationService.getAllExaminations();
    }

    @GetMapping("/{id}")
    public Examination getExaminationById(@PathVariable Long id) {
        return examinationService.getExaminationById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteExamination(@PathVariable Long id) {
        examinationService.deleteExamination(id);
    }
    @GetMapping("/patient/{patientId}")
    public List<Examination> getPatientExaminations(@PathVariable Long patientId) {
        return examinationService.getExaminationsByPatient(patientId);
    }
    @GetMapping("/doctor/{doctorId}/count")
    public Long countDoctorExaminations(@PathVariable Long doctorId) {

        return examinationService.countExaminationsByDoctor(doctorId);
    }
    @GetMapping("/revenue/total")
    public java.math.BigDecimal getTotalRevenue() {

        return examinationService.getTotalRevenue();
    }
    @GetMapping("/revenue/doctor/{doctorId}")
    public java.math.BigDecimal getDoctorRevenue(
            @PathVariable Long doctorId) {

        return examinationService.getRevenueByDoctor(doctorId);
    }
    @GetMapping("/paged")
    public Page<Examination> getPagedExaminations(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return examinationService.getAllExaminations(pageable);
    }

    @GetMapping("/date-range")
    public List<Examination> getExaminationsBetweenDates(

            @RequestParam LocalDate startDate,

            @RequestParam LocalDate endDate
    ) {

        return examinationService
                .getExaminationsBetweenDates(startDate, endDate);
    }

    @GetMapping("/statistics/most-common-diagnosis")
    public String getMostCommonDiagnosis() {

        return examinationService.getMostCommonDiagnosis();
    }
    @PutMapping("/{id}")
    public Examination updateExamination(
            @PathVariable Long id,
            @RequestBody Examination examination) {

        return examinationService
                .updateExamination(id, examination);
    }
}