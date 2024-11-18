package com.example.ecommerceamex.exception;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
}