package com.audifarma.customer.domain.model;

/**
 * Direccion domain entity — pure Java, no framework dependencies.
 */
public class Direccion {

    private final Long id;
    private final String departamento;
    private final String ciudad;
    private final String direccionCompleta;

    public Direccion(Long id, String departamento, String ciudad, String direccionCompleta) {
        this.id = id;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.direccionCompleta = direccionCompleta;
    }

    public Long getId() { return id; }
    public String getDepartamento() { return departamento; }
    public String getCiudad() { return ciudad; }
    public String getDireccionCompleta() { return direccionCompleta; }

    public Direccion withId(Long newId) {
        return new Direccion(newId, departamento, ciudad, direccionCompleta);
    }
}
