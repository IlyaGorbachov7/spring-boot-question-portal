package softarex.gorbachev.springbootquestionportal.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "answer_type", schema = "questportal")
@NoArgsConstructor
@Data
public class AnswerType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    @Column(name = "name_type", unique = true, nullable = false)
    private String nameType;
}
