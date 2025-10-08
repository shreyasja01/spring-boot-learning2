package com.springtutorial.springTutorialweek2.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = EmployeeRoleValidtor.class)
public @interface EmployeeRoleValidation {

    String message() default "Role of employee is either Admin or User or Manager";

    Class<?>[] groups() default  {};

    Class<? extends Payload>[] payload() default {};


}
