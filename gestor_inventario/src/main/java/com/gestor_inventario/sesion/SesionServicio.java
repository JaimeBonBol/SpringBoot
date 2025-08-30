package com.gestor_inventario.sesion;

import com.gestor_inventario.modelo.Usuario;
import org.springframework.stereotype.Component;

/**
 * Clase para mantener el usuario logeado.
 * Este componente guardará el usuario activo y se podrá consultar desde cualquier controlador.
 */

@Component
public class SesionServicio {

    private Usuario usuarioLogeado;

    public void setUsuarioLogeado(Usuario usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }

    public Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void logout(){
        usuarioLogeado = null;
    }
}
