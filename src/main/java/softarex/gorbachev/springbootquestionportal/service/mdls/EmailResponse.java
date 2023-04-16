package softarex.gorbachev.springbootquestionportal.service.mdls;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class EmailResponse {
    private String prevEmail;

    private String newEmail;
}
