package com.meters.requests.update.catalogs;

import com.meters.requests.create.catalogs.ObjectTypeRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ObjectTypeUpdateRequest extends ObjectTypeRequest {

    private Boolean isDeleted;
}
