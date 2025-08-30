package com.gestor_inventario.controlador;

import com.gestor_inventario.GestorInventarioApplication;
import com.gestor_inventario.modelo.Producto;
import com.gestor_inventario.modelo.Usuario;
import com.gestor_inventario.servicio.ProductoServicio;
import com.gestor_inventario.sesion.SesionServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ProductosViewControlador implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(ProductosViewControlador.class);

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private SesionServicio sesionServicio;

    /**
     * Mapeo componentes vista
     */

    @FXML
    private TableView<Producto> productosTabla;

    @FXML
    private TableColumn<Producto, Integer> idProductoColumna;

    @FXML
    private TableColumn<Producto, String> nombreProductoColumna;

    @FXML
    private TableColumn<Producto, Integer> stockProductoColumna;

    @FXML
    private TableColumn<Producto, Float> precioProductoColumna;

    @FXML
    private Button gestionBoton;

    @FXML
    private Button gestionUsuariosBoton;

    @FXML
    private Button loginBoton;

    @FXML
    private Button logoutBoton;

    @FXML
    private TextField sesionUsuarioTexto;

    /**
     * ObservableList para que cualquier cambio sobre esta lista se refleje de manera automática
     */
    protected final ObservableList<Producto> productosLista = FXCollections.observableArrayList();

    private final ApplicationContext context;

    // Atributo para asiganr contraseña
    private final String PASSWORD = "1234";

    // Inyección por constructor
    public ProductosViewControlador(ApplicationContext context) {
        this.context = context;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productosTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        configurarColumnas();

        listarProductos();

        mostrarSesion();
    }

    /**
     * Metodo para mapear los atributos FMXL con los de nuestra entidad
     * Tienen que ser exactamente los mismos nombres de los atributos de la clase modelo Tarea
     */
    public void configurarColumnas(){
        idProductoColumna.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        nombreProductoColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        stockProductoColumna.setCellValueFactory(new PropertyValueFactory<>("stock"));
        precioProductoColumna.setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    /**
     * productosLista es como el modelo, primero se limpia y se carga en ella todos los productos de la bd.
     * productosTabla es donde realmente se muestran los productos, por eso una vez están en la lista se cargan en la tabla.
     */
    public void listarProductos(){
        logger.info("Ejecutando listado de productos");

        productosLista.clear();

        // Cargar todos los productos de la base de datos
        productosLista.addAll(productoServicio.listarProductos());

        // Relacionar la lista a la tabla de la vista (agregar la información a la tabla)
        productosTabla.setItems(productosLista);
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
     * Método asosciado al boton para cambiar de ventana, primero comprueba el usuario logeado
     * y si tiene permisos llama al método de abrirGestion que es el que realmente cambia la vista.
     */
    public void gestionProductos() throws IOException {

        // Obtener el usuario logeado
        Usuario usuario = sesionServicio.getUsuarioLogeado();

        // Comprobar que la sesión está iniciada
        if (usuario != null){
            // Si el rol es ADMIN u OPERARIO entonces tiene permisos para entrar, de lo contrario no tiene pemrisos.
            if (usuario.getRol().equals("ADMIN") || usuario.getRol().equals("OPERARIO")){
                mostrarMensaje("Información", "Entrando a la gestión de inventario como " + usuario.getUsername() + " con el rol " + usuario.getRol());
                abrirGestion();
            }else {
                mostrarMensaje("Error", "No tiene permisos para la acción");
            }
        }else {
            mostrarMensaje("Información", "Debe iniciar sesión");
        }

    }


    /**
     * Método para cambiar la vista, lo que hace es crear una nueva escena con la plantilla gestionview.fxml y la sustituye
     * en la escena donde se encuentra el boton.
     * @throws IOException
     */
    public void abrirGestion() throws IOException {

        // Cargar el FXML de gestión de productos
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/gestionview.fxml"));
        loader.setControllerFactory(context::getBean); // Integrar Spring con JavaFX

        Parent root = loader.load();
        Scene escena = new Scene(root);

        // Obtener el Stage actual desde el botón
        Stage stage = (Stage) gestionBoton.getScene().getWindow();

        // Reemplazar la escena actual
        stage.setScene(escena);
        stage.setTitle("Gestión de Inventario");

    }

    /**
     * Método para cambiar la vista, lo que hace es crear una nueva escena con la plantilla usuariosview.fxml y la sustituye
     * en la escena donde se encuentra el boton.
     * @throws IOException
     */
    public void abrirGestionUsuarios() throws IOException {

        // Obtener el usuario logeado
        Usuario usuario = sesionServicio.getUsuarioLogeado();

        // Comprobar que la sesión está iniciada
        if (usuario !=null){
            // Si el usuario es ADMIN entonces tiene permisos, de lo contrario no los tiene.
            if (usuario.getRol().equals("ADMIN")){

                mostrarMensaje("Información", "Entrando a la gestión de usuarios como " + usuario.getUsername() + " con el rol " + usuario.getRol());

                // Cargar el FXML de gestión de productos
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/usuariosview.fxml"));
                loader.setControllerFactory(context::getBean); // Integrar Spring con JavaFX

                Parent root = loader.load();
                Scene escena = new Scene(root);

                // Obtener el Stage actual desde el botón
                Stage stage = (Stage) gestionUsuariosBoton.getScene().getWindow();

                // Reemplazar la escena actual
                stage.setScene(escena);
                stage.setTitle("Gestión de Usuarios");
            }else {
                mostrarMensaje("Error", "No tiene permisos para la acción");

            }
        }else {
            mostrarMensaje("Información", "Debe iniciar sesión");
        }

    }

    /**
     * Método asociado al botón login para abrir la ventana de login.
     */
    public void abrirVentanaLogin() throws IOException {
        // Cargar el FXML de login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/loginview.fxml"));
        loader.setControllerFactory(context::getBean); // Integrar Spring con JavaFX

        Parent root = loader.load();
        Scene escena = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Login Usuario");
        stage.setScene(escena);
        stage.initModality(Modality.APPLICATION_MODAL); // Bloquea hasta cerrar
        stage.showAndWait();

        // Cuando la ventana de login se cierra, se actualiza el textField con la sesión
        mostrarSesion();
    }

    /**
     * Método para mostrar la sesión en un TextField
     */
    public void mostrarSesion(){

        Usuario usuario = sesionServicio.getUsuarioLogeado();

        // Hacer que el textField no sea editable
        sesionUsuarioTexto.setEditable(false);

        if (usuario != null){
            sesionUsuarioTexto.setText("Sesión: " + usuario.getUsername());
            mostrarLogout();
        }else {
            sesionUsuarioTexto.setText("No hay sesión iniciada");
        }

    }

    /**
     * Método para ocultar el boton de login (ya esta la sesión iniciada) y mostrar el botón de logout
     */
    public void mostrarLogout(){
        loginBoton.setVisible(false);
        logoutBoton.setVisible(true);
    }

    /**
     * Método llamado desde el botón de logout de la vista para cerrar sesión
     */
    public void logout(){
        // Llama al método logout del servicio de la sesión que pone el usuarioLogeado a null
        sesionServicio.logout();

        // Actualiza el texto de la sesión.
        mostrarSesion();

        // Vuelve a mostrar el boton de login y a ocultar el de logout
        loginBoton.setVisible(true);
        logoutBoton.setVisible(false);
    }


}
