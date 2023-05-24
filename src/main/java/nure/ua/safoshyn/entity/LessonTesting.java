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
    @Column(name = "value", nullable = false)
    private double value;

    @ManyToOne(optional = false)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;
}
