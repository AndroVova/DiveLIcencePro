package nure.ua.safoshyn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email cannot be blank")
    @NonNull
    @Email(message = "Email must be a valid email address")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "password cannot be blank")
    @NonNull
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "custom_user_id", referencedColumnName = "id")
    private CustomUser customUser;
}
