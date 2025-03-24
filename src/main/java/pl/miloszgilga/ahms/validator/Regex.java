package pl.miloszgilga.ahms.validator;

public class Regex {
    public final static String LOGIN_EMAIL = "^[a-z0-9.@+-_]{3,100}$";
    public final static String OTA_TOKEN = "^[a-zA-Z0-9]{10}$";
    public final static String PASSWORD_REQUIREMENTS = "^(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%&+=!])(?!.*\\s).{8,30}$";
    public final static String LOGIN = "^[a-z0-9]{3,30}$";
}
