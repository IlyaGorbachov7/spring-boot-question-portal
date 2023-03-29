package softarex.gorbachev.springbootquestionportal.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "questions", schema = "questportal")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    @Column(name = "question")
    private String questionText = "";

    @Column(name = "answer")
    private String answerText = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_type_id", nullable = false)
    @ToString.Exclude
    private AnswerType answerType;

    @Column(name = "answer_options")
    private String answerOptions = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user", nullable = false)
    @ToString.Exclude
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "for_user", nullable = false)
    @ToString.Exclude
    private User forUser;

    public Question(String questionText, String answerText, AnswerType answerType, String answerOptions, User fromUser, User forUser) {
        this.questionText = questionText;
        this.answerText = answerText;
        this.answerType = answerType;
        this.answerOptions = answerOptions;
        this.fromUser = fromUser;
        this.forUser = forUser;
    }
}
