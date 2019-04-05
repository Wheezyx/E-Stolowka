package pl.prodzajto.estolowkabackend.order.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = NotAllMealsFalseValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotAllMealsFalse
{
    String message() default "At least one meal must be selected";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
