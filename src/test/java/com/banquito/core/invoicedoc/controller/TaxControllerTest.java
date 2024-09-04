package com.banquito.core.invoicedoc.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.banquito.core.invoicedoc.dto.TaxDTO;
import com.banquito.core.invoicedoc.service.TaxService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TaxControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaxService taxService;

    @InjectMocks
    private TaxController taxController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taxController).build();
    }

    @Test
    void testCreateTax() throws Exception {
        TaxDTO taxDTO = new TaxDTO();
        taxDTO.setUniqueId("SZA0094735");

        when(taxService.createTax(any(TaxDTO.class))).thenReturn(taxDTO);

        mockMvc.perform(post("/invoice-microservice/api/v1/taxes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(taxDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uniqueId").value(taxDTO.getUniqueId()));

        verify(taxService, times(1)).createTax(any(TaxDTO.class));
    }

    @Test
    void testGetAllTaxes() throws Exception {
        List<TaxDTO> taxes = List.of(new TaxDTO(), new TaxDTO());

        when(taxService.getAllTaxes()).thenReturn(taxes);

        mockMvc.perform(get("/invoice-microservice/api/v1/taxes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(taxes.size()));

        verify(taxService, times(1)).getAllTaxes();
    }

    @Test
    void testGetTaxByUniqueId() throws Exception {
        String uniqueId = "SZA0094735";
        TaxDTO taxDTO = new TaxDTO();
        taxDTO.setUniqueId(uniqueId);

        when(taxService.getTaxByUniqueId(uniqueId)).thenReturn(taxDTO);

        mockMvc.perform(get("/invoice-microservice/api/v1/taxes/{uniqueId}", uniqueId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uniqueId").value(uniqueId));

        verify(taxService, times(1)).getTaxByUniqueId(uniqueId);
    }

    @Test
    void testUpdateTax() throws Exception {
        String uniqueId = "SZA0094735";
        TaxDTO taxDTO = new TaxDTO();
        taxDTO.setUniqueId(uniqueId);

        when(taxService.updateTax(eq(uniqueId), any(TaxDTO.class))).thenReturn(taxDTO);

        mockMvc.perform(put("/invoice-microservice/api/v1/taxes/{uniqueId}", uniqueId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(taxDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uniqueId").value(uniqueId));

        verify(taxService, times(1)).updateTax(eq(uniqueId), any(TaxDTO.class));
    }

}
