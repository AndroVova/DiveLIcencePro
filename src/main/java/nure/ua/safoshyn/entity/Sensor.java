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
    @Column(name = "max_heart_rate_value", nullable = false)
    private double maxHeartRateValue;

    @NotNull
    @Column(name = "max_depth", nullable = false)
    private double maxDepth;

    @NotNull
    @Column(name = "max_time", nullable = false)
    private Long maxTime;

    @JsonIgnore
    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private List<LessonTesting> lessonTestings;
}
