package com.meters.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class CompanyRequest {

    private String companyName;
    private String unpNum;
    private String phoneNum;
    private Timestamp dateCreateCompany;
    private String address;
    private String directorName;
    private String directorSurname;
    private String checkingAccount;
    private String bankName;
}
