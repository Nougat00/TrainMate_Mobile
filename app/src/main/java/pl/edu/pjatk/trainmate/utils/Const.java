package pl.edu.pjatk.trainmate.utils;

/**
 * A utility class that holds constant values used throughout the application.
 * These constants include preference keys, API endpoints, error messages, and
 * other string literals used in the application.
 */
public class Const {

    // Preference keys

    public static final String REFRESH_ACTIVE = "refreshActive";
    public static final String PREFS_NAME = "preferences";
    public static final String PREF_UNAME = "Username";
    public static final String PREF_PASSWORD = "Password";
    public static final String PREF_ACCESS_TOKEN = "AccessToken";
    public static final String PREF_REFRESH_TOKEN = "RefreshToken";
    public static final String CLIENT_ID = "train-mate";
    public static final String DEFAULT_STRING_VALUE = "";
    public static final String LOCAL_KEYCLOAK_URL = "http://10.0.2.2:8123";
    public static final String LOCAL_API_URL = "http://10.0.2.2:8081/api/tm-core/";
    public static final String TOKEN_REFRESH_GRANT_TYPE = "refresh_token";
    public static final String GRANT_TYPE = "password";
    public static final String REFRESH_TAG = "REFRESH TOKEN";
    public static final String REFRESH_TOKEN_FAIL = "Token refresh malfunction";
    public static final String LOGIN_FAIL_ANNOUNCEMENT = "Incorrect login or password";
    public static final String TOKEN_TYPE = "Bearer ";
    public static final String API_FAIL = "Data downloading from API malfunction: ";
    public static final String API_TAG = "API FAIL";
    public static final String REPS_TEXT = "Reps: ";
    public static final String WEIGHT_TEXT = " | Weight: ";
    public static final String SETS_TEXT = " | Sets: ";
    public static final String TEMPO_TEXT = " | Tempo: ";
    public static final String RIR_TEXT = " | Rir: ";
    public static final String ADD_REPORT_BUTTON_TEXT = "Add report";
    public static final String CLOSE_REPORT_BUTTON_TEXT = "Close report";
    public static final String WEIGHT_FOR_SERIES_TEXT = "Weight series: ";
    public static final String REPS_FOR_SERIES_TEXT = "Reps series: ";
    public static final String RIR_FOR_SERIES_TEXT = "RIR series: ";
    public static final String REMARKS_TEXT = "Remarks for all training unit";
    public static final String EMPTY_FIELD_ERROR = "Field cannot be empty";
    public static final String NEGATIVE_NUMBER_ERROR = "Number cannot be negative";
    public static final String INVALID_NUMBER_ERROR = "Invalid number format";
    public static final char DASH = '-';
    public static final String[] BODY_PARTS = {
        "Weight", "Body Fat", "Left Biceps", "Right Biceps", "Left Forearm", "Right Forearm",
        "Left Thigh", "Right Thigh", "Left Calf", "Right Calf", "Shoulders", "Chest", "Waist",
        "Abdomen", "Hips"
    };
    public static final String BODY_PART_WEIGHT = "Weight";
    public static final String BODY_PART_BODY_FAT = "Body Fat";
    public static final String BODY_PART_LEFT_BICEPS = "Left Biceps";
    public static final String BODY_PART_RIGHT_BICEPS = "Right Biceps";
    public static final String BODY_PART_LEFT_FOREARM = "Left Forearm";
    public static final String BODY_PART_RIGHT_FOREARM = "Right Forearm";
    public static final String BODY_PART_LEFT_THIGH = "Left Thigh";
    public static final String BODY_PART_RIGHT_THIGH = "Right Thigh";
    public static final String BODY_PART_LEFT_CALF = "Left Calf";
    public static final String BODY_PART_RIGHT_CALF = "Right Calf";
    public static final String BODY_PART_SHOULDERS = "Shoulders";
    public static final String BODY_PART_CHEST = "Chest";
    public static final String BODY_PART_WAIST = "Waist";
    public static final String BODY_PART_ABDOMEN = "Abdomen";
    public static final String BODY_PART_HIPS = "Hips";
}
