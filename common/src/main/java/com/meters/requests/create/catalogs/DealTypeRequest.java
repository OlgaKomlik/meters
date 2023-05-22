package com.meters.requests.create.catalogs;

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
public class DealTypeRequest {

    @Size(min = 2, max = 30, message = "DealType name must be between 2 and 30 characters")
    @NotNull(message = "DealType name must not be null")
    private String typeName;
}

