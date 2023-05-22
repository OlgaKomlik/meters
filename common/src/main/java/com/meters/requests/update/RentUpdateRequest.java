package com.meters.requests.update;


import com.meters.requests.create.RentRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RentUpdateRequest extends RentRequest {

    private Boolean isDeleted;
}
