package com.gestor_inventario.controlador;

import com.gestor_inventario.modelo.Producto;
import com.gestor_inventario.modelo.Usuario;
import com.gestor_inventario.servicio.ProductoServicio;
import com.gestor_inventario.sesion.SesionServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class GestionViewControlador implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(ProductosViewControlador.class);


    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private SesionServicio sesionServicio;

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

    @FXML
    private TextField nombreProductoTexto;

    @FXML
    private TextField stockProductoTexto;

    @FXML
    private TextField precioProductoTexto;

    @FXML
    private TextArea stockBajoTexto;

    @FXML
    private TextField sesionUsuarioTexto;


    /**
     * ObservableList para que cualquier cambio sobre esta lista se refleje de manera automática
     */
    protected final ObservableList<Producto> productosLista = FXCollections.observableArrayList();

    private final ApplicationContext context;

    private Integer idProductoInterno;

    // Inyección por constructor
    public GestionViewControlador(ApplicationContext context) {
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
     * Tienen que ser exactamente los mismos nombres de los atributos de la clase modelo Producto
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

        // Muestra en un textArea los productos con stock bajo
        mostrarProductosStockBajo();

    }

    /**
     * Método que muestra en un TextArea los productos que tienen el stock bajo.
     */
    public void mostrarProductosStockBajo(){
        // Recuperar los productos que tienen el stock bajo
        List<Producto> productosStockBajo = productoServicio.listarProductosConStockBajo();

        // Para que no se pueda editar y solo muestre información.
        stockBajoTexto.setEditable(false);

        // Almaceno la información en el sb.
        StringBuilder sb = new StringBuilder();

        for (Producto producto : productosStockBajo){
            sb.append(producto.getNombre() + " con id " + producto.getIdProducto() + " tiene stock bajo: " + producto.getStock() + " unidades");
            sb.append("\n");
        }

        // Agregar el texto del sb al TextArea
        stockBajoTexto.setText(sb.toString());
        stockBajoTexto.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
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
     * dependiendo del valor de esta, se agregará nuevo prodcuto o se modificará.
     */
    public void limpiarFormulario(){
        idProductoInterno = null;   // Asegurar que se resetea el id interno
        nombreProductoTexto.clear();
        stockProductoTexto.clear();
        precioProductoTexto.clear();
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
     * Método para cargar un producto en los textFields desde la tabla al clickar en una fila. Rellena los textField.
     */
    public void cargarProductoTabla(){
        // Recoger el objeto seleccionado
        Producto producto = productosTabla.getSelectionModel().getSelectedItem();

        // Por si da click en otra parte de la tabla se comprueba que no sea nul.
        // Se carga en nuestra variable id interna el valor de la id y en los TextFields los atributos del producto seleccionado.
        if (producto != null){
            idProductoInterno = producto.getIdProducto();
            nombreProductoTexto.setText(producto.getNombre());
            stockProductoTexto.setText(String.valueOf(producto.getStock()));
            precioProductoTexto.setText(String.valueOf(producto.getPrecio()));
        }
    }

    /**
     * Método para asignar a un producto los datos de los textFIelds.
     * @param producto
     */
    public void recolectarDatosProducto(Producto producto){
        // Si el producto ya existe, entonces la variable idProductoInterno tiene un valor, si no existe no se asigna ningún
        // valor a la id interna y por lo tanto se creará nuevo producto.
        if (idProductoInterno != null){
            producto.setIdProducto(idProductoInterno);
        }

        producto.setNombre(nombreProductoTexto.getText());
        producto.setStock(Integer.valueOf(stockProductoTexto.getText()));
        producto.setPrecio(Float.valueOf(precioProductoTexto.getText()));
    }

    /**
     * Método para agregar un nuevo producto, se comprueba que existe un nombre del producto.
     * Se crea un objeto del tipo producto en el que a través del metodo recolectarDatos se le asignan los valores de los
     * textFields a los atribitos del nuevbo producto. Después se guarda en la bd.
     */
    public void agregarProducto(){
        if (nombreProductoTexto.getText().isEmpty()){
            mostrarMensaje("Error Validación", "Debe proporcionar un nombre");
            nombreProductoTexto.requestFocus();
            return;
        }

        Producto producto = new Producto();
        recolectarDatosProducto(producto);

        // Asegurar que no haya problema con el id ya que para agregar el id tiene que ser null
        producto.setIdProducto(null);

        productoServicio.guardarProducto(producto);
        mostrarMensaje("Información", "Producto agregado al inventario");

        limpiarFormulario();

        listarProductos();
    }

    public void modificarProducto(){
        // Comprobar que realmente se va a modificar, para ello se comprueba si idInterna tiene valor (es decir si se
        // ha clickado sobre un producto y se ha cargado la id en la variable idInterna)
        if (idProductoInterno == null){
            mostrarMensaje("Información", "Debe selecionar un producto");
            return;
        }
        // Se comprueba si se ha proporcionado nombre del producto a modificar
        if (nombreProductoTexto.getText().isEmpty()){
            mostrarMensaje("Error Validación", "Debe proporcionar un producto");
            nombreProductoTexto.requestFocus();
            return;
        }

        Producto producto = new Producto();
        recolectarDatosProducto(producto);

        // Hibernate guarda un nuveo producto o modifica automáticamente dependiendo si el id es null o no.
        productoServicio.guardarProducto(producto);
        mostrarMensaje("Información", "Producto con id " + idProductoInterno + " modificado");

        limpiarFormulario();
        listarProductos();
    }

    /**
     * Método para eliminar producto seleccionado.
     */
    public void eliminarProducto(){
        // Obtener el producto seleccionado
        Producto producto = productosTabla.getSelectionModel().getSelectedItem();

        if (producto != null && !nombreProductoTexto.getText().isEmpty()){
            productoServicio.eliminarProducto(producto);
            mostrarMensaje("Información", "Producto con id " + producto.getIdProducto() + " eliminado");

            limpiarFormulario();
            listarProductos();
        }else {
            mostrarMensaje("Error", "No se ha seleccionado ningún producto");
        }
    }

    /**
     * Método asociado al botón de vender para abrir la ventana para vender producto.
     */
    public void abrirVentanaVender() throws IOException {
        // Cargar el FXML de vender productos
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/venderproductoview.fxml"));
        loader.setControllerFactory(context::getBean); // Integrar Spring con JavaFX

        Parent root = loader.load();
        Scene escena = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Venta Producto");
        stage.setScene(escena);
        stage.initModality(Modality.APPLICATION_MODAL); // Bloquea hasta cerrar
        stage.showAndWait();


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
        }else {
            sesionUsuarioTexto.setText("No hay sesión iniciada");
        }

    }

}
