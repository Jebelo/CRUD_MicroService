package com.audifarma.customer.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class ClienteJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(name = "numero_documento", nullable = false, unique = true)
    private String numeroDocumento;

    @Column(name = "tipo_documento", nullable = false)
    private String tipoDocumento;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false)
    private Boolean activo;

    // Direcciones are loaded separately via DireccionJpaRepository.findAllByClienteId()
    // No bidirectional @OneToMany here — avoids orphanRemoval risk when saving scalars.

    protected ClienteJpaEntity() {}

    public ClienteJpaEntity(Long id,
                            String nombre,
                            String apellido,
                            String numeroDocumento,
                            String tipoDocumento,
                            Integer edad,
                            Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.edad = edad;
        this.activo = activo;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public String getTipoDocumento() { return tipoDocumento; }
    public Integer getEdad() { return edad; }
    public Boolean getActivo() { return activo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
