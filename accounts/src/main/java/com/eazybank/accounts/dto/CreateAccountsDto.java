package com.eazybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "CreateAccounts", description = "Schema to hold account creation details")
public class CreateAccountsDto {

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
}
