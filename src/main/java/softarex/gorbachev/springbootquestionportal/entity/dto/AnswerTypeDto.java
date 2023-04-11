package softarex.gorbachev.springbootquestionportal.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static softarex.gorbachev.springbootquestionportal.constant.ValidationConstants.MSG_NOT_EMPTY;

@Data
public class AnswerTypeDto {
    private String id;

    @NotBlank(message = MSG_NOT_EMPTY)
    private String nameType;
}
