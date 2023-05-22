package com.meters.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Size(max = 9)
    @NotNull
    @Column(name = "unp_num", nullable = false, length = 9)
    private String unpNum;
    @Size(max = 100)
    @NotNull
    @Column(name = "director_full_name", nullable = false, length = 100)
    private String directorFullName;

    @Size(max = 20)
    @NotNull
    @Column(name = "phone_num", nullable = false, length = 20)
    private String phoneNum;

    @Column(name = "date_create_company", nullable = false)
    private Timestamp dateCreateCompany;

    @Size(max = 100)
    @NotNull
    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Size(max = 30)
    @NotNull
    @Column(name = "director_name", nullable = false, length = 30)
    private String directorName;

    @Size(max = 30)
    @NotNull
    @Column(name = "director_surname", nullable = false, length = 30)
    private String directorSurname;

    @Size(max = 50)
    @NotNull
    @Column(name = "checking_account", nullable = false, length = 50)
    private String checkingAccount;

    @Size(max = 100)
    @NotNull
    @Column(name = "bank_name", nullable = false, length = 100)
    private String bankName;

    @Column(nullable = false)
    private Timestamp created = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false)
    private Timestamp changed = Timestamp.valueOf(LocalDateTime.now());

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "ownerCompany", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonBackReference
    private Set<RealEstate> realEstates = Collections.emptySet();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "ownerCompany", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonBackReference
    private Set<Deal> dealsOwner = Collections.emptySet();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "buyerCompany", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonBackReference
    private Set<Deal> dealsBuyer = Collections.emptySet();
}
