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
@Schema(description = "Company Request")
public class CompanyRequest {

    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    @NotNull(message = "Company name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Oriflame enc",
            type = "string", description = "Company name")
    private String companyName;

    @Size(min = 9, max = 9, message = "Company unp number must be 9 characters")
    @NotNull(message = "Company unp number must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "123654987",
            type = "string", description = "Number UNP")
    private String unpNum;

    @Size(min = 7, max = 20, message = "Company phone number must be between 7 and 20 characters")
    @NotNull(message = "Company phone number must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "375689563254",
            type = "string", description = "Phone number")
    private String phoneNum;

    @NotNull(message = "Company date of create must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1684860195379",
            type = "timestamp", description = "date of create company")
    private Timestamp dateCreateCompany;

    @Size(min = 2, max = 100, message = "Company address must be between 2 and 100 characters")
    @NotNull(message = "Company address must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Maxaleva 5-15",
            type = "string", description = "Adress of company")
    private String address;

    @Size(min = 2, max = 30, message = "Director name must be between 2 and 30 characters")
    @NotNull(message = "Director name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Olga",
            type = "string", description = "Director name")
    private String directorName;

    @Size(min = 2, max = 30, message = "Director surname must be between 2 and 30 characters")
    @NotNull(message = "Director surname must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Komlik",
            type = "string", description = "Director surname")
    private String directorSurname;

    @Size(min = 2, max = 100, message = "Company checkingAccount must be between 2 and 100 characters")
    @NotNull(message = "Company checkingAccount must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "BY20 OLMP 3135 0000 0010 0000 0933",
            type = "string", description = "Checking account")
    private String checkingAccount;

    @Size(min = 2, max = 100, message = "Company bankName must be between 2 and 100 characters")
    @NotNull(message = "Company bankName must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "BSB bank",
            type = "string", description = "Bank name")
    private String bankName;
}
