package com.meters.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {

    private LocalDateTime timestamp;

    private Integer errorCode;

    private String errorMessage;
}
