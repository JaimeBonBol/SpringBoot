package com.gestor_inventario.servicio;

import com.gestor_inventario.modelo.Producto;

import java.util.List;

public interface ProductoServicio {

    List<Producto> listarProductos();

    // Se utiliza para guardar nueva y actualizar lo hace automaticamente Hibernate, dependiendo si tiene id o no.
    public Producto guardarProducto(Producto producto);

    public void eliminarProducto(Producto producto);

    public List<Producto> listarProductosConStockBajo();

    public boolean vender(Integer idProducto, Integer cantidad);

}
