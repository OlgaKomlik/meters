package com.meters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class RealEstateDto {
    private Integer square;
    private Integer rooms;
    private Integer floors;
    private Integer gardenSquare;
    private Boolean garage;
    private String address;
    private String ownerClientType;
    private Long owner;
    private Long location;
    private Long objectType;

}
