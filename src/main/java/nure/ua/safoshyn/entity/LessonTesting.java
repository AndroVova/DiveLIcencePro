package nure.ua.safoshyn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
//@NoArgsConstructor(force = true)
@Entity
@Table(name = "lesson_testing")
public class LessonTesting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "heart_rate_value", nullable = false)
    private double heartRateValue;

    @NotNull
    @Column(name = "depth", nullable = false)
    private double depth;

    @NotNull
    @Column(name = "time", nullable = false)
    private Long time;

    @ManyToOne(optional = false)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id", nullable = true)
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id", nullable = true)
    private Sensor sensor;
}
