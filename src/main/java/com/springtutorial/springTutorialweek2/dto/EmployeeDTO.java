package com.springtutorial.springTutorialweek2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springtutorial.springTutorialweek2.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name of user cannot be empty, please enter proper name...!")
    @Size(min = 0,max = 15,message = "length should vari between 0 to 15")
    private String name;

    @NotBlank(message = "Email should not blank")
    @Email(message = "Email should valid email...!")
    private String email;

    @Min(value = 18, message = "Minimum age of user should not less than the 18")
    @Max(value = 80,message = "Maximum age should not be greater than 80")
    private Integer age;

    @NotBlank(message = "Role should not blank")
    //@Pattern(regexp = "^(Admin|User)$")
    @EmployeeRoleValidation
    private String role ;//Admin,User

    @NotNull(message = "salary of user should not be null")
    @Positive(message = "salary should not be negative")
    @Digits(integer = 5,fraction = 2,message = "salery should be in format of *****.**")
    @DecimalMax(value = "90000.99")
    @DecimalMin(value = "10000.90")
    private Double salary;

    @PastOrPresent(message = "Date of joining not in future")
    private LocalDate dateOFJoining;



    @JsonProperty("isActive")
    private Boolean isActive;


}
