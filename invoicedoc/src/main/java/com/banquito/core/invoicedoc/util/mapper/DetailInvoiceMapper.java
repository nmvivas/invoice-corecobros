package com.banquito.core.invoicedoc.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.banquito.core.invoicedoc.dto.DetailInvoiceDTO;
import com.banquito.core.invoicedoc.model.DetailInvoice;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DetailInvoiceMapper {

    DetailInvoiceMapper INSTANCE = Mappers.getMapper(DetailInvoiceMapper.class);

    DetailInvoiceDTO toDTO(DetailInvoice detailInvoice);

    DetailInvoice toPersistence(DetailInvoiceDTO detailInvoiceDTO);
}
