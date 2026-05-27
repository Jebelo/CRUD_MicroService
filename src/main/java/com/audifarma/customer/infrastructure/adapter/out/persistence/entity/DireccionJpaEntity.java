package com.audifarma.customer.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "direcciones")
public class DireccionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String departamento;

    @Column(nullable = false)
    private String ciudad;

    @Column(name = "direccion_completa", nullable = false)
    private String direccionCompleta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteJpaEntity cliente;

    protected DireccionJpaEntity() {}

    public DireccionJpaEntity(Long id,
                              String departamento,
                              String ciudad,
                              String direccionCompleta,
                              ClienteJpaEntity cliente) {
        this.id = id;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.direccionCompleta = direccionCompleta;
        this.cliente = cliente;
    }

    public Long getId() { return id; }
    public String getDepartamento() { return departamento; }
    public String getCiudad() { return ciudad; }
    public String getDireccionCompleta() { return direccionCompleta; }
    public ClienteJpaEntity getCliente() { return cliente; }
}
