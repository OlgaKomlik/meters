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
public class LocationRequest {

    @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters")
    @NotNull(message = "Country name must not be null")
    private String country;

    @Size(min = 2, max = 50, message = "City name must be between 2 and 50 characters")
    @NotNull(message = "City name must not be null")
    private String city;

    @Size(min = 2, max = 50, message = "District name must be between 2 and 50 characters")
    @NotNull(message = "District name must not be null")
    private String district;

    @Size(min = 2, max = 100, message = "Region name must be between 2 and 100 characters")
    @NotNull(message = "Region name must not be null")
    private String region;
}
