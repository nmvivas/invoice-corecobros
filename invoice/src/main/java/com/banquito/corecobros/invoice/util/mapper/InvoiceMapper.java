package com.banquito.corecobros.invoice.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.banquito.corecobros.invoice.dto.InvoiceDTO;
import com.banquito.corecobros.invoice.model.Invoice;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    InvoiceDTO toDTO(Invoice invoice);

    Invoice toPersistence(InvoiceDTO invoiceDTO);
}
