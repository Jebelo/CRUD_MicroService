package com.audifarma.customer.application.usecase;

import com.audifarma.customer.domain.exception.ClienteNotFoundException;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddDireccionServiceTest {

    @Mock private ClienteRepositoryPort clienteRepository;
    @Mock private DireccionRepositoryPort direccionRepository;

    private AddDireccionService service;

    @BeforeEach
    void setUp() {
        service = new AddDireccionService(clienteRepository, direccionRepository);
    }

    @Test
    void shouldAddDireccionSuccessfully() {
        Cliente cliente = new Cliente(1L, "Juan", "Perez", "123456", "CC", 30, true, List.of());
        Direccion input = new Direccion(null, "Antioquia", "Medellín", "Calle 10");
        Direccion saved = new Direccion(5L, "Antioquia", "Medellín", "Calle 10");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(direccionRepository.save(any(), eq(1L))).thenReturn(saved);

        Direccion result = service.addDireccion(1L, input);

        assertThat(result.getId()).isEqualTo(5L);
    }

    @Test
    void shouldThrowExceptionWhenClienteNotFound() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.addDireccion(99L, new Direccion(null, "X", "Y", "Z")))
                .isInstanceOf(ClienteNotFoundException.class)
                .hasMessageContaining("99");
    }
}
