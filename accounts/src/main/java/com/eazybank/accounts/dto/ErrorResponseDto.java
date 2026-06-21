package com.eazybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(name = "ErrorResponse", description = "Schema to hold error response details")
public class ErrorResponseDto {

    @Schema(description = "API path that triggered by the client", example = "/api/operation")
    private String apiPath;

    @Schema(description = "Error code returned in the response", example = "500")
    private HttpStatus errorCode;

    @Schema(description = "Error message returned in the response", example = "Unexpected error occurred")
    private String errorMessage;

    @Schema(description = "Time of the error")
    private LocalDateTime errorTime;
}
