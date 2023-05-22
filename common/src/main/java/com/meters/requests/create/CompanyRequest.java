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
public class CompanyRequest {

    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    @NotNull(message = "Company name must not be null")
    private String companyName;

    @Size(min = 9, max = 9, message = "Company unp number must be 9 characters")
    @NotNull(message = "Company unp number must not be null")
    private String unpNum;

    @Size(min = 7, max = 20, message = "Company phone number must be between 7 and 20 characters")
    @NotNull(message = "Company phone number must not be null")
    private String phoneNum;

    @NotNull(message = "Company date of create must not be null")
    private Timestamp dateCreateCompany;

    @Size(min = 2, max = 100, message = "Company address must be between 2 and 100 characters")
    @NotNull(message = "Company address must not be null")
    private String address;

    @Size(min = 2, max = 30, message = "Director name must be between 2 and 30 characters")
    @NotNull(message = "Director name must not be null")
    private String directorName;

    @Size(min = 2, max = 30, message = "Director surname must be between 2 and 30 characters")
    @NotNull(message = "Director surname must not be null")
    private String directorSurname;

    @Size(min = 2, max = 100, message = "Company checkingAccount must be between 2 and 100 characters")
    @NotNull(message = "Company checkingAccount must not be null")
    private String checkingAccount;

    @Size(min = 2, max = 100, message = "Company bankName must be between 2 and 100 characters")
    @NotNull(message = "Company bankName must not be null")
    private String bankName;
}
