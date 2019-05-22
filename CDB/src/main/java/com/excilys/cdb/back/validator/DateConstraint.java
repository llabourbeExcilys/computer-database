package com.excilys.cdb.back.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = DateConstraintValidator.class)
@Target( { java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraint {
    String message() default "DateConstraint.computerDTO.ldDiscontinued";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
     
    String ldIntroduced();
	String ldDiscontinued();
}
