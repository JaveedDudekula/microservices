package com.eazybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "Accounts", description = "Schema to hold Account details")
public class AccountsDto {

    @Schema(description = "Account number of the customer", example = "7894561230")
    @NotNull(message = "Account number cannot be null")
    @Min(value = 1000000000L, message = "Account number must be exactly 10 digits")
    @Max(value = 9999999999L, message = "Account number must be exactly 10 digits")
    private Long accountNumber;

    @Schema(description = "Type of the account", example = "Savings")
    @NotEmpty(message = "Account type cannot be empty or null")
    private String accountType;

    @Schema(description = "Branch address of the account", example = "123 Main Street, New York, NY 10001")
    @NotEmpty(message = "Branch address cannot be empty or null")
    private String branchAddress;
}
