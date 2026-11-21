
package com.saborgourmet.entity;

import jakarta.persistence.*;

// Indica que esta clase representa una entidad almacenada en la base de datos.
@Entity
@Table(name = "mesas")
public class Mesa {

    // Identificador único de la mesa, generado automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Número de mesa. Es obligatorio y no puede repetirse.
    @Column(nullable = false, unique = true)
    private Integer numero;

    // Cantidad de personas que puede recibir la mesa.
    @Column(nullable = false)
    private Integer capacidad;

    // Indica si la mesa está disponible. Por defecto comienza disponible.
    @Column(nullable = false)
    private boolean disponible = true;

    // Constructor vacío requerido por JPA.
    public Mesa() {
    }

    // Devuelve el identificador de la mesa.
    public Long getId() {
        return id;
    }

    // Permite establecer el identificador de la mesa.
    public void setId(Long id) {
        this.id = id;
    }

    // Devuelve el número de la mesa.
    public Integer getNumero() {
        return numero;
    }

    // Permite establecer el número de la mesa.
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    // Devuelve la capacidad de personas de la mesa.
    public Integer getCapacidad() {
        return capacidad;
    }

    // Permite establecer la capacidad de la mesa.
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    // Indica si la mesa se encuentra disponible.
    public boolean isDisponible() {
        return disponible;
    }

    // Permite modificar la disponibilidad de la mesa.
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

}
