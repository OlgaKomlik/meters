package com.meters.dto;

import com.meters.entities.enums.Gender;
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
    private String email;
    private String password;
    private Gender gender;
    private String fullName;
}
