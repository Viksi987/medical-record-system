package bg.nbu.medicalrecordsystem.controller;

import bg.nbu.medicalrecordsystem.entity.SickLeave;
import bg.nbu.medicalrecordsystem.service.SickLeaveService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sick-leaves")
public class SickLeaveController {

    private final SickLeaveService sickLeaveService;

    public SickLeaveController(SickLeaveService sickLeaveService) {
        this.sickLeaveService = sickLeaveService;
    }

    @PostMapping
    public SickLeave createSickLeave(@Valid @RequestBody SickLeave sickLeave) {
        return sickLeaveService.saveSickLeave(sickLeave);
    }

    @GetMapping
    public List<SickLeave> getAllSickLeaves() {
        return sickLeaveService.getAllSickLeaves();
    }

    @GetMapping("/{id}")
    public SickLeave getSickLeaveById(@PathVariable Long id) {
        return sickLeaveService.getSickLeaveById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteSickLeave(@PathVariable Long id) {
        sickLeaveService.deleteSickLeave(id);
    }
}