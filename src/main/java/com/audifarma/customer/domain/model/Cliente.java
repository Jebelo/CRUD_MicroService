package com.audifarma.customer.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cliente domain entity — pure Java, no framework dependencies.
 * Immutable: every mutating operation returns a new instance.
 */
public class Cliente {

    private final Long id;
    private final String nombre;
    private final String apellido;
    private final String numeroDocumento;
    private final String tipoDocumento;
    private final Integer edad;
    private final Boolean activo;
    private final List<Direccion> direcciones;

    public Cliente(Long id,
                   String nombre,
                   String apellido,
                   String numeroDocumento,
                   String tipoDocumento,
                   Integer edad,
                   Boolean activo,
                   List<Direccion> direcciones) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.edad = edad;
        this.activo = activo;
        this.direcciones = direcciones == null
                ? Collections.emptyList()
                : Collections.unmodifiableList(new ArrayList<>(direcciones));
    }

    // ── Domain behaviour ────────────────────────────────────────────────────

    public Cliente addDireccion(Direccion direccion) {
        List<Direccion> updated = new ArrayList<>(this.direcciones);
        updated.add(direccion);
        return new Cliente(id, nombre, apellido, numeroDocumento, tipoDocumento, edad, activo, updated);
    }

    public Cliente removeDireccion(Long direccionId) {
        List<Direccion> updated = this.direcciones.stream()
                .filter(d -> !d.getId().equals(direccionId))
                .toList();
        return new Cliente(id, nombre, apellido, numeroDocumento, tipoDocumento, edad, activo, updated);
    }

    public Cliente updateData(String nombre,
                              String apellido,
                              String tipoDocumento,
                              Integer edad,
                              Boolean activo) {
        return new Cliente(id, nombre, apellido, numeroDocumento, tipoDocumento, edad, activo, direcciones);
    }

    public Cliente withId(Long newId) {
        return new Cliente(newId, nombre, apellido, numeroDocumento, tipoDocumento, edad, activo, direcciones);
    }

    public Cliente withDirecciones(List<Direccion> newDirecciones) {
        return new Cliente(id, nombre, apellido, numeroDocumento, tipoDocumento, edad, activo, newDirecciones);
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public String getTipoDocumento() { return tipoDocumento; }
    public Integer getEdad() { return edad; }
    public Boolean getActivo() { return activo; }
    public List<Direccion> getDirecciones() { return direcciones; }
}
