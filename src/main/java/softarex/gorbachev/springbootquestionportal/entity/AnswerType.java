package softarex.gorbachev.springbootquestionportal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "answer_type", schema = "questportal")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true, doNotUseGetters = true)
public class AnswerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_type", unique = true, nullable = false)
    private String nameType;
}
