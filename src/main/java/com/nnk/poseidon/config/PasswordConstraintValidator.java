package com.nnk.poseidon.config;

import org.passay.PasswordValidator;
import org.passay.LengthRule;
import org.passay.UppercaseCharacterRule;
import org.passay.DigitCharacterRule;
import org.passay.SpecialCharacterRule;
import org.passay.RuleResult;
import org.passay.PasswordData;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class PasswordConstraintValidator
        implements ConstraintValidator<ValidPassword, String> {
   @Override
    public void initialize(final ValidPassword arg0) {
    }

    @Override
    public boolean isValid(final String password,
                          final ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 80),
                new UppercaseCharacterRule(1),
                new DigitCharacterRule(1),
                new SpecialCharacterRule(1)));
        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join(",", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }

}
