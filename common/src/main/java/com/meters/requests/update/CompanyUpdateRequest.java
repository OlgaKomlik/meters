package com.meters.requests.update;

import com.meters.requests.create.CompanyRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompanyUpdateRequest extends CompanyRequest {

    @Schema(example = "false", type = "boolean", description = "is object deleted")
    private Boolean isDeleted;
}
