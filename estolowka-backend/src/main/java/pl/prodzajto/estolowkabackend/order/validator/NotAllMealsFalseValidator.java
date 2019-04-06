package pl.prodzajto.estolowkabackend.order.validator;

import pl.prodzajto.estolowkabackend.order.Day;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotAllMealsFalseValidator implements ConstraintValidator<NotAllMealsFalse, Day>
{
    
    @Override
    public boolean isValid(Day day, ConstraintValidatorContext constraintValidatorContext)
    {
        return day.isBreakfast() || day.isDinner() || day.isSupper();
    }
}
