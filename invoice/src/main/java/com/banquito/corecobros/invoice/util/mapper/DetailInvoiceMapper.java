package com.banquito.corecobros.invoice.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.corecobros.invoice.dto.DetailInvoiceDTO;
import com.banquito.corecobros.invoice.model.DetailInvoice;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DetailInvoiceMapper {
    DetailInvoiceDTO toDTO(DetailInvoice detailInvoice);

    DetailInvoice toPersistence(DetailInvoiceDTO detailInvoiceDTO);
}
