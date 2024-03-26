package com.example.coffeecraft.Utils;

public class PasswordUtils {

    public static boolean isPasswordStrong(String password) {

        boolean hasCapitalLetter = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c) && Character.isUpperCase(c)) {
                hasCapitalLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecialChar = true;
            }
        }
        return hasCapitalLetter && hasDigit && hasSpecialChar;
    }
}

