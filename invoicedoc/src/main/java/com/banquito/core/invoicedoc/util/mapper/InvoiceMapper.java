package com.banquito.core.invoicedoc.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.banquito.core.invoicedoc.dto.InvoiceDTO;
import com.banquito.core.invoicedoc.model.Invoice;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { DetailInvoiceMapper.class, TaxMapper.class })
public interface InvoiceMapper {
    
    @Mapping(source = "detailInvoices", target = "detailInvoices")
    @Mapping(source = "taxes", target = "taxes")
    InvoiceDTO toDTO(Invoice invoice);

    @Mapping(source = "detailInvoices", target = "detailInvoices")
    @Mapping(source = "taxes", target = "taxes")
    Invoice toPersistence(InvoiceDTO dto);
}