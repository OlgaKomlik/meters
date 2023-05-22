package com.meters.requests.update;

import com.meters.requests.create.PersonRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonUpdateRequest extends PersonRequest {

    private Boolean isDeleted;

}
