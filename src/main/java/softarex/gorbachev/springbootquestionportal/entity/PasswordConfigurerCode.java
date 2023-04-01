package softarex.gorbachev.springbootquestionportal.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "password_configurer_code", schema = "questportal")
public class PasswordConfigurerCode extends BaseEntity{

    @Column(name = "code", unique = true)
    private String code;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(name = "expiry_datetime")
    private LocalDateTime expiryDateTime;
}
