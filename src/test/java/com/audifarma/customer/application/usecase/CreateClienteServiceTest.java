package com.audifarma.customer.application.usecase;

import com.audifarma.customer.domain.exception.DuplicateClienteException;
import com.audifarma.customer.domain.model.Cliente;
import com.audifarma.customer.domain.model.Direccion;
import com.audifarma.customer.domain.port.out.ClienteRepositoryPort;
import com.audifarma.customer.domain.port.out.DireccionRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateClienteServiceTest {

    @Mock private ClienteRepositoryPort clienteRepository;
    @Mock private DireccionRepositoryPort direccionRepository;

    private CreateClienteService service;

    @BeforeEach
    void setUp() {
        service = new CreateClienteService(clienteRepository, direccionRepository);
    }

    @Test
    void shouldCreateClienteSuccessfully() {
        Cliente input = new Cliente(null, "Juan", "Perez", "123456", "CC", 30, true, List.of());
        Cliente saved = new Cliente(1L, "Juan", "Perez", "123456", "CC", 30, true, List.of());

        when(clienteRepository.existsByNumeroDocumento("123456")).thenReturn(false);
        when(clienteRepository.save(any())).thenReturn(saved);

        Cliente result = service.create(input);

        assertThat(result.getId()).isEqualTo(1L);
        verify(clienteRepository).save(any());
        verifyNoInteractions(direccionRepository);
    }

    @Test
    void shouldPersistDireccionesWhenProvided() {
        Direccion dir = new Direccion(null, "Antioquia", "Medellín", "Calle 10");
        Cliente input = new Cliente(null, "Juan", "Perez", "123456", "CC", 30, true, List.of(dir));
        Cliente savedCliente = new Cliente(1L, "Juan", "Perez", "123456", "CC", 30, true, List.of());
        Direccion savedDir = new Direccion(10L, "Antioquia", "Medellín", "Calle 10");

        when(clienteRepository.existsByNumeroDocumento("123456")).thenReturn(false);
        when(clienteRepository.save(any())).thenReturn(savedCliente);
        when(direccionRepository.save(any(), eq(1L))).thenReturn(savedDir);

        Cliente result = service.create(input);

        assertThat(result.getDirecciones()).hasSize(1);
        assertThat(result.getDirecciones().get(0).getId()).isEqualTo(10L);
    }

    @Test
    void shouldThrowExceptionWhenNumeroDocumentoDuplicated() {
        Cliente input = new Cliente(null, "Juan", "Perez", "123456", "CC", 30, true, List.of());

        when(clienteRepository.existsByNumeroDocumento("123456")).thenReturn(true);

        assertThatThrownBy(() -> service.create(input))
                .isInstanceOf(DuplicateClienteException.class)
                .hasMessageContaining("123456");

        verify(clienteRepository, never()).save(any());
    }
}
