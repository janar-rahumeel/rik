package ee.rik.application.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

@Target(ElementType.FIELD)
@NotNull(message = "{jakarta.validation.constraints.NotNull_Fix.message}")
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EventStartDateTimeValidator.class)
public @interface EventStartDateTimeConstraint {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
