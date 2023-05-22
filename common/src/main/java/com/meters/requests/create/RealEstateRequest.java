package com.meters.requests.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class RealEstateRequest {

    @NotNull(message = "RealEstate square must not be null")
    private Integer square;

    @NotNull(message = "RealEstate rooms must not be null")
    private Integer rooms;

    @NotNull(message = "RealEstate floors must not be null")
    private Integer floors;

    private Integer gardenSquare;

    private Boolean garage;

    @Size(min = 2, max = 30, message = "RealEstate address must be between 2 and 30 characters")
    @NotNull(message = "RealEstate address must not be null")
    private String address;

    @NotNull(message = "Owner Client Type must not be null")
    private String ownerClientType;

    @NotNull(message = "Owner id must not be null")
    private Long owner;

    @NotNull(message = "Location id must not be null")
    private Long location;

    @NotNull(message = "ObjectType id must not be null")
    private Long objectType;
}
