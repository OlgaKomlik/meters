package com.meters.requests.create;

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
public class SaleRequest {

    @NotNull(message = "Sale price must not be null")
    private BigDecimal price;

    @NotNull(message = "RealEstate id must not be null")
    private Long realEstate;
}
