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
public class RentRequest {

    @NotNull(message = "Rent per month must not be null")
    private BigDecimal rentPerMonth;

    @NotNull(message = "Min period must not be null")
    private Integer minPeriod;

    @NotNull(message = "RealEstate id must not be null")
    private Long realEstate;
}
