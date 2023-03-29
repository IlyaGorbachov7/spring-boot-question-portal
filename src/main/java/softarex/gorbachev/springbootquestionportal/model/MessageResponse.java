package softarex.gorbachev.springbootquestionportal.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class MessageResponse {
    String msg;
}
