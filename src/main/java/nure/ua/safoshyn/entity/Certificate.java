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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Name cannot be blank")
    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Name cannot be blank")
    @NonNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotBlank(message = "number_of_successful_lessons_to_get cannot be blank")
    @NonNull
    @Column(name = "number_of_successful_lessons_to_get", nullable = false)
    private int numberOfSuccessfulLessonsToGet;

    @NotBlank(message = "isCompleted field cannot be blank")
    @NonNull
    @Column(name = "isCompleted", nullable = false)
    private Boolean isCompleted = false;

    @ManyToOne
    @JoinColumn(name = "custom_user_id", referencedColumnName = "id", nullable = true)
    private CustomUser customUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id", nullable = true)
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