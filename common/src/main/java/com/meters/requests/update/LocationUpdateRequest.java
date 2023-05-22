package com.meters.requests.update;

import com.meters.requests.create.LocationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LocationUpdateRequest extends LocationRequest {

    private Boolean isDeleted;

}
