package ee.rik.application.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import ee.rik.application.mapper.EventMapper;
import ee.rik.domain.EntityFieldErrorCodeConstant;

public class EventStartDateTimeValidator implements ConstraintValidator<EventStartDateTimeConstraint, String> {

    @Override
    public boolean isValid(String startDateTime, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (startDateTime != null && !startDateTime.trim().isBlank()) {
                LocalDateTime.parse(startDateTime, EventMapper.DATE_TIME_FORMATTER);
            }
        } catch (DateTimeParseException e) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(
                            String.format(
                                    "{domain.constraints.%s.message}",
                                    EntityFieldErrorCodeConstant.Event.START_DATE_TIME_INVALID_FORMAT))
                    .addPropertyNode("event.startDateTime")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
