package com.meters.requests.create;

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
@Schema(description = "Role Request")
public class RoleRequest {

    @Size(min = 2, max = 50, message = "Role name must be between 2 and 30 characters")
    @NotNull(message = "Role name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "ROLE_USER",
            type = "string", description = "Role name")
    private String roleName;
}
