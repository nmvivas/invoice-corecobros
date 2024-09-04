package com.banquito.core.invoicedoc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.banquito.core.invoicedoc.dto.InvoiceDTO;
import com.banquito.core.invoicedoc.model.Invoice;
import com.banquito.core.invoicedoc.repository.InvoiceRepository;
import com.banquito.core.invoicedoc.util.UniqueIdGeneration;
import com.banquito.core.invoicedoc.util.mapper.InvoiceMapper;

public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private UniqueIdGeneration uniqueIdGeneration;

    @Mock
    private InvoiceMapper mapper;

    @InjectMocks
    private InvoiceService invoiceService;

    private InvoiceDTO invoiceDTO;
    private Invoice invoice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        invoiceDTO = new InvoiceDTO();
        invoiceDTO.setUniqueId("BWJ0087837");
        invoiceDTO.setRuc("1717094955001");
        invoiceDTO.setCompanyName("La Favorita");
        invoiceDTO.setAddress("23 Calle de los Arupos");
        invoiceDTO.setPhone("123456789");
        invoiceDTO.setEmail("facturacion@favorita.com");
        invoiceDTO.setSequential("002-002-000000002");
        invoiceDTO.setAuthorizationNumber("2323212233");
        invoiceDTO.setDate(LocalDateTime.now());
        invoiceDTO.setSubtotal(new BigDecimal("100.00"));
        invoiceDTO.setTotal(new BigDecimal("112.00"));

        invoice = new Invoice();
        invoice.setId("66aed3bae028d404dd9df64e");
        invoice.setUniqueId("BWJ0087837");
        invoice.setRuc("1717094955001");
        invoice.setCompanyName("La Favorita");
        invoice.setAddress("23 Calle de los Arupos");
        invoice.setPhone("123456789");
        invoice.setEmail("facturacion@favorita.com");
        invoice.setSequential("002-002-000000002");
        invoice.setAuthorizationNumber("2323212233");
        invoice.setDate(LocalDateTime.now());
        invoice.setSubtotal(new BigDecimal("100.00"));
        invoice.setTotal(new BigDecimal("112.00"));
    }

    @Test
    void testCreateInvoice() {
        when(uniqueIdGeneration.generateUniqueId()).thenReturn("BWJ0087837");
        when(mapper.toPersistence(invoiceDTO)).thenReturn(invoice);
        when(invoiceRepository.save(invoice)).thenReturn(invoice);
        when(mapper.toDTO(invoice)).thenReturn(invoiceDTO);

        InvoiceDTO result = invoiceService.create(invoiceDTO);

        assertNotNull(result);
        assertEquals(invoiceDTO.getUniqueId(), result.getUniqueId());
        verify(invoiceRepository, times(1)).save(invoice);
    }

    @Test
    void testCreateInvoiceWithNullUniqueId() {
        invoiceDTO.setUniqueId(null);

        when(uniqueIdGeneration.generateUniqueId()).thenReturn("BWJ0087837");
        when(mapper.toPersistence(invoiceDTO)).thenReturn(invoice);
        when(invoiceRepository.save(invoice)).thenReturn(invoice);
        when(mapper.toDTO(invoice)).thenReturn(invoiceDTO);

        InvoiceDTO result = invoiceService.create(invoiceDTO);

        assertNotNull(result);
        assertEquals("BWJ0087837", result.getUniqueId());
        verify(invoiceRepository, times(1)).save(invoice);
    }

    @Test
    void testGetAllInvoices() {
        Invoice invoice1 = new Invoice();
        invoice1.setId("1");
        Invoice invoice2 = new Invoice();
        invoice2.setId("2");

        List<Invoice> invoices = List.of(invoice1, invoice2);
        List<InvoiceDTO> invoiceDTOs = List.of(new InvoiceDTO(), new InvoiceDTO());

        when(invoiceRepository.findAll()).thenReturn(invoices);
        when(mapper.toDTO(any(Invoice.class))).thenReturn(new InvoiceDTO());

        List<InvoiceDTO> result = invoiceService.getAllInvoices();

        assertEquals(invoiceDTOs.size(), result.size());
        verify(invoiceRepository, times(1)).findAll();
        verify(mapper, times(2)).toDTO(any(Invoice.class));
    }

    @Test
    void testUpdateInvoice() {
        String invoiceId = "1";
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setAddress("New Address");
        Invoice existingInvoice = new Invoice();
        existingInvoice.setId(invoiceId);
        existingInvoice.setAddress("Old Address");

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(existingInvoice));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(existingInvoice);
        when(mapper.toDTO(any(Invoice.class))).thenReturn(invoiceDTO);

        InvoiceDTO result = invoiceService.updateInvoice(invoiceId, invoiceDTO);

        assertEquals(invoiceDTO.getAddress(), result.getAddress());
        verify(invoiceRepository, times(1)).findById(invoiceId);
        verify(invoiceRepository, times(1)).save(existingInvoice);
        verify(mapper, times(1)).toDTO(existingInvoice);
    }

    @Test
    void testDeleteInvoice() {
        String invoiceId = "1";

        doNothing().when(invoiceRepository).deleteById(invoiceId);

        invoiceService.deleteInvoice(invoiceId);

        verify(invoiceRepository, times(1)).deleteById(invoiceId);
    }

    @Test
    void testGetInvoiceByUniqueId() {
        String uniqueId = "BWJ0087837";
        Invoice invoice = new Invoice();
        invoice.setUniqueId(uniqueId);
        InvoiceDTO invoiceDTO = new InvoiceDTO();

        when(invoiceRepository.findByUniqueId(uniqueId)).thenReturn(Optional.of(invoice));
        when(mapper.toDTO(invoice)).thenReturn(invoiceDTO);

        InvoiceDTO result = invoiceService.getInvoiceByUniqueId(uniqueId);

        assertNotNull(result);
        verify(invoiceRepository, times(1)).findByUniqueId(uniqueId);
        verify(mapper, times(1)).toDTO(invoice);
    }

    @Test
    void testGetInvoiceBySequential() {
        String sequential = "002-002-000000002";
        Invoice invoice = new Invoice();
        invoice.setSequential(sequential);
        InvoiceDTO invoiceDTO = new InvoiceDTO();

        when(invoiceRepository.findBySequential(sequential)).thenReturn(Optional.of(invoice));
        when(mapper.toDTO(invoice)).thenReturn(invoiceDTO);

        InvoiceDTO result = invoiceService.getInvoiceBySequential(sequential);

        assertNotNull(result);
        verify(invoiceRepository, times(1)).findBySequential(sequential);
        verify(mapper, times(1)).toDTO(invoice);
    }

    @Test
    void testGetInvoicesByDateRange() {
        LocalDateTime startDate = LocalDateTime.of(2024, 8, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 8, 31, 23, 59);
        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();
        List<Invoice> invoices = List.of(invoice1, invoice2);
        List<InvoiceDTO> invoiceDTOs = List.of(new InvoiceDTO(), new InvoiceDTO());

        when(invoiceRepository.findByDateRange(startDate, endDate)).thenReturn(invoices);
        when(mapper.toDTO(any(Invoice.class))).thenReturn(new InvoiceDTO());

        List<InvoiceDTO> result = invoiceService.getInvoicesByDateRange(startDate, endDate);

        assertEquals(invoiceDTOs.size(), result.size());
        verify(invoiceRepository, times(1)).findByDateRange(startDate, endDate);
        verify(mapper, times(2)).toDTO(any(Invoice.class));
    }

    @Test
    void testGetInvoicesByClient() {
        String ruc = "1717094955001";
        String companyName = "La Favorita";
        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();
        List<Invoice> invoices = List.of(invoice1, invoice2);
        List<InvoiceDTO> invoiceDTOs = List.of(new InvoiceDTO(), new InvoiceDTO());

        when(invoiceRepository.findByRucAndCompanyNameContainingIgnoreCase(ruc, companyName)).thenReturn(invoices);
        when(mapper.toDTO(any(Invoice.class))).thenReturn(new InvoiceDTO());

        List<InvoiceDTO> result = invoiceService.getInvoicesByClient(ruc, companyName);

        assertEquals(invoiceDTOs.size(), result.size());
        verify(invoiceRepository, times(1)).findByRucAndCompanyNameContainingIgnoreCase(ruc, companyName);
        verify(mapper, times(2)).toDTO(any(Invoice.class));
    }

}
