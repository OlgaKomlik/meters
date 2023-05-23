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
@Schema(description = "Sale Request")
public class SaleRequest {

    @NotNull(message = "Sale price must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "10000",
            type = "number", format = "bigDecimal", description = "Sale price")
    private BigDecimal price;

    @NotNull(message = "RealEstate id must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID Real Estate")
    private Long realEstate;
}
