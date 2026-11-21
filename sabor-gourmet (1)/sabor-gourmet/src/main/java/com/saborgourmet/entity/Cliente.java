
package com.saborgourmet.entity;

import jakarta.persistence.*;

// Indica que esta clase representa una entidad que se almacenará en la base de datos.
@Entity
@Table(name = "clientes")
public class Cliente {

    // Identificador único del cliente, generado automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del cliente. Es obligatorio y admite hasta 100 caracteres.
    @Column(nullable = false, length = 100)
    private String nombre;

    // Correo electrónico obligatorio y único para cada cliente.
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    // Número telefónico obligatorio del cliente.
    @Column(nullable = false, length = 20)
    private String telefono;

    // Constructor vacío requerido por JPA para crear instancias de la entidad.
    public Cliente() {
    }

    // Devuelve el identificador del cliente.
    public Long getId() {
        return id;
    }

    // Permite establecer el identificador del cliente.
    public void setId(Long id) {
        this.id = id;
    }

    // Devuelve el nombre del cliente.
    public String getNombre() {
        return nombre;
    }

    // Permite establecer el nombre del cliente.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devuelve el correo electrónico del cliente.
    public String getEmail() {
        return email;
    }

    // Permite establecer el correo electrónico del cliente.
    public void setEmail(String email) {
        this.email = email;
    }

    // Devuelve el teléfono del cliente.
    public String getTelefono() {
        return telefono;
    }

    // Permite establecer el teléfono del cliente.
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
