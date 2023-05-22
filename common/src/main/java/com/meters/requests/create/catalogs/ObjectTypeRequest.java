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
public class ObjectTypeRequest {

    @Size(min = 2, max = 30, message = "ObjectType name must be between 2 and 30 characters")
    @NotNull(message = "ObjectType name must not be null")
    private String typeName;
}
