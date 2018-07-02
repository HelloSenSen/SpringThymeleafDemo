package springdemo.date;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalDate;

/**
 * @auther: Sunnysen
 * @Date: 2018/7/2 14:08
 * @Description:
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PastLocalDate.PastValidator.class)
@Documented
public @interface PastLocalDate {
    String message() default "{javax.validation.constraints.Past.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class PastValidator implements ConstraintValidator<PastLocalDate,LocalDate>{
        @Override
        public void initialize(PastLocalDate past){
        }
        @Override
        public boolean isValid(LocalDate localDate, ConstraintValidatorContext context){
            return localDate== null || localDate.isBefore(LocalDate.now());
        }
    }
}
