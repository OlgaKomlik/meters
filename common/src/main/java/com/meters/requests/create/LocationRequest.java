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
@Schema(description = "Location Request")
public class LocationRequest {

    @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Belarus",
            type = "string", description = "Country name")
    private String country;

    @Size(min = 2, max = 50, message = "City name must be between 2 and 50 characters")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Minsk",
            type = "string", description = "City name")
    private String city;

    @Size(min = 2, max = 50, message = "District name must be between 2 and 50 characters")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Shabany",
            type = "string", description = "District name")
    private String district;

    @Size(min = 2, max = 100, message = "Region name must be between 2 and 100 characters")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Minskaya",
            type = "string", description = "Region name")
    private String region;
}
