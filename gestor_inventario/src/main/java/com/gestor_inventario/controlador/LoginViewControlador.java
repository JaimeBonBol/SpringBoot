package com.gestor_inventario.controlador;

import com.gestor_inventario.modelo.Usuario;
import com.gestor_inventario.servicio.UsuarioServicio;
import com.gestor_inventario.sesion.SesionServicio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginViewControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private SesionServicio sesionServicio;

    /**
     * Mapeo de los componentes de la vista
     */

    @FXML
    private TextField usernameLoginTexto;

    @FXML
    private PasswordField passwordLoginTexto;

    @FXML
    private Button loginBoton;

    @FXML
    private Button cancelarBoton;

    public void login() {
        String username = usernameLoginTexto.getText();
        String password = passwordLoginTexto.getText();

        if (username.isEmpty() || password.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar usuario y contraseña");
            return;
        }

        Usuario usuario = usuarioServicio.logearUsuario(username, password);

        if (usuario != null) {
            sesionServicio.setUsuarioLogeado(usuario);
            mostrarMensaje("Información", "Sesión iniciada como " + sesionServicio.getUsuarioLogeado().getUsername());
            cerrarVentana();
        } else {
            mostrarMensaje("Error", "Usuario inexistente o credenciales incorrectas");
        }
    }


    /**
     * Mñetodo para cerrar ventana a partir de recuperar la escena desde un componente de ella
     */
    public void cerrarVentana(){
        // Se recupera la ventana en la que estoy a traves de un componente de ella.
        Stage stage = (Stage) loginBoton.getScene().getWindow();

        stage.close();
    }

    /**
     * Método para mostrar mensajes
     */
    public void mostrarMensaje(String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
