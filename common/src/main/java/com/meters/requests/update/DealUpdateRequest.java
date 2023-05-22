package com.meters.requests.update;

import com.meters.requests.create.DealRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DealUpdateRequest extends DealRequest {

    private Boolean isDeleted;
}
