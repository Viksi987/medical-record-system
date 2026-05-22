package bg.nbu.medicalrecordsystem.controller;

import bg.nbu.medicalrecordsystem.entity.Patient;
import bg.nbu.medicalrecordsystem.service.DoctorService;
import bg.nbu.medicalrecordsystem.service.ExaminationService;
import bg.nbu.medicalrecordsystem.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import bg.nbu.medicalrecordsystem.service.SickLeaveService;
import bg.nbu.medicalrecordsystem.entity.SickLeave;
import bg.nbu.medicalrecordsystem.service.SickLeaveService;
import bg.nbu.medicalrecordsystem.entity.Doctor;
import bg.nbu.medicalrecordsystem.entity.Examination;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import bg.nbu.medicalrecordsystem.entity.User;
import bg.nbu.medicalrecordsystem.entity.Role;
import bg.nbu.medicalrecordsystem.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import bg.nbu.medicalrecordsystem.entity.Role;
import bg.nbu.medicalrecordsystem.entity.Patient;
import bg.nbu.medicalrecordsystem.entity.Doctor;
import java.util.List;
import java.time.LocalDate;
@Controller
public class ViewController {

    private final PatientService patientService;

    private final DoctorService doctorService;

    private final ExaminationService examinationService;

    private final SickLeaveService sickLeaveService;

    private final UserService userService;

    public ViewController(
            PatientService patientService,
            DoctorService doctorService,
            ExaminationService examinationService,
            SickLeaveService sickLeaveService,UserService userService)
    {

        this.patientService = patientService;
        this.doctorService = doctorService;
        this.examinationService = examinationService;
        this.sickLeaveService = sickLeaveService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "patientsCount",
                patientService.getAllPatients().size()
        );

        model.addAttribute(
                "doctorsCount",
                doctorService.getAllDoctors().size()
        );

        model.addAttribute(
                "examinationsCount",
                examinationService.getAllExaminations().size()
        );

        model.addAttribute(
                "mostCommonDiagnosis",
                examinationService.getMostCommonDiagnosis()
        );

        model.addAttribute(
                "fluCount",
                examinationService.countByDiagnosis("Flu")
        );

        model.addAttribute(
                "covidCount",
                examinationService.countByDiagnosis("COVID")
        );

        model.addAttribute(
                "diabetesCount",
                examinationService.countByDiagnosis("Diabetes")
        );

        model.addAttribute(
                "totalRevenue",
                examinationService.getTotalRevenue()
        );

