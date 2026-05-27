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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetClienteServiceTest {

    @Mock private ClienteRepositoryPort clienteRepository;
    @Mock private DireccionRepositoryPort direccionRepository;

    private GetClienteService service;

    @BeforeEach
    void setUp() {
        service = new GetClienteService(clienteRepository, direccionRepository);
    }

    @Test
    void shouldReturnClienteWithDirecciones() {
        Cliente cliente = new Cliente(1L, "Juan", "Perez", "123456", "CC", 30, true, List.of());
        Direccion dir = new Direccion(1L, "Antioquia", "Medellín", "Calle 10");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(direccionRepository.findAllByClienteId(1L)).thenReturn(List.of(dir));

        Cliente result = service.getById(1L);

        assertThat(result.getDirecciones()).hasSize(1);
        assertThat(result.getDirecciones().get(0).getCiudad()).isEqualTo("Medellín");
    }

    @Test
    void shouldThrowExceptionWhenClienteNotFound() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(ClienteNotFoundException.class)
                .hasMessageContaining("99");
    }
}
