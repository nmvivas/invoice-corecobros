package com.banquito.core.invoicedoc.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.core.invoicedoc.dto.DetailInvoiceDTO;
import com.banquito.core.invoicedoc.model.DetailInvoice;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DetailInvoiceMapper {

    DetailInvoice toPersistence(DetailInvoiceDTO dto);

    DetailInvoiceDTO toDTO(DetailInvoice detailInvoice);
}