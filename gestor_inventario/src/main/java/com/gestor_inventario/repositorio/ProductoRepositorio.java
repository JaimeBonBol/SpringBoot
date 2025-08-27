package com.gestor_inventario.repositorio;

import com.gestor_inventario.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {

    List<Producto> findByStockLessThan(Integer num);

}
