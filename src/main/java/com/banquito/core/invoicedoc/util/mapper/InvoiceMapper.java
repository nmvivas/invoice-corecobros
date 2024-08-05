package com.banquito.core.invoicedoc.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.banquito.core.invoicedoc.dto.InvoiceDTO;
import com.banquito.core.invoicedoc.model.Invoice;

@Mapper(componentModel = "spring", uses = { DetailInvoiceMapper.class })
public interface InvoiceMapper {
    
    @Mapping(source = "detailInvoices", target = "detailInvoices")
    InvoiceDTO toDTO(Invoice invoice);

    @Mapping(source = "detailInvoices", target = "detailInvoices")
    Invoice toPersistence(InvoiceDTO dto);
}
