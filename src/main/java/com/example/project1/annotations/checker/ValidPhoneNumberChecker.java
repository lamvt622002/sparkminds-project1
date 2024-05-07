package com.example.project1.annotations.checker;

import com.example.project1.annotations.ValidPhoneNumber;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.lookups.v1.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

@Configurable
public class ValidPhoneNumberChecker implements ConstraintValidator<ValidPhoneNumber, String> {
    @Value("${TWILIO_ACCOUNT_SID}")
    private String twilioAccountSid;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String twilioAuthToken;

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation){
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        value = value.replaceAll("[\\s()-]", "");

        if ("".equals(value)) {
            return false;
        }

        try {
            PhoneNumber.fetcher(String.valueOf(new com.twilio.type.PhoneNumber(value))).fetch();
            return true;

        } catch (ApiException e) {
            // The Lookup API returns HTTP 404 if the phone number is not found
            // (ie it is not a real phone number)
            if (e.getStatusCode() == 404) {
                return false;
            }
            throw e;
        }
    }
}
