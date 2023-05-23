package com.meters.requests.update;

import com.meters.requests.create.LocationRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LocationUpdateRequest extends LocationRequest {

    @Schema(example = "false", type = "boolean", description = "is object deleted")
    private Boolean isDeleted;

}
