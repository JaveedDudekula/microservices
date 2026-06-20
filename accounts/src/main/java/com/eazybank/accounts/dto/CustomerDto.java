package com.eazybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Customer", description = "Schema to hold Customer & Account details")
public class CustomerDto {

    @Schema(description = "Name of the customer", example = "John Doe")
    @NotEmpty(message = "Name cannot be empty or null")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String name;

    @Schema(description = "Email of the customer", example = "johndoe@gmail.com")
    @NotEmpty(message = "Email cannot be empty or null")
    @Email(message = "Email must be valid")
    private String email;

    @Schema(description = "Mobile number of the customer", example = "1234567890")
    @NotEmpty(message = "Mobile number cannot be empty or null")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;


    @Schema(description = "Account details of the customer")
    @NotNull(message = "Account cannot be empty or null")
    @Valid
    private AccountsDto account;
}
