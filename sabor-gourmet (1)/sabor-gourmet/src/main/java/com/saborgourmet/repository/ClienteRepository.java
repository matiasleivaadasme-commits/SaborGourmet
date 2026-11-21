
package com.saborgourmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saborgourmet.entity.Cliente;

// Repositorio encargado de gestionar las operaciones de acceso
// a los datos de los clientes en la base de datos.
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // JpaRepository proporciona automáticamente operaciones como:
    // guardar, listar, buscar por ID y eliminar clientes.
    // Cliente corresponde a la entidad y Long al tipo de su identificador.

}
