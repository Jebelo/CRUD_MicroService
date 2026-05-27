package com.audifarma.customer.application.usecase;

import com.audifarma.customer.domain.exception.ClienteNotFoundException;
import com.audifarma.customer.domain.exception.DireccionNotFoundException;
import com.audifarma.customer.domain.model.Cliente;
import com.audifarma.customer.domain.port.out.ClienteRepositoryPort;
import com.audifarma.customer.domain.port.out.DireccionRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveDireccionServiceTest {

    @Mock private ClienteRepositoryPort clienteRepository;
    @Mock private DireccionRepositoryPort direccionRepository;

    private RemoveDireccionService service;

    @BeforeEach
    void setUp() {
        service = new RemoveDireccionService(clienteRepository, direccionRepository);
    }

    @Test
    void shouldRemoveDireccionSuccessfully() {
        Cliente cliente = new Cliente(1L, "Juan", "Perez", "123456", "CC", 30, true, List.of());

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(direccionRepository.existsByIdAndClienteId(5L, 1L)).thenReturn(true);

        assertThatCode(() -> service.removeDireccion(1L, 5L)).doesNotThrowAnyException();
        verify(direccionRepository).deleteById(5L);
    }

    @Test
    void shouldThrowExceptionWhenClienteNotFound() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.removeDireccion(99L, 5L))
                .isInstanceOf(ClienteNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenDireccionDoesNotBelongToCliente() {
        Cliente cliente = new Cliente(1L, "Juan", "Perez", "123456", "CC", 30, true, List.of());

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(direccionRepository.existsByIdAndClienteId(5L, 1L)).thenReturn(false);

        assertThatThrownBy(() -> service.removeDireccion(1L, 5L))
                .isInstanceOf(DireccionNotFoundException.class);

        verify(direccionRepository, never()).deleteById(any());
    }
}
