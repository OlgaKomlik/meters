package com.meters.requests.update;

import com.meters.requests.create.RoleRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleUpdateRequest extends RoleRequest {

    private Boolean isDeleted;
}
