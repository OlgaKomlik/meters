package com.meters.requests.create;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Person Request")
public class PersonRequest {

    @Size(min = 2, max = 30, message = "Person name must be between 2 and 30 characters")
    @NotNull(message = "Person name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Olga",
            type = "string", description = "Person name")
    private String personName;

    @Size(min = 2, max = 30, message = "Person surname must be between 2 and 30 characters")
    @NotNull(message = "Person surname must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Komlik",
            type = "string", description = "Person surname")
    private String surname;


    @NotNull(message = "BirthDate must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1684860195379",
            type = "timestamp", description = "Birth date")
    private Timestamp birthDate;

    @Size(min = 2, max = 20, message = "PhoneNum name must be between 2 and 20 characters")
    @NotNull(message = "PhoneNum name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "375296854756",
            type = "string", description = "Phone number")
    private String phoneNum;

    @Size(min = 2, max = 20, message = "PassportNum name must be between 2 and 20 characters")
    @NotNull(message = "PassportNum name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "MP2569874",
            type = "string", description = "Passport number")
    private String passportNum;
}
