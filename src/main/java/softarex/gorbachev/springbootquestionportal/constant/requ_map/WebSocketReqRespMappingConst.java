package softarex.gorbachev.springbootquestionportal.constant.requ_map;

public interface WebSocketReqRespMappingConst {
    // --------------------- UserRestController.class ------------------------
    String PUB_USER_CRUD = "/public/users/crud";

    String FROMEMAIL = "from-email";

    String PRV_USER_FROMEMAIL = "/private/user/{" + FROMEMAIL + "}";

    String PRV_USER_SUBSCRIBEME = "/private/user/subscribe-me";

    String PRV_USER_UNSUBSCRIBEME = "/private/user/unsubscribe-me";

    String PRV_FOREMAIL_SUBSCRIBEME = "/private/%s/subscribe-me";

    String PRV_FOREMAIL_UNSUBSCRIBEME = "/private/%s/subscribe-me";
}
