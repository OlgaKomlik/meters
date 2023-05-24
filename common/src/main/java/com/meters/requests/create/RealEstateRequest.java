package com.meters.requests.create;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "3659",
            type = "number", format = "integer", description = "Square of object")
    private Integer square;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "3",
            type = "number", format = "integer", description = "Count of rooms")
    private Integer rooms;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "3",
            type = "number", format = "integer", description = "Count of floors")
    private Integer floors;

    @Schema(example = "456", type = "number", format = "integer", description = "Square of garden")
    private Integer gardenSquare;

    @Schema(example = "false", type = "boolean", description = "Has got a garage")
    private Boolean garage;

    @Size(min = 2, max = 30, message = "RealEstate address must be between 2 and 30 characters")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Kovaleva 3, 90",
            type = "string", description = "Address")
    private String address;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "PERSON",
            type = "string", description = "Type of owner")
    private String ownerClientType;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID owner")
    private Long owner;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID locations")
    private Long location;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID object Type")
    private Long objectType;
}
