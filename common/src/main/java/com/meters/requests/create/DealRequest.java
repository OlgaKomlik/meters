package com.meters.requests.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class DealRequest {

    @NotNull(message = "Amount must not be null")
    private BigDecimal amount;

    @NotNull(message = "Fee must not be null")
    private BigDecimal fee;

    @NotNull(message = "DealType name must not be null")
    private Timestamp dealDate;

    @NotNull(message = "Owner client type name must not be null")
    private String ownerClientType;

    @NotNull(message = "Buyer client type must not be null")
    private String buyerClientType;

    @NotNull(message = "DealType id must not be null")
    private Long dealType;

    @NotNull(message = "Deal conditions id must not be null")
    private Long dealConditions;

    @NotNull(message = "Manager id must not be null")
    private Long manager;

    @NotNull(message = "Owner id must not be null")
    private Long owner;

    @NotNull(message = "Buyer id must not be null")
    private Long buyer;
}
