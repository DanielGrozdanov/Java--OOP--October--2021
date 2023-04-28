package restaurant.utility;

import restaurant.common.ExceptionMessages;

public class StringValidation {

    public static void isValid(String str, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
