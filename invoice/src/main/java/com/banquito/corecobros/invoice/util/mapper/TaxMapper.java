package com.banquito.corecobros.invoice.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.banquito.corecobros.invoice.dto.TaxDTO;
import com.banquito.corecobros.invoice.model.Tax;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaxMapper {

    TaxMapper INSTANCE = Mappers.getMapper(TaxMapper.class);

    TaxDTO toDTO(Tax tax);

    Tax toPersistence(TaxDTO taxDTO);
}
