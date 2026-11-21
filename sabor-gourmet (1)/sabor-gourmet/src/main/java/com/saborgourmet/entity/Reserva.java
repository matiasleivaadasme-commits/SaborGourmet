
package com.saborgourmet.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

// Indica que esta clase representa una entidad almacenada en la base de datos.
@Entity
@Table(name = "reservas")
public class Reserva {

    // Identificador único de la reserva, generado automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relaciona cada reserva con un cliente.
    // Muchas reservas pueden pertenecer al mismo cliente.
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Relaciona cada reserva con una mesa del restaurante.
    // Una mesa puede estar asociada a distintas reservas en diferentes horarios.
    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

    // Fecha en que se realizará la reserva.
    @Column(nullable = false)
    private LocalDate fecha;

    // Hora programada para la reserva.
    @Column(nullable = false)
    private LocalTime hora;

    // Cantidad de personas que asistirán a la reserva.
    @Column(nullable = false)
    private Integer cantidadPersonas;

    // Estado de la reserva. Por defecto se crea como CONFIRMADA.
    @Column(nullable = false, length = 20)
    private String estado = "CONFIRMADA";

    // Constructor vacío requerido por JPA.
    public Reserva() {
    }

    // Devuelve el identificador de la reserva.
    public Long getId() {
        return id;
    }

    // Permite establecer el identificador de la reserva.
    public void setId(Long id) {
        this.id = id;
    }

    // Devuelve el cliente asociado a la reserva.
    public Cliente getCliente() {
        return cliente;
    }

    // Permite asignar un cliente a la reserva.
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Devuelve la mesa asociada a la reserva.
    public Mesa getMesa() {
        return mesa;
    }

    // Permite asignar una mesa a la reserva.
    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    // Devuelve la fecha de la reserva.
    public LocalDate getFecha() {
        return fecha;
    }

    // Permite establecer la fecha de la reserva.
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    // Devuelve la hora de la reserva.
    public LocalTime getHora() {
        return hora;
    }

    // Permite establecer la hora de la reserva.
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    // Devuelve la cantidad de personas de la reserva.
    public Integer getCantidadPersonas() {
        return cantidadPersonas;
    }

    // Permite modificar la cantidad de personas.
    public void setCantidadPersonas(Integer cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    // Devuelve el estado actual de la reserva.
    public String getEstado() {
        return estado;
    }

    // Permite modificar el estado de la reserva.
    public void setEstado(String estado) {
        this.estado = estado;
    }

}
