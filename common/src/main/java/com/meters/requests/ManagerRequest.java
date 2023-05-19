package com.meters.requests;

import com.meters.entities.constants.Gender;
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
public class ManagerRequest {
    private String managerName;
    private String surname;
    private Timestamp birthDate;
    private String email;
    private String password;
    private Gender gender;
}