        return "index";
    }

    @GetMapping("/patients-page")
    public String patientsPage(Model model) {

        model.addAttribute(
                "patients",
                patientService.getAllPatients()
        );

        return "patients";
    }

    @GetMapping("/add-patient")
    public String addPatientPage(Model model) {

        model.addAttribute(
                "patient",
                new Patient()
        );

        model.addAttribute(
                "doctors",
                doctorService.getAllDoctors()
        );

        return "add-patient";
    }

    @PostMapping("/save-patient")
    public String savePatient(Patient patient) {

        patientService.savePatient(patient);

        return "redirect:/patients-page";
    }

    @GetMapping("/delete-patient/{id}")
    public String deletePatient(@PathVariable Long id) {

        patientService.deletePatient(id);

        return "redirect:/patients-page";
    }

    @GetMapping("/edit-patient/{id}")
    public String editPatientPage(
            @PathVariable Long id,
            Model model) {

        Patient patient =
                patientService.getPatientById(id);

        model.addAttribute(
                "patient",
                patient
        );

        model.addAttribute(
                "doctors",
                doctorService.getAllDoctors()
        );

        return "edit-patient";
    }

    @PostMapping("/update-patient")
    public String updatePatient(Patient patient) {

        patientService.savePatient(patient);

        return "redirect:/patients-page";
    }

    @GetMapping("/doctors-page")
    public String doctorsPage(Model model) {

        model.addAttribute(
                "doctors",
                doctorService.getAllDoctors()
        );

        return "doctors";
    }

    @GetMapping("/examinations-page")
    public String examinationsPage(Model model) {

        model.addAttribute(
                "examinations",
                examinationService.getAllExaminations()
        );

        return "examinations";
    }

    @GetMapping("/patient-history/{id}")
    public String patientHistory(@PathVariable Long id,
                                 Model model) {

        Patient patient = patientService.getPatientById(id);

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String loggedUsername =
                authentication.getName();

        boolean isAdmin =
                authentication.getAuthorities()
                        .stream()
                        .anyMatch(a ->
                                a.getAuthority()
                                        .equals("ROLE_ADMIN"));

        boolean isDoctor =
                authentication.getAuthorities()
                        .stream()
                        .anyMatch(a ->
                                a.getAuthority()
                                        .equals("ROLE_DOCTOR"));

        if (isAdmin || isDoctor) {

            model.addAttribute("patient", patient);

            model.addAttribute(
                    "examinations",
                    examinationService
                            .getExaminationsByPatient(id)
            );

            return "patient-history";
        }

        if (patient.getUser() != null &&
                patient.getUser()
                        .getUsername()
                        .equals(loggedUsername)) {

            model.addAttribute("patient", patient);

            model.addAttribute(
                    "examinations",
                    examinationService
                            .getExaminationsByPatient(id)
            );

            return "patient-history";
        }

        return "redirect:/";
    }

    @GetMapping("/sick-leaves-page")
    public String sickLeavesPage(Model model) {

        model.addAttribute(
                "sickLeaves",
                sickLeaveService.getAllSickLeaves()
        );

        return "sick-leaves";
    }
    @GetMapping("/add-sick-leave")
    public String addSickLeavePage(Model model) {

        model.addAttribute("sickLeave",
                new SickLeave());

        model.addAttribute("patients",
                patientService.getAllPatients());

        return "add-sick-leave";
    }

    @PostMapping("/save-sick-leave")
    public String saveSickLeave(SickLeave sickLeave) {

        sickLeaveService.saveSickLeave(sickLeave);

        return "redirect:/sick-leaves-page";
    }
    @GetMapping("/add-doctor")
    public String addDoctorPage(Model model) {

        model.addAttribute("doctor", new Doctor());

        return "add-doctor";
    }

    @PostMapping("/save-doctor")
    public String saveDoctor(Doctor doctor) {

        doctorService.saveDoctor(doctor);

        return "redirect:/doctors-page";
    }
    @GetMapping("/add-examination")
    public String addExaminationPage(Model model) {

        model.addAttribute(
                "examination",
                new Examination()
        );

        return "add-examination";
    }

    @PostMapping("/save-examination")
    public String saveExamination(Examination examination) {

        examinationService.saveExamination(examination);

        return "redirect:/examinations-page";

    }
    @GetMapping("/delete-doctor/{id}")
    public String deleteDoctor(@PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return "redirect:/doctors-page";
    }

    @GetMapping("/edit-doctor/{id}")
    public String editDoctorPage(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "doctor",
                doctorService.getDoctorById(id)
        );

        return "edit-doctor";
    }

    @PostMapping("/update-doctor")
    public String updateDoctor(Doctor doctor) {

        doctorService.saveDoctor(doctor);

        return "redirect:/doctors-page";
    }
    @GetMapping("/delete-examination/{id}")
    public String deleteExamination(@PathVariable Long id) {

        examinationService.deleteExamination(id);

        return "redirect:/examinations-page";
    }

    @GetMapping("/edit-examination/{id}")
    public String editExaminationPage(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "examination",
                examinationService.getExaminationById(id)
        );

        return "edit-examination";
    }

    @PostMapping("/update-examination")
    public String updateExamination(Examination examination) {

        examinationService.saveExamination(examination);

        return "redirect:/examinations-page";
    }
    @GetMapping("/delete-sick-leave/{id}")
    public String deleteSickLeave(@PathVariable Long id) {

        sickLeaveService.deleteSickLeave(id);

        return "redirect:/sick-leaves-page";
    }

    @GetMapping("/edit-sick-leave/{id}")
    public String editSickLeavePage(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "sickLeave",
                sickLeaveService.getSickLeaveById(id)
        );

        return "edit-sick-leave";
    }

    @PostMapping("/update-sick-leave")
    public String updateSickLeave(SickLeave sickLeave) {

        sickLeaveService.saveSickLeave(sickLeave);

        return "redirect:/sick-leaves-page";
    }
    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }
    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("user", new User());

        model.addAttribute(
                "doctors",
                doctorService.getAllDoctors()
        );

        return "register";
    }
    @PostMapping("/save-user")
    public String saveUser(User user,
                           @RequestParam String fullName,
                           @RequestParam String egn,
                           @RequestParam(required = false)
                           Boolean insured,
                           @RequestParam Long doctorId) {

        userService.saveUser(user);

        if (user.getRole() == Role.PATIENT) {

            Doctor doctor = doctorService
                    .getDoctorById(doctorId);

            Patient patient = new Patient();

            patient.setFullName(fullName);

            patient.setEgn(egn);

            patient.setInsured(
                    insured != null
            );

            patient.setPersonalDoctor(doctor);

            patient.setUser(user);

            patientService.savePatient(patient);
        }

        return "redirect:/login";
    }
    @GetMapping("/my-history")
    public String myHistory() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String username = authentication.getName();

        Patient patient =
                patientService.findByUsername(username);

        return "redirect:/patient-history/" + patient.getId();
    }
    @GetMapping("/patients-by-diagnosis")
    public String patientsByDiagnosis(
            @RequestParam(required = false)
            String diagnosis,
            Model model) {

        if (diagnosis != null &&
                !diagnosis.isEmpty()) {

            model.addAttribute(
                    "patients",
                    examinationService
                            .getPatientsByDiagnosis(
                                    diagnosis
                            )
            );
        }

        return "patients-by-diagnosis";
    }
    @GetMapping("/patients-by-doctor")
    public String patientsByDoctor(
            @RequestParam(required = false)
            Long doctorId,
            Model model) {

        model.addAttribute(
                "doctors",
                doctorService.getAllDoctors()
        );

        if (doctorId != null) {

            model.addAttribute(
                    "patients",
                    patientService
                            .getPatientsByDoctor(
                                    doctorId
                            )
            );
        }

        return "patients-by-doctor";
    }
    @GetMapping("/revenue-by-doctor")
    public String revenueByDoctor(
            @RequestParam(required = false)
            Long doctorId,
            Model model) {

        model.addAttribute(
                "doctors",
                doctorService.getAllDoctors()
        );

        if (doctorId != null) {

            model.addAttribute(
                    "revenue",
                    examinationService
                            .getRevenueByDoctor(
                                    doctorId
                            )
            );

            model.addAttribute(
                    "selectedDoctor",
                    doctorService
                            .getDoctorById(
                                    doctorId
                            )
            );
        }

        return "revenue-by-doctor";
    }
    @GetMapping("/patients-count-by-doctor")
    public String patientsCountByDoctor(
            @RequestParam(required = false)
            Long doctorId,
            Model model) {

        model.addAttribute(
                "doctors",
                doctorService.getAllDoctors()
        );

        if (doctorId != null) {

            model.addAttribute(
                    "count",
                    patientService
                            .countPatientsByDoctor(
                                    doctorId
                            )
            );

            model.addAttribute(
                    "selectedDoctor",
                    doctorService
                            .getDoctorById(
                                    doctorId
                            )
            );
        }

        return "patients-count-by-doctor";
    }
    @GetMapping("/doctor-visits")
    public String doctorVisits(Model model) {

        model.addAttribute(
                "visits",
                examinationService.getVisitsCountByDoctor()
        );

        return "doctor-visits";
    }
    @GetMapping("/filter-examinations")
    public String filterExaminations(
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Model model
    ) {

        LocalDate start = null;
        LocalDate end = null;

        if (startDate != null && !startDate.isEmpty()) {
            start = LocalDate.parse(startDate);
        }

        if (endDate != null && !endDate.isEmpty()) {
            end = LocalDate.parse(endDate);
        }

        model.addAttribute(
                "examinations",
                examinationService.filterExaminations(
                        doctorId,
                        start,
                        end
                )
        );

        model.addAttribute(
                "doctors",
                doctorService.getAllDoctors()
        );

        return "filter-examinations";
    }
    @GetMapping("/sick-leaves-by-month")
    public String sickLeavesByMonth(Model model) {

        model.addAttribute(
                "months",
                sickLeaveService.getSickLeavesByMonth()
        );

        return "sick-leaves-by-month";
    }
    @GetMapping("/top-doctors-sick-leaves")
    public String topDoctorsSickLeaves(Model model) {

        model.addAttribute(
                "doctors",
                sickLeaveService
                        .getDoctorsWithMostSickLeaves()
        );

        return "top-doctors-sick-leaves";
    }
}