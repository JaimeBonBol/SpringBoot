package com.gestor_inventario.controlador;

import com.gestor_inventario.GestorInventarioApplication;
import com.gestor_inventario.modelo.Producto;
import com.gestor_inventario.servicio.ProductoServicio;
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
     * Método asosciado al boton para cambiar de ventana, primero crea una ventana emergente que solicita autenticación
     * y si esta es correcta llama al método de abrirGestion que es el que realmente cambia la vista.
     */
    @FXML
    public void gestionProductos() {
        // Crear el diálogo de contraseña
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Autenticación requerida");
        dialog.setHeaderText("Introduce la contraseña para acceder a la gestión");
        dialog.setContentText("Contraseña:");

        // Mostrar el diálogo y esperar respuesta
        dialog.showAndWait().ifPresent(password -> {
            // Comprobar la contraseña
            if (password.equals(PASSWORD)) {
                // Contraseña correcta, cambiar la ventana
                try {
                    abrirGestion();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // Contraseña incorrecta, mostrar alerta
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Contraseña incorrecta");
                alert.showAndWait();
            }
        });
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

    }
}
