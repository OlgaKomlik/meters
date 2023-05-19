package com.meters.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class DealRequest {

    private BigDecimal amount;
    private BigDecimal fee;
    private Timestamp dealDate;
    private String ownerClientType;
    private String buyerClientType;
    private Long dealType;
    private Long dealConditions;
    private Long manager;
    private Long owner;
    private Long buyer;
}
