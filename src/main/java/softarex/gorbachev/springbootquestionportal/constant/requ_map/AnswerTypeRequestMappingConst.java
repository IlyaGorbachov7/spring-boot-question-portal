package softarex.gorbachev.springbootquestionportal.constant.requ_map;

import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.REST_MAIN_URL;

public interface AnswerTypeRequestMappingConst {
    String ANSWER_TYPES_CONTROLLER = REST_MAIN_URL + "/answer-types";

    String ANSWER_TYPES = "/";

    String ANSWER_TYPES_PATHVARIABLE_ID = "{id}";
}
