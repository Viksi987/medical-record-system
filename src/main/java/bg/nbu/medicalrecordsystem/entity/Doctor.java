package bg.nbu.medicalrecordsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Doctor name is required")
    private String fullName;

    @Column(nullable = false)
    @NotBlank(message = "Specialty is required")
    private String specialty;

    @Column(nullable = false)
    private Boolean canBePersonalDoctor;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}