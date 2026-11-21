
package com.saborgourmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saborgourmet.entity.Reserva;

// Repositorio encargado de gestionar el acceso a los datos de las reservas.
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // JpaRepository proporciona automáticamente operaciones para:
    // guardar, listar, buscar por ID y eliminar reservas.
    // Reserva corresponde a la entidad y Long al tipo de su identificador.

}
