package com.meters.requests.create;

import com.meters.entities.constants.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Schema(description = "Manager Request")
public class ManagerRequest {

    @Size(min = 2, max = 50, message = "Manager name must be between 2 and 50 characters")
    @NotNull(message = "Manager name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Olga",
            type = "string", description = "Manager name")
    private String managerName;

    @Size(min = 2, max = 50, message = "Manager surname must be between 2 and 50 characters")
    @NotNull(message = "Manager surname must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Komlik",
            type = "string", description = "Manager surname")
    private String surname;

    @NotNull(message = "BirthDate must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1684860195379",
            type = "timestamp", description = "birthdate")
    private Timestamp birthDate;

    @Size(min = 10, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "Email must be between 10 and 30 characters  containing numbers, lowercase and uppercase letters")
    @NotNull(message = "Email must not be null")
    @Email(message = "Invalid email address")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "oluska92666@gmail.com",
            type = "string", description = "Manager email")
    private String email;

    @Size(min = 8, max = 30)
    @Pattern(message = "Password must be between 8 and 30 characters containing numbers, lowercase and uppercase letters",
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,30}$")
    @NotNull(message = "Password must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "gTdkg785JFhf",
            type = "string", description = "Manager email")
    private String password;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "FEMALE",
            type = "Gender", description = "Manager gender")
    private Gender gender;
}
