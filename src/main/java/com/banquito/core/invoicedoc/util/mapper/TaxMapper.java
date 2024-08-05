package com.banquito.core.invoicedoc.util.mapper;

import org.mapstruct.Mapper;

import com.banquito.core.invoicedoc.dto.TaxDTO;
import com.banquito.core.invoicedoc.model.Tax;

@Mapper(componentModel = "spring")
public interface TaxMapper {

    Tax toPersistence(TaxDTO dto);

    TaxDTO toDTO(Tax tax);

}
