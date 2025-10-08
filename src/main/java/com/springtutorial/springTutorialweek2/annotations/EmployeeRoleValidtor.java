package com.springtutorial.springTutorialweek2.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class EmployeeRoleValidtor implements ConstraintValidator<EmployeeRoleValidation, String> {
    @Override
    public boolean isValid(String inputRole, ConstraintValidatorContext context) {
        List<String> roleList =  List.of("User","Admin","Manager");

        return roleList.contains(inputRole);
    }
}
