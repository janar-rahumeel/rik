package ee.rik.domain.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NationalIdentificationCodeValidator.class)
public @interface NationalIdentificationCodeConstraint {

    String message() default "{domain.constraints.NationalIdentificationCodeConstraint.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
