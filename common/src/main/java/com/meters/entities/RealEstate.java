package com.meters.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.meters.entities.catalogs.ObjectType;
import com.meters.entities.constants.ClientType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "real_estates")
public class RealEstate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "real_estate_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "square", nullable = false)
    private Integer square;

    @NotNull
    @Column(name = "rooms", nullable = false)
    private Integer rooms;

    @NotNull
    @Column(name = "floors", nullable = false)
    private Integer floors;

    @Column(name = "garden_square")
    private Integer gardenSquare;

    @Column(name = "garage")
    private Boolean garage;
    @Size(max = 30)
    @NotNull
    @Column(name = "address", nullable = false, length = 30)
    private String address;

    @Column(nullable = false)
    private Timestamp created;

    @Column(nullable = false)
    private Timestamp changed;

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "owner_client_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientType ownerClientType = ClientType.NOT_SELECTED;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    @JsonManagedReference
    private Location location;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "object_type_id", nullable = false)
    @JsonManagedReference
    private ObjectType objectType;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "owner_person_id")
    @JsonManagedReference
    private Person ownerPerson;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "owner_company_id")
    @JsonManagedReference
    private Company ownerCompany;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "realEstate", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonBackReference
    private Set<Rent> rents = Collections.emptySet();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "realEstate", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonBackReference
    private Set<Sale> sales = Collections.emptySet();


}
