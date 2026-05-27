package com.audifarma.customer.application.usecase;

import com.audifarma.customer.domain.exception.ClienteNotFoundException;
import com.audifarma.customer.domain.model.Cliente;
import com.audifarma.customer.domain.port.out.ClienteRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateClienteServiceTest {

    @Mock private ClienteRepositoryPort clienteRepository;

    private UpdateClienteService service;

    @BeforeEach
    void setUp() {
        service = new UpdateClienteService(clienteRepository);
    }

    @Test
    void shouldUpdateClienteSuccessfully() {
        Cliente existing = new Cliente(1L, "Juan", "Perez", "123456", "CC", 30, true, List.of());
        Cliente updatedData = new Cliente(null, "Maria", "Lopez", null, "TI", 25, false, List.of());
        Cliente expected = new Cliente(1L, "Maria", "Lopez", "123456", "TI", 25, false, List.of());

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(clienteRepository.save(any())).thenReturn(expected);

        Cliente result = service.update(1L, updatedData);

        assertThat(result.getNombre()).isEqualTo("Maria");
        assertThat(result.getNumeroDocumento()).isEqualTo("123456");
        verify(clienteRepository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenClienteNotFound() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99L, new Cliente(null, "x", "y", null, "CC", 1, true, List.of())))
                .isInstanceOf(ClienteNotFoundException.class)
                .hasMessageContaining("99");
    }
}
