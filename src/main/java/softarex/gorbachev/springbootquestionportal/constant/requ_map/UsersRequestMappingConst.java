package softarex.gorbachev.springbootquestionportal.constant.requ_map;

import softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant;

public class UsersRequestMappingConst {
    public final static  String USERS_CONTROLLER = CommonAppConstant.REST_MAIN_URL + "/users";

    public final static String USERS_REGISTER = "/register";

    public final static String USERS_LOGIN = "/login";

    public final static String USERS_CURUSER="/cur-user";

    public final static String USERS_RESETPASSWORD="/reset-password";

    public final static String USERS_CHANGEPASSWORD="/change-password";
}
