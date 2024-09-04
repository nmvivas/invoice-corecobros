package com.banquito.core.invoicedoc.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.banquito.core.invoicedoc.dto.InvoiceDTO;
import com.banquito.core.invoicedoc.service.InvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InvoiceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InvoiceService invoiceService;

    @InjectMocks
    private InvoiceController invoiceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(invoiceController).build();
    }

    @Test
    void testGetAllInvoices() throws Exception {
        List<InvoiceDTO> invoices = List.of(new InvoiceDTO(), new InvoiceDTO());

        when(invoiceService.getAllInvoices()).thenReturn(invoices);

        mockMvc.perform(get("/invoice-microservice/api/v1/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(invoices.size()));

        verify(invoiceService, times(1)).getAllInvoices();
    }

    @Test
    void testCreateInvoice() throws Exception {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setSequential("002-002-000000002");

        when(invoiceService.create(any(InvoiceDTO.class))).thenReturn(invoiceDTO);

        mockMvc.perform(post("/invoice-microservice/api/v1/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invoiceDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sequential").value(invoiceDTO.getSequential()));

        verify(invoiceService, times(1)).create(any(InvoiceDTO.class));
    }

    @Test
    void testGetInvoiceBySequential() throws Exception {
        String sequential = "002-002-000000002";
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setSequential(sequential);

        when(invoiceService.getInvoiceBySequential(sequential)).thenReturn(invoiceDTO);

        mockMvc.perform(get("/invoice-microservice/api/v1/invoices/sequential/{sequential}", sequential))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sequential").value(sequential));

        verify(invoiceService, times(1)).getInvoiceBySequential(sequential);
    }

    @Test
    void testGetInvoiceByUniqueId() throws Exception {
        String uniqueId = "BWJ0087837";
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setUniqueId(uniqueId);

        when(invoiceService.getInvoiceByUniqueId(uniqueId)).thenReturn(invoiceDTO);

        mockMvc.perform(get("/invoice-microservice/api/v1/invoices/uniqueId")
                .param("uniqueId", uniqueId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uniqueId").value(uniqueId));

        verify(invoiceService, times(1)).getInvoiceByUniqueId(uniqueId);
    }

    @Test
    void testGetInvoicesByDateRange() throws Exception {
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);
        List<InvoiceDTO> invoices = List.of(new InvoiceDTO(), new InvoiceDTO());

        when(invoiceService.getInvoicesByDateRange(startDate, endDate)).thenReturn(invoices);

        mockMvc.perform(get("/invoice-microservice/api/v1/invoices/dateRange")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(invoices.size()));

        verify(invoiceService, times(1)).getInvoicesByDateRange(startDate, endDate);
    }

    @Test
    void testGetInvoicesByClient() throws Exception {
        String ruc = "170756616001";
        String companyName = "Global Trading";
        List<InvoiceDTO> invoices = List.of(new InvoiceDTO(), new InvoiceDTO());

        when(invoiceService.getInvoicesByClient(ruc, companyName)).thenReturn(invoices);

        mockMvc.perform(get("/invoice-microservice/api/v1/invoices/client")
                .param("ruc", ruc)
                .param("companyName", companyName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(invoices.size()));

        verify(invoiceService, times(1)).getInvoicesByClient(ruc, companyName);
    }

}
