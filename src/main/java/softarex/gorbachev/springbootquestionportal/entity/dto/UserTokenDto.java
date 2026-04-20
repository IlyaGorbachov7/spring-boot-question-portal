package softarex.gorbachev.springbootquestionportal.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

import static softarex.gorbachev.springbootquestionportal.constant.ValidationConstants.MSG_NOT_EMPTY;

@Data
public class UserTokenDto {
	@NotNull
	@NotBlank(message = MSG_NOT_EMPTY)
	private UUID id;

	@NotNull
	@NotBlank(message = MSG_NOT_EMPTY)
	private String token;
}
