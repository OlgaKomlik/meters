package com.meters.requests.update;

import com.meters.requests.create.CompanyRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompanyUpdateRequest extends CompanyRequest {

    private Boolean isDeleted;
}
