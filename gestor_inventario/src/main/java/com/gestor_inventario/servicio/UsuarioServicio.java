package com.gestor_inventario.servicio;


import com.gestor_inventario.modelo.Usuario;

import java.util.List;

public interface UsuarioServicio {

    List<Usuario> listarUsuarios();

    Usuario listarUsuarioPorId(Integer id);

    // Se utiliza para guardar nuevo usuario y actualizar, lo hace automaticamente Hibernate, dependiendo si tiene id o no.
    Usuario guardarUsuario(Usuario usuario);

    void eliminarUsuario(Usuario usuario);

    // Devuelve el usuario que ha saido logeado, si no coinciden las credenciales devuelve null.
    Usuario logearUsuario(String username, String password);

}
