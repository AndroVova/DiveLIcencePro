package nure.ua.safoshyn.diveLicencePro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "dive_club")
public class DiveClub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Diving Club name cannot be blank")
    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Diving Club name cannot be blank")
    @NonNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank(message = "Diving Club name cannot be blank")
    @NonNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotBlank(message = "Diving Club name cannot be blank")
    @NonNull
    @Column(name = "country", nullable = false)
    private String country;

    @JsonIgnore
    @OneToMany(mappedBy = "diveClub", cascade = CascadeType.ALL)
    private List<CustomUser> userList;

}
/*
* id
* name
* address
* city
* country
* */