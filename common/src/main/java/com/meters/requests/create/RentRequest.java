package com.meters.requests.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
@Schema(description = "Rent Request")
public class RentRequest {

    @NotNull(message = "Rent per month must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "10000",
            type = "number", format = "bigDecimal", description = "Rent per month")
    private BigDecimal rentPerMonth;

    @NotNull(message = "Min period must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "3",
            type = "number", format = "integer", description = "Minimal period for rent")
    private Integer minPeriod;

    @NotNull(message = "RealEstate id must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID Real Estate")
    private Long realEstate;
}
