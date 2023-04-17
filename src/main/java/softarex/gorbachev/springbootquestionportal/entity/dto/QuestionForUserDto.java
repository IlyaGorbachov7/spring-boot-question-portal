package softarex.gorbachev.springbootquestionportal.entity.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import softarex.gorbachev.springbootquestionportal.constant.valid.OnCreate;
import softarex.gorbachev.springbootquestionportal.constant.valid.OnUpdate;

import java.util.UUID;

import static softarex.gorbachev.springbootquestionportal.constant.ValidationConstants.*;
import static softarex.gorbachev.springbootquestionportal.constant.ValidationConstants.REGEX_EMAIL;

@Data
public class QuestionForUserDto {

    @Null(groups = OnCreate.class, message = MSG_QUEST_ID_NOT_NULL)
    @NotNull(groups = OnUpdate.class, message = MSG_QUEST_ID_IS_NULL)
    private UUID id;

    @NotBlank(message = MSG_NOT_EMPTY)
    @Size(max = MAX_SIZE_QUESTTEXT, message = MSG_MAX_SIZE_QUESTTEXT)
    private String questionText;

    @Size(max = MAX_SIZE_ANSWERTEXT, message = MSG_MAX_SIZE_ANSWERTEXT)
    @NotNull(message = MSG_NOT_NULL)
    private String answerText;

    @NotBlank(message = MSG_NOT_EMPTY)
    private String answerType; // relation entity

    @NotNull(message = MSG_NOT_NULL)
    private String options;

    @NotBlank(message = MSG_NOT_EMPTY)
    @Email(message = MSG_EMAIL, regexp = REGEX_EMAIL)
    private String emailForUser; // relation entity
}
