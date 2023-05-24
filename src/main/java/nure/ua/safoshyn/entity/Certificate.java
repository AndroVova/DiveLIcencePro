package nure.ua.safoshyn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "certificate")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Name cannot be blank")
    @NonNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "custom_user_id", referencedColumnName = "id", nullable = true)
    private CustomUser customUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private CustomUser instructor;

}
/*
* id
* userId
* clubId
* instructorId
* dateOfReseiving
* CertificateName
* */