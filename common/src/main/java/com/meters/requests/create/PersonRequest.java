package com.meters.requests.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class PersonRequest {

    @Size(min = 2, max = 30, message = "Person name must be between 2 and 30 characters")
    @NotNull(message = "Person name must not be null")
    private String personName;

    @Size(min = 2, max = 30, message = "Person surname must be between 2 and 30 characters")
    @NotNull(message = "Person surname must not be null")
    private String surname;


    @NotNull(message = "BirthDate must not be null")
    private Timestamp birthDate;

    @Size(min = 2, max = 20, message = "PhoneNum name must be between 2 and 20 characters")
    @NotNull(message = "PhoneNum name must not be null")
    private String phoneNum;

    @Size(min = 2, max = 20, message = "PassportNum name must be between 2 and 20 characters")
    @NotNull(message = "PassportNum name must not be null")
    private String passportNum;
}
