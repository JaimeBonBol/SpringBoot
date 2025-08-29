package com.gestor_inventario.repositorio;

import com.gestor_inventario.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    Usuario findByUsername(String username);

}
