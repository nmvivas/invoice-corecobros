package com.banquito.core.invoicedoc.util.mapper;

import org.mapstruct.Mapper;

import com.banquito.core.invoicedoc.dto.DetailInvoiceDTO;
import com.banquito.core.invoicedoc.model.DetailInvoice;

@Mapper(componentModel = "spring")
public interface DetailInvoiceMapper {

    DetailInvoice toPersistence(DetailInvoiceDTO dto);

    DetailInvoiceDTO toDTO(DetailInvoice detailInvoice);
}