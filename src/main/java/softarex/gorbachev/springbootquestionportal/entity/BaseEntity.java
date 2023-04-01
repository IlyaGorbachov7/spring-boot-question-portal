package softarex.gorbachev.springbootquestionportal.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(doNotUseGetters = true)
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

}
