package com.meters.requests.create;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Deal Request")
public class DealRequest {

    @NotNull(message = "Amount must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "10000",
            type = "number", format = "bigDecimal", description = "Amount of deal")
    private BigDecimal amount;

    @NotNull(message = "Fee must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "10000",
            type = "number", format = "bigDecimal", description = "Fee from deal")
    private BigDecimal fee;

    @NotNull(message = "DealType name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1684860195379",
            type = "timestamp", description = "date of deal")
    private Timestamp dealDate;

    @NotNull(message = "Owner client type name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "PERSON",
            type = "string", description = "Type of owner")
    private String ownerClientType;

    @NotNull(message = "Buyer client type must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "COMPANY",
            type = "string", description = "Type of buyer")
    private String buyerClientType;

    @NotNull(message = "DealType id must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID Deal Type")
    private Long dealType;

    @NotNull(message = "Deal conditions id must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID Deal conditions Type")
    private Long dealConditions;

    @NotNull(message = "Manager id must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID manager")
    private Long manager;

    @NotNull(message = "Owner id must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID owner")
    private Long owner;

    @NotNull(message = "Buyer id must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID buyer")
    private Long buyer;
}
