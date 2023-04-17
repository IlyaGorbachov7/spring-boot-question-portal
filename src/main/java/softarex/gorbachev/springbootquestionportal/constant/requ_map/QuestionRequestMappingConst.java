package softarex.gorbachev.springbootquestionportal.constant.requ_map;

import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.REST_MAIN_URL;

public interface QuestionRequestMappingConst {

    String QUESTIONS_CONTROLLER = REST_MAIN_URL + "/questions";

    String QUESTIONS = "/";

    String QUESTIONS_FROM_ME = "/from-me";

    String QUESTIONS_FROM_ME_ALL = QUESTIONS_FROM_ME + "/all";

    String QUESTIONS_FROM_ME_QUANTITY = QUESTIONS_FROM_ME + "/quantity";

    String PV_FOREMAIL = "for-email";

    String QUESTIONS_FROM_ME_PVFOREMAIL_QUANTITY = QUESTIONS_FROM_ME + "/{" + PV_FOREMAIL + "}/quantity";

    String QUESTIONS_ID_PV = QUESTIONS + "{id}";

    String QUESTIONS_FOR_ME = "/for-me";

    String QUESTIONS_FOR_ME_ALL = QUESTIONS_FOR_ME + "/all";

    String QUESTIONS_FOR_ME_QUANTITY = QUESTIONS_FOR_ME + "/quantity";

}
