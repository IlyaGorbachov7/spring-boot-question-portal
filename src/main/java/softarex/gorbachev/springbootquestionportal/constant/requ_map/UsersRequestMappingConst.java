package softarex.gorbachev.springbootquestionportal.constant.requ_map;

import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.REST_MAIN_URL;

public interface UsersRequestMappingConst {
    String USERS_CONTROLLER = REST_MAIN_URL + "/users";

    String USERS_REGISTER = "/register";

    String USERS_LOGIN = "/login";

    String USERS_CURUSER = "/cur-user";

    String USERS_RESETPASSWORD = "/reset-password";

    String USERS_CHANGEPASSWORD = "/change-password";

    String USERS_EMAILS = "/emails";
}
