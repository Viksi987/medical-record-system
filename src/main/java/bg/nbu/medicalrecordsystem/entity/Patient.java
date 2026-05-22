package bg.nbu.medicalrecordsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Column(nullable = false, unique = true, length = 10)
    @NotBlank(message = "EGN is required")
    @Size(min = 10, max = 10, message = "EGN must be exactly 10 digits")
    private String egn;

    @Column(nullable = false)
    private Boolean insured;

    @ManyToOne
    @JoinColumn(name = "personal_doctor_id")
    private Doctor personalDoctor;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}