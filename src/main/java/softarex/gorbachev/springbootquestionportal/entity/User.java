package softarex.gorbachev.springbootquestionportal.entity;

import jakarta.persistence.*;
import lombok.*;
import softarex.gorbachev.springbootquestionportal.config.security.Roles;

@Entity
@Table(name = "users", schema = "questportal")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, doNotUseGetters = true)
public class User extends BaseEntity{

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
}

