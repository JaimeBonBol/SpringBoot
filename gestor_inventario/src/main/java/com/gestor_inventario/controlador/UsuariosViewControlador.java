package com.gestor_inventario.controlador;

import com.gestor_inventario.modelo.Producto;
import com.gestor_inventario.modelo.Usuario;
import com.gestor_inventario.servicio.UsuarioServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class UsuariosViewControlador implements Initializable {

    @Autowired
    private UsuarioServicio usuarioServicio;

    /**
     * Mapeo componentes vista
     */

    @FXML
    private TableView<Usuario> usuariosTabla;

    @FXML
    private TableColumn<Usuario, Integer> idUsuarioColumna;

    @FXML
    private TableColumn<Usuario, String> nombreUsuarioColumna;

    @FXML
    private TableColumn<Usuario, String> usernameUsuarioColumna;

    @FXML
    private TableColumn<Usuario, String> passwordUsuarioColumna;

    @FXML
    private TableColumn<Usuario, String> rolUsuarioColumna;

    @FXML
    private TextField nombreUsuarioTexto;

    @FXML
    private TextField usernameUsuarioTexto;

    @FXML
    private TextField passwordUsuarioTexto;

    @FXML
    private TextField rolUsuarioTexto;

    @FXML
    private Button volverBoton;

    /**
     * ObservableList para que cualquier cambio sobre esta lista se refleje de manera automática
     */
    protected final ObservableList<Usuario> usuariosLista = FXCollections.observableArrayList();

    private Integer idUsuarioInterno;

    private final ApplicationContext context;


    // Inyección por constructor
    public UsuariosViewControlador(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuariosTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        configurarColumnas();

        listarUsuarios();
    }

    /**
     * Metodo para mapear los atributos FMXL con los de nuestra entidad
     * Tienen que ser exactamente los mismos nombres de los atributos de la clase modelo Usuario
     */
    public void configurarColumnas(){
        idUsuarioColumna.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        nombreUsuarioColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        usernameUsuarioColumna.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordUsuarioColumna.setCellValueFactory(new PropertyValueFactory<>("password"));
        rolUsuarioColumna.setCellValueFactory(new PropertyValueFactory<>("rol"));
    }


    /**
     * usuariosLista es como el modelo, primero se limpia y se carga en ella todos los usuarios de la bd.
     * usuariosTabla es donde realmente se muestran los usuarios, por eso una vez están en la lista se cargan en la tabla.
     */
    public void listarUsuarios(){
        usuariosLista.clear();

        // Cragar todos los usuarios de la base de datos
        usuariosLista.addAll(usuarioServicio.listarUsuarios());

        // Relacionar la lista a la tabla de la vista (agregar la información a la tabla)
        usuariosTabla.setItems(usuariosLista);
    }


    /**
     * Método asociado al botón dsew voover para volver a la pantalla anterior, es decir obtiene la escena actual del botón
     * desde donde se actua y cambia la escena a productosview.fxml
     * @throws IOException
     */
    public void inventarioProductos() throws IOException {
        // Cargar FXML de productosView
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/productosview.fxml"));
        loader.setControllerFactory(context::getBean);

        Parent root = loader.load();
        Scene escena = new Scene(root);

        // Obtener el Stage Actual desde el botón
        Stage stage = (Stage) volverBoton.getScene().getWindow();

        // Reemplazar la escena actual
        stage.setScene(escena);
        stage.setTitle("Inventario Productos");
    }



    /**
     * Método para limpiar formulario y resetear el ID de la variable creada interna, para que no haya problemas ya que
     * dependiendo del valor de esta, se agregará nuevo usuario o se modificará.
     */
    public void limpiarFormulario(){
        idUsuarioInterno = null;    // ASegurar que se resetea el id interno
        nombreUsuarioTexto.clear();
        usernameUsuarioTexto.clear();
        passwordUsuarioTexto.clear();
        rolUsuarioTexto.clear();
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

    /**
     * Método para cargar un usuario en los textFields desde la tabla al clickar en una fila. Rellena los textField.
     */
    public void cargarUsuarioTabla(){
        // Recoger el usuario seleccionado
        Usuario usuario = usuariosTabla.getSelectionModel().getSelectedItem();

        // Por si da click en otra parte de la tabla se comprueba que no sea null.
        // Se carga en nuestra variable id interna el valor de la id y en los TextFields los atributos del usuario seleccionado.
        if (usuario != null){
            idUsuarioInterno = usuario.getIdUsuario();
            nombreUsuarioTexto.setText(usuario.getNombre());
            usernameUsuarioTexto.setText(usuario.getUsername());
            passwordUsuarioTexto.setText(usuario.getPassword());
            rolUsuarioTexto.setText(usuario.getRol());
        }
    }

    /**
     * Método para asignar a un objeto Usuario los datos de los textFIelds.
     */
    public void recolectarDatosUsuario(Usuario usuario){
        // Si el usuario ya existe, entonces la variable idUsuarioInterno tiene un valor, si no existe no se asigna ningún
        // valor a la id interna y por lo tanto se creará nuevo usuario.
        if (idUsuarioInterno != null){
            usuario.setIdUsuario(idUsuarioInterno);
        }

        usuario.setNombre(nombreUsuarioTexto.getText());
        usuario.setUsername(usernameUsuarioTexto.getText());
        usuario.setPassword(passwordUsuarioTexto.getText());
        usuario.setRol(rolUsuarioTexto.getText());
    }

}
