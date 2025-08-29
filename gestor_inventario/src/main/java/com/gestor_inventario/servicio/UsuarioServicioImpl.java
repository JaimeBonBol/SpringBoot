package com.gestor_inventario.servicio;

import com.gestor_inventario.modelo.Usuario;
import com.gestor_inventario.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicioImpl implements UsuarioServicio{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public Usuario listarUsuarioPorId(Integer id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        usuarioRepositorio.delete(usuario);
    }

    @Override
    public Usuario logearUsuario(String username, String password) {
        // Buscar el usuario en la bd
        Usuario usuarioLog = usuarioRepositorio.findByUsername(username);

        if (usuarioLog != null){
            if (usuarioLog.getPassword().equals(password)){
                return usuarioLog;  // Login correcto
            }
        }

        return null;    // Devuelve null si no existe o la contraseña no es correcta
    }
}
