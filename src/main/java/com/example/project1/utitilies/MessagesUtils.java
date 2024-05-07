package com.example.project1.utitilies;

import com.example.project1.exception.MissingResourceException;
import com.twilio.rest.api.v2010.account.incomingphonenumber.Local;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessagesUtils {
    static ResourceBundle messageBundle = ResourceBundle.getBundle("messages.response_messages", Locale.getDefault());
    public static String getMessage(String errorCode, Object... var2) {
        String message;
        try {
            message = messageBundle.getString(errorCode);
        } catch (MissingResourceException ex) {
            // case message_code is not defined.
            message = errorCode;
        }
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(message, var2);
        return formattingTuple.getMessage();
    }
}
