package softarex.gorbachev.springbootquestionportal.constant;

public interface ValidationConstants {

    String MSG_EMAIL = "Value this field  should be email\n" +
                       "Example of valid email is\n" +
                       "" +
                       "\tmysite@ourearth.com\n" +
                       "\tmy.ownsite@ourearth.org\n" +
                       "\tmysite@you.me.net";

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

    String MSG_QUEST_ID_NOT_NULL = "The ID must not be specified";

    String MSG_QUEST_ID_IS_NULL = "The ID must be require specified";

    String MSG_NOT_NULL = "Field don't should be null";

    String MSG_NOT_EMPTY = "Fields don't should be empty";

    String MSG_NOT_BLANK = "Fields don't should be blank";

    int MAX_SIZE_QUESTTEXT = 250;

    String MSG_MAX_SIZE_QUESTTEXT = "Max length question text should be " + MAX_SIZE_QUESTTEXT;

    int MAX_SIZE_ANSWERTEXT = 250;

    String MSG_MAX_SIZE_ANSWERTEXT = "Max length answer text should be " + MAX_SIZE_QUESTTEXT;

    String REGEX_NAME = "(^([A-ZА-ЯЁ][a-zа-яё]{2,}))$";

    String MSG_FIRSTNAME = "First name must consist of one word.\n" +
                           "Start with  one uppercase. Remove the extra spaces.";

    int MAX_SIZE_FIRSTNAME = 20;

    String MSG_SIZE_FIRSTNAME = "Max length password must be " + MAX_SIZE_FIRSTNAME;

    String MSG_LASTNAME = "Last name must consist of one word.\n" +
                          "Start with  one uppercase. Remove the extra spaces.";


    int MAX_SIZE_LASTNAME = 20;

    String MSG_SIZE_LASTNAME = "Max length password must be " + MAX_SIZE_LASTNAME;

    int MAX_SIZE_PASSWORD = 20;

    String MSG_SIZE_PASSWORD = "Max length password must be " + MAX_SIZE_PASSWORD;

    int MAX_SIZE_PHONE = 20;

    String MSG_SIZE_PHONE = "Max length password must be "+ MAX_SIZE_PHONE;

}
