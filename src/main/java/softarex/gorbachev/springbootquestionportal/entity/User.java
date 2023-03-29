package softarex.gorbachev.springbootquestionportal.entity;

import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.*;
import lombok.*;
import softarex.gorbachev.springbootquestionportal.model.Roles;

@Entity
@Table(name = "users", schema = "questportal")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    @Column(name = "password", nullable = false)
    private String password;

    @ToString.Exclude
    @Column(name = "first_name", length = 50)
    private String firstName;

    @ToString.Exclude
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @ToString.Exclude
    @Column(name = "phone", length = 25)
    private String phone;

    @Transient
    @ToString.Exclude
    private final Roles roles = Roles.USER;

    public User(String password, String firstName, String lastName, String email, String phone) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return email;
    }
}

