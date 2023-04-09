package softarex.gorbachev.springbootquestionportal.constant.requ_map;

import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.REST_MAIN_URL;

public interface QuestionRequestMappingConst {

    String QUESTIONS_CONTROLLER = REST_MAIN_URL + "/questions";

    String QUESTIONS_FROM_ME = "/from-me";

    String QUESTIONS_FROM_ME_QUANTITY = QUESTIONS_FROM_ME + "/quantity";

    String QUESTIONS_FOR_ME = "/for-me";

    String QUESTIONS_FOR_ME_QUANTITY = QUESTIONS_FOR_ME + "/quantity";

}
