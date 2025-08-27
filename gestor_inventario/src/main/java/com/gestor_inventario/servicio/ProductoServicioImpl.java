package com.gestor_inventario.servicio;

import com.gestor_inventario.modelo.Producto;
import com.gestor_inventario.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServicioImpl implements ProductoServicio{

    private static final Integer MIN_STOCK = 5;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Override
    public List<Producto> listarProductos() {
        return productoRepositorio.findAll();
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        return productoRepositorio.save(producto);
    }

    @Override
    public void eliminarProducto(Producto producto) {
        productoRepositorio.delete(producto);
    }

    @Override
    public List<Producto> listarProductosConStockBajo() {
        return productoRepositorio.findByStockLessThan(MIN_STOCK);
    }

    @Override
    public boolean vender(Integer idProducto, Integer cantidad) {
        Producto producto = productoRepositorio.findById(idProducto).orElse(null);

        if(producto != null){
            if (producto.getStock() >= cantidad) {
                producto.setStock(producto.getStock() - cantidad);
                productoRepositorio.save(producto);
                return true;
            }
            return false;
        }else {
            return false;
        }
    }
}
