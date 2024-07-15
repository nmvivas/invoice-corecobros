package com.banquito.corecobros.invoice.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.corecobros.invoice.dto.TaxDTO;
import com.banquito.corecobros.invoice.model.Tax;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaxMapper {

    TaxDTO toDTO(Tax tax);

    Tax toPersistence(TaxDTO taxDTO);
}
