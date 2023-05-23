package com.meters.requests.create;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "RealEstate Request")
public class RealEstateRequest {

    @NotNull(message = "RealEstate square must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "3659",
            type = "number", format = "integer", description = "Square of object")
    private Integer square;

    @NotNull(message = "RealEstate rooms must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "3",
            type = "number", format = "integer", description = "Count of rooms")
    private Integer rooms;

    @NotNull(message = "RealEstate floors must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "3",
            type = "number", format = "integer", description = "Count of floors")
    private Integer floors;

    @Schema(example = "456", type = "number", format = "integer", description = "Square of garden")
    private Integer gardenSquare;

    @Schema(example = "false", type = "boolean", description = "Has got a garage")
    private Boolean garage;

    @Size(min = 2, max = 30, message = "RealEstate address must be between 2 and 30 characters")
    @NotNull(message = "RealEstate address must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Kovaleva 3, 90",
            type = "string", description = "Address")
    private String address;

    @NotNull(message = "Owner Client Type must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "PERSON",
            type = "string", description = "Type of owner")
    private String ownerClientType;

    @NotNull(message = "Owner id must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID owner")
    private Long owner;

    @NotNull(message = "Location id must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID locations")
    private Long location;

    @NotNull(message = "ObjectType id must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID object Type")
    private Long objectType;
}
