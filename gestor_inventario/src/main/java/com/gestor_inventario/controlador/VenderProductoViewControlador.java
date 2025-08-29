package com.gestor_inventario.controlador;

import com.gestor_inventario.servicio.ProductoServicio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VenderProductoViewControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private ProductosViewControlador productosViewControlador;

    @Autowired
    private GestionViewControlador gestionViewControlador;


    /**
     * Mapeo de componentes de la vista
     */

    @FXML
    private TextField idVentaProductoText;

    @FXML
    private TextField cantidadVentaProductoText;

    /**
     * Método para vender producto, se recupera el id y la contidad de los textFields y dependiendo si se ha realizado
     * la venta o no se muestra un mensaje. (El método del servicio que realiza la venta devuelve true o false si se realiza o no)
     */
    public void venderProducto(){
        Integer id = Integer.valueOf(idVentaProductoText.getText());
        Integer cantidad = Integer.valueOf(cantidadVentaProductoText.getText());

        boolean exito = productoServicio.vender(id, cantidad);

        if (exito){
            mostrarMensaje("Información", "Venta de " + cantidad + " unidades del producto con id " + id);

            // Refrescar las tablas de las otras vistas.
            if (productosViewControlador != null){
                productosViewControlador.listarProductos();
            }
            if (gestionViewControlador != null){
                gestionViewControlador.listarProductos();
            }

        }else {
            mostrarMensaje("Error", "No hay suficiente stock o el producto no ha sido encontrado");
        }

        cerrarVentana();
    }

    public void cerrarVentana(){
        // Se recupera la ventana en la que estoy a traves de un componente de ella.
        Stage stage = (Stage) idVentaProductoText.getScene().getWindow();

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
