package com.banquito.core.invoicedoc.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.banquito.core.invoicedoc.dto.InvoiceDTO;
import com.banquito.core.invoicedoc.model.Invoice;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { DetailInvoiceMapper.class, TaxMapper.class })
public interface InvoiceMapper {
    InvoiceDTO toDTO(Invoice invoice);

    Invoice toPersistence(InvoiceDTO dto);
}