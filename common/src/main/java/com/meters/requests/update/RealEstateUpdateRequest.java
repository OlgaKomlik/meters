package com.meters.requests.update;

import com.meters.requests.create.RealEstateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RealEstateUpdateRequest extends RealEstateRequest {

    private Boolean isDeleted;
}
