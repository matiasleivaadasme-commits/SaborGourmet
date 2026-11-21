
package com.saborgourmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saborgourmet.entity.Mesa;

// Repositorio encargado de gestionar el acceso a los datos de las mesas.
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    // JpaRepository proporciona automáticamente operaciones para:
    // guardar, listar, buscar por ID y eliminar mesas.
    // Mesa corresponde a la entidad y Long al tipo de su identificador.

}
