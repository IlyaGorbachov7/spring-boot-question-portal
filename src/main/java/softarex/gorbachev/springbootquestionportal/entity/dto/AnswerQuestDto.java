package softarex.gorbachev.springbootquestionportal.entity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import softarex.gorbachev.springbootquestionportal.constant.valid.OnCreate;
import softarex.gorbachev.springbootquestionportal.constant.valid.OnUpdate;

import java.util.UUID;

import static softarex.gorbachev.springbootquestionportal.constant.ValidationConstants.*;

@Data
public class AnswerQuestDto {

    @Null(groups = OnCreate.class, message = MSG_QUEST_ID_NOT_NULL)
    @NotNull(groups = OnUpdate.class, message = MSG_QUEST_ID_IS_NULL)
    private UUID id;

    @Size(max = MAX_SIZE_ANSWERTEXT, message = MSG_MAX_SIZE_ANSWERTEXT)
    @NotNull(message = MSG_NOT_NULL)
    private String answerText;
}
