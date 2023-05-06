package com.meters.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
//@EqualsAndHashCode
@ToString
@Entity
@Table(name = "managers")
public class Manager {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "managers_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "managers_id_seq", sequenceName = "managers_id_seq", allocationSize = 1, initialValue = 100)
    @Column(name = "manager_id")
    private Long id;

    @Column(name = "manager_name")
    private String managerName;

    @Column
    private String surname;

    @Column(name = "birth_date")
    private Timestamp birthDate;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;

    @Column(name = "is_deleted", nullable = false)
    @JsonIgnore
    private Boolean isDeleted = false;

    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "email", nullable = false))
    @AttributeOverride(name = "password", column = @Column(name = "manager_password", nullable = false))
    private AuthenticationInfo authenticationInfo;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.NOT_SELECTED;

    @Column(name = "manager_full_name")
    private String fullName;
}