package softarex.gorbachev.springbootquestionportal.constant;

public interface ValidationConstants {

    String MSG_EMAIL = "Value this field  should be email\n" +
                       "Example of valid email id\n" +
                       "    mysite@ourearth.com</i>\n" +
                       "    my.ownsite@ourearth.org</i>\n" +
                       "    mysite@you.me.net</i>";

    /**
     * Example of valid email id
     * <p>
     * <i>mysite@ourearth.com</i>
     * <p>
     * <i>my.ownsite@ourearth.org</i>
     * <p>
     * <i>mysite@you.me.net</i>
     */
    String REGEX_EMAIL = "^\\w+([\\.\\-]?\\w+)*@\\w+([\\.\\-]?\\w+)*(\\.\\w{2,4})+$";

    String MSG_URL = "Value this filed should be URL";

    String MSG_USER_ID_IS_NULL = "The ID must be require specified";

    String MSG_NOT_NULL = "Field don't should be null";

    String MSG_NOT_EMPTY = "Fields don't should be empty";

    String MSG_NOT_BLANK = "Fields don't should be blank";

}
