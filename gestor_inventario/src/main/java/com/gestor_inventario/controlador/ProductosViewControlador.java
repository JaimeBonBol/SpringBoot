package com.gestor_inventario.controlador;

import com.gestor_inventario.modelo.Producto;
import com.gestor_inventario.servicio.ProductoServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    /**
     * ObservableList para que cualquier cambio sobre esta lista se refleje de manera automática
     */
    private final ObservableList<Producto> productosLista = FXCollections.observableArrayList();


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
}
