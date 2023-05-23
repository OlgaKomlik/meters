package com.meters.requests.update.catalogs;

import com.meters.requests.create.catalogs.ObjectTypeRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ObjectTypeUpdateRequest extends ObjectTypeRequest {

    @Schema(example = "false", type = "boolean", description = "is object deleted")
    private Boolean isDeleted;
}
