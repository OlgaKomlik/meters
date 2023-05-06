package com.meters.dto;

import com.meters.entities.AuthenticationInfo;
import com.meters.entities.Gender;
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
public class ManagerDto {
    private String managerName;
    private String surname;
    private Timestamp birthDate;
    private AuthenticationInfo authenticationInfo;
    private Gender gender = Gender.NOT_SELECTED;
    private String fullName;
}
