package com.meters.entities.catalogs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.meters.entities.RealEstate;
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
import org.springframework.cache.annotation.Cacheable;

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
@Cacheable("c_object_type")
@Table(name = "c_object_type")
public class ObjectType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "object_type_id", nullable = false)
    private Long id;

    @Size(max = 30)
    @NotNull
    @Column(name = "object_type_name", nullable = false, length = 30)
    private String typeName;

    @Column(nullable = false)
    private Timestamp created = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false)
    private Timestamp changed = Timestamp.valueOf(LocalDateTime.now());

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "objectType", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonBackReference
    private Set<RealEstate> realEstates = Collections.emptySet();
}
