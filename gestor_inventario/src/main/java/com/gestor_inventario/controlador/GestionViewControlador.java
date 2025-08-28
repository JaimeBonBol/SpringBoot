package com.gestor_inventario.controlador;

import com.gestor_inventario.modelo.Producto;
import com.gestor_inventario.servicio.ProductoServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class GestionViewControlador implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(ProductosViewControlador.class);


    @Autowired
    private ProductoServicio productoServicio;

    /**
     * Mapeo componentes vista
     */

    @FXML
    private Button volverBoton;

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

    /**
     * ObservableList para que cualquier cambio sobre esta lista se refleje de manera automática
     */
    protected final ObservableList<Producto> productosLista = FXCollections.observableArrayList();

    private final ApplicationContext context;

    // Inyección por constructor
    public GestionViewControlador(ApplicationContext context) {
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

}
