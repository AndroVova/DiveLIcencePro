package nure.ua.safoshyn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @Column(name = "task", nullable = false)
    private String task;

    @NonNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NonNull
    @Column(name = "duration", nullable = false)
    private int duration;

    @NonNull
    @Column(name = "is_successful")
    private Boolean isSuccessful = false;

    @JsonIgnore
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<LessonTesting> lessonTestings;

    @ManyToOne
    @JoinColumn(name = "custom_user_id", referencedColumnName = "id", nullable = true)
    private CustomUser customUser;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lesson_instructor_id", referencedColumnName = "id", nullable = true)
    private CustomUser instructor;

}
/*
 * id+
 * date+
 * duration+
 * stud ID+
 * instr ID+
 */