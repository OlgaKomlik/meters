package com.meters.requests.create;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Deal Request")
public class DealRequest {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "10000",
            type = "number", format = "bigDecimal", description = "Amount of deal")
    private BigDecimal amount;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "10000",
            type = "number", format = "bigDecimal", description = "Fee from deal")
    private BigDecimal fee;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1684860195379",
            type = "timestamp", description = "date of deal")
    private Timestamp dealDate;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "PERSON",
            type = "string", description = "Type of owner")
    private String ownerClientType;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "COMPANY",
            type = "string", description = "Type of buyer")
    private String buyerClientType;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID Deal Type")
    private Long dealType;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID Deal conditions Type")
    private Long dealConditions;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID manager")
    private Long manager;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID owner")
    private Long owner;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID buyer")
    private Long buyer;
}
