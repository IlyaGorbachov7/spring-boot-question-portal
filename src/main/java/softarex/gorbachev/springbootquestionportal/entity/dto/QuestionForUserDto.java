package softarex.gorbachev.springbootquestionportal.entity.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionForUserDto {
    private UUID id;

    private String questionText;

    private String answerText;

    private String answerType; // relation entity

    private String options;

    private String emailForUser; // relation entity
}
