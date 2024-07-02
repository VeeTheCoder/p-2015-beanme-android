package com.beanmeapp.beanme.helpers;

import android.widget.EditText;

/**
 * Contains extra helper methods to help with signing on.
 */
public class SignUp_Login_Helper {

    /**
     * Checks if a EditText is empty.
     * @param text EditText, the View to check if it is empty.
     * @return boolean, true if the EditText is empty.
     */
    public static boolean isEmpty(EditText text) {
        return text.getText().toString().trim().length() == 0;
    }

    /**
     * Appends the text to the builder adding ', and' if it already has an error.
     * @param builder StringBuilder, where we are appending the message
     * @param text String, the error message to append
     * @param hasError boolean, if there is already an error that has occurred or not.
     */
    public static void appendErrorMessage(StringBuilder builder, String text, boolean hasError) {
        if (hasError) {
            builder.append(", and ");
        }

        builder.append(text);
    }
}
