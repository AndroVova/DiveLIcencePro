package nure.ua.safoshyn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "sensor")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Name cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private double studentPulse;

    @JsonIgnore
    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private List<LessonTesting> lessonTestings;
}
