package team.jlpt.config;

public class PasswordConfig {
    private final static String allowNumber = "(?=.*[0-9])";
    private final static String allowEnglish = "(?=.*[a-z])";
    private final static String allowSpecial = "(?=.*[!\"#$%&'()+,-./:;<=>?@^_`{|}\\[\\]~\\\\])(?=\\S+$)";
    private final static String NotAllowSpace = "(?=\\S+$)";
    public final static String passwordRegexp = "^"+allowNumber+allowEnglish+allowSpecial+NotAllowSpace+".{5,128}$";
}
