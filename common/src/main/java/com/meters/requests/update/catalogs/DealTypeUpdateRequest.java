package com.meters.requests.update.catalogs;

import com.meters.requests.create.catalogs.DealTypeRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DealTypeUpdateRequest extends DealTypeRequest {

    private Boolean isDeleted;
}
