package softarex.gorbachev.springbootquestionportal.service.mdls;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value(staticConstructor = "of")
@AllArgsConstructor
public class MessageResponse {
    String msg;
}
