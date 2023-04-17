package softarex.gorbachev.springbootquestionportal.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "questions", schema = "questportal")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
public class Question extends BaseEntity {

    @Column(name = "question")
    private String questionText = "";

    @Column(name = "answer")
    private String answerText = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_type_id", nullable = false)
    @ToString.Exclude
    private AnswerType answerType;

    @Column(name = "answer_options")
    private String options = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user", nullable = false)
    @ToString.Exclude
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "for_user", nullable = true)
    @ToString.Exclude
    private User forUser;
}
