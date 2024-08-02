package com.banquito.core.invoicedoc.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.core.invoicedoc.dto.TaxDTO;
import com.banquito.core.invoicedoc.model.Tax;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaxMapper {

    Tax toPersistence(TaxDTO dto);

    TaxDTO toDTO(Tax tax);
}
