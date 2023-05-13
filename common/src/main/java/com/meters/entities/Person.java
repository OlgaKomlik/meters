package com.meters.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private Long id;

    @Size(max = 30)
    @NotNull
    @Column(name = "person_name", nullable = false, length = 30)
    private String personName;

    @Size(max = 30)
    @NotNull
    @Column(name = "surname", nullable = false, length = 30)
    private String surname;
    @Size(max = 100)
    @NotNull
    @Column(name = "person_full_name", nullable = false, length = 100)
    private String personFullName;

    @Column(name = "birth_date", nullable = false)
    private Timestamp birthDate;

    @Size(max = 20)
    @NotNull
    @Column(name = "phone_num", nullable = false, length = 20)
    private String phoneNum;

    @Size(max = 20)
    @NotNull
    @Column(name = "passport_num", nullable = false, length = 20)
    private String passportNum;

    @Column(nullable = false)
    private Timestamp created;

    @Column(nullable = false)
    private Timestamp changed;

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    @JsonIgnore
    private Boolean isDeleted = false;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "ownerPerson", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonBackReference
    private Set<RealEstate> realEstates = Collections.emptySet();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "ownerPerson", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonBackReference
    private Set<Deal> dealsOwner = Collections.emptySet();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "buyerPerson", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonBackReference
    private Set<Deal> dealsBuyer = Collections.emptySet();

}
