package com.meters.requests.update.catalogs;

import com.meters.requests.create.catalogs.DealTypeRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DealTypeUpdateRequest extends DealTypeRequest {

    @Schema(example = "false", type = "boolean", description = "is object deleted")
    private Boolean isDeleted;
}
