package com.meters.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.meters.entities.catalogs.DealType;
import com.meters.entities.constants.ClientType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "deals")
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deal_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "fee", nullable = false)
    private BigDecimal fee;

    @Column(name = "deal_date", nullable = false)
    private Timestamp dealDate;

    @Column(nullable = false)
    private Timestamp created = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false)
    private Timestamp changed = Timestamp.valueOf(LocalDateTime.now());

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "owner_client_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientType ownerClientType = ClientType.NOT_SELECTED;

    @Column(name = "buyer_client_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientType buyerClientType = ClientType.NOT_SELECTED;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "deal_type_id", nullable = false)
    @JsonManagedReference
    private DealType dealType;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "rent_id")
    @JsonManagedReference
    private Rent rent;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "sale_id")
    @JsonManagedReference
    private Sale sale;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JsonManagedReference
    private Manager manager;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "owner_person_id")
    @JsonManagedReference
    private Person ownerPerson;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "buyer_person_id")
    @JsonManagedReference
    private Person buyerPerson;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "owner_company_id")
    @JsonManagedReference
    private Company ownerCompany;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "buyer_company_id")
    @JsonManagedReference
    private Company buyerCompany;
}
