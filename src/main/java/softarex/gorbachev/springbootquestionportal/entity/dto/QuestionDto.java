package softarex.gorbachev.springbootquestionportal.entity.dto;

import lombok.Data;

@Data
public class QuestionDto {

    private String id;

    private String questionText;

    private String answerText;

    private String answerType; // relation entity

    private String options;

    private String emailFromUser; // relation entity

    private String emailForUser; // relation entity
}
