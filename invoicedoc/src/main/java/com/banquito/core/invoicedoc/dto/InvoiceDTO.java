package com.banquito.core.invoicedoc.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class InvoiceDTO {

    private String id;

    @NotBlank(message = "RUC is mandatory")
    @Size
    private String ruc;

    @NotBlank(message = "Company Name is mandatory")
    @Size(max = 100, message = "Company Name cannot be longer than 100 characters")
    private String companyName;

    @NotBlank(message = "Address is mandatory")
    @Size(max = 100, message = "Address cannot be longer than 100 characters")
    private String address;

    @NotBlank(message = "Phone is mandatory")
    @Size(max = 10, message = "Phone cannot be longer than 10 characters")
    private String phone;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email cannot be longer than 100 characters")
    private String email;

    @NotBlank(message = "Sequential is mandatory")
    @Size(max = 20, message = "Sequential cannot be longer than 20 characters")
    private String sequential;

    @NotBlank(message = "Authorization Number is mandatory")
    @Size(max = 40, message = "Authorization Number cannot be longer than 40 characters")
    private String authorizationNumber;

    @NotNull(message = "Date is mandatory")
    private LocalDateTime date;

    @NotNull(message = "Subtotal is mandatory")
    private BigDecimal subtotal;

    @NotNull(message = "Total is mandatory")
    private BigDecimal total;
}
