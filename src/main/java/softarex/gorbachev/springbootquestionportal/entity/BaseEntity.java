package softarex.gorbachev.springbootquestionportal.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString(doNotUseGetters = true)
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @Setter(value = AccessLevel.PRIVATE)
    private String id;

}
