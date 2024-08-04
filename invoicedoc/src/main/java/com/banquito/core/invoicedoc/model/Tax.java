package com.banquito.core.invoicedoc.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "taxes")
public class Tax {

    @Id
    private String id;
    @Indexed(unique = true)
    @NotNull
    private String uniqueId;

    @NotNull
    private String invoiceId;
    @NotNull
    private String name;
    @NotBlank
    private BigDecimal value;
    @NotBlank
    private BigDecimal percentage;

}
