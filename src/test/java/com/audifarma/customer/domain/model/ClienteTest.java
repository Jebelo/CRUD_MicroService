package com.audifarma.customer.domain.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteTest {

    private Cliente buildCliente() {
        return new Cliente(1L, "Juan", "Perez", "123456", "CC", 30, true, new ArrayList<>());
    }

    private Direccion buildDireccion() {
        return new Direccion(1L, "Antioquia", "Medellín", "Calle 10 #20-30");
    }

    @Test
    void shouldAddDireccionReturningNewInstance() {
        Cliente cliente = buildCliente();
        Direccion direccion = buildDireccion();

        Cliente updated = cliente.addDireccion(direccion);

        assertThat(updated).isNotSameAs(cliente);
        assertThat(updated.getDirecciones()).hasSize(1);
        assertThat(cliente.getDirecciones()).isEmpty();
    }

    @Test
    void shouldRemoveDireccionReturningNewInstance() {
        Direccion d1 = new Direccion(1L, "Antioquia", "Medellín", "Calle 10");
        Direccion d2 = new Direccion(2L, "Bogotá", "Bogotá", "Carrera 7");
        Cliente cliente = new Cliente(1L, "Juan", "Perez", "123456", "CC", 30, true, List.of(d1, d2));

        Cliente updated = cliente.removeDireccion(1L);

        assertThat(updated).isNotSameAs(cliente);
        assertThat(updated.getDirecciones()).hasSize(1);
        assertThat(updated.getDirecciones().get(0).getId()).isEqualTo(2L);
    }

    @Test
    void shouldUpdateDataReturningNewInstance() {
        Cliente cliente = buildCliente();

        Cliente updated = cliente.updateData("Maria", "Lopez", "TI", 25, false);

        assertThat(updated).isNotSameAs(cliente);
        assertThat(updated.getNombre()).isEqualTo("Maria");
        assertThat(updated.getApellido()).isEqualTo("Lopez");
        assertThat(updated.getTipoDocumento()).isEqualTo("TI");
        assertThat(updated.getEdad()).isEqualTo(25);
        assertThat(updated.getActivo()).isFalse();
        // numeroDocumento is preserved
        assertThat(updated.getNumeroDocumento()).isEqualTo("123456");
    }

    @Test
    void shouldReturnImmutableDireccionesList() {
        Cliente cliente = buildCliente().addDireccion(buildDireccion());

        assertThat(cliente.getDirecciones()).isUnmodifiable();
    }
}
