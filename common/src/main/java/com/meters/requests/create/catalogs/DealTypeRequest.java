package com.meters.requests.create.catalogs;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Deal type Request")
public class DealTypeRequest {

    @Size(min = 2, max = 30, message = "DealType name must be between 2 and 30 characters")
    @NotNull(message = "DealType name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Sale",
            type = "string", description = "Deal type name")
    private String typeName;
}

