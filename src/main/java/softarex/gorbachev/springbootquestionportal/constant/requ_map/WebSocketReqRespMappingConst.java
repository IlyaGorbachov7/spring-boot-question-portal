package softarex.gorbachev.springbootquestionportal.constant.requ_map;

public interface WebSocketReqRespMappingConst {
    //---------------------- Socket Configuration ---------------------------
    String ENDPOINT_HANDSHAKE = "/ws";

    String APP_DES_PREFIX = "/quest-portal";

    String SIM_BROKER_PUBLIC = "/public";

    String SIM_BROKER_PRIVATE = "/private";

    // --------------------- UserRestController.class -----------------------
    String PUB_USER_CRUD = "/public/users/crud";

    String FROMEMAIL = "from-email";

    String PRV_USER_FROMEMAIL = "/private/user/{" + FROMEMAIL + "}";

    String PRV_USER_SUBSCRIBEME = "/private/user/subscribe-me";

    String PRV_USER_UNSUBSCRIBEME = "/private/user/unsubscribe-me";

    String PRV_FOREMAIL_SUBSCRIBEME = "/private/%s/subscribe-me";

    String PRV_FOREMAIL_UNSUBSCRIBEME = "/private/%s/subscribe-me";


    //--------------------- QuestionRestController.class --------------------
    String PRV_QUEST_CRUD = "/private/questions/crud";

    String PRV_EMAIL_QUEST_CRUD = "/private/%s/question/crud";
}
