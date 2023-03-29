package softarex.gorbachev.springbootquestionportal.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value(staticConstructor = "of")
@AllArgsConstructor
public class MessageResponse {
    String msg;
}
