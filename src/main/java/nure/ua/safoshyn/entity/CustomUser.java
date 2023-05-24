package nure.ua.safoshyn.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "custom_user")
public class CustomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @Past(message = "The birth date must be in the past")
    @NonNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotBlank(message = "Role cannot be blank")
    @NonNull
    @Column(name = "role", nullable = false)
    private String role;

    @NotBlank(message = "Place of residence cannot be blank")
    @NonNull
    @Column(name = "place_of_residence", nullable = false)
    private String placeOfResidence;

    @ManyToOne
    @JoinColumn(name = "dive_club_id", referencedColumnName = "id", nullable = true)
    private DiveClub diveClub;

    @JsonIgnore
    @OneToMany(mappedBy = "customUser", cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    @JsonIgnore
    @OneToMany(mappedBy = "customUser", cascade = CascadeType.ALL)
    private List<Certificate> certificates;

    @JsonIgnore
    @OneToOne(mappedBy = "customUser", cascade = CascadeType.ALL)
    private Profile profile;

    @JsonIgnore
    @OneToOne(mappedBy = "instructor", cascade = CascadeType.ALL)
    private Certificate certificate_instructor;

    @JsonIgnore
    @OneToOne(mappedBy = "instructor", cascade = CascadeType.ALL)
    private Lesson lesson_instructor;
}
/*
* id +
* name +
* date of birth +
* placeOfResidence +
* clubID +
* profileID +
* */