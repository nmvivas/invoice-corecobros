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

    @NotBlank(message = "RUC es obligatorio")
    @Size
    private String ruc;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 100, message = "El nombre de la empresa no puede tener más de 100 caracteres.")
    private String companyName;

    @NotBlank(message = "La dirección es obligatoria.")
    @Size(max = 100, message = "La dirección no puede tener más de 100 caracteres.")
    private String address;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 10, message = "El teléfono no puede tener más de 10 caracteres")
    private String phone;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico debe ser válido.")
    @Size(max = 100, message = "El correo electrónico no puede tener más de 100 caracteres.")
    private String email;

    @NotBlank(message = "Secuencial es obligatoria")
    @Size(max = 20, message = "El secuencial no puede tener más de 20 caracteres.")
    private String sequential;

    @NotBlank(message = "El número de autorización es obligatorio")
    @Size(max = 40, message = "El número de autorización no puede tener más de 40 caracteres")
    private String authorizationNumber;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime date;

    @NotNull(message = "El subtotal es obligatorio")
    private BigDecimal subtotal;

    @NotNull(message = "El total es obligatorio.")
    private BigDecimal total;
}
