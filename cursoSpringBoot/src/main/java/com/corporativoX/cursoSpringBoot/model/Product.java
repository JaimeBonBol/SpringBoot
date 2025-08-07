package com.corporativoX.cursoSpringBoot.model;

public class Product {

    // Atributos. Cunado utilizamos un framework, usar tipos de datos referenciales en lugar de primitivos es considerado buenas prácticas
    private Integer id;
    private String nombre;
    private Double precio;
    private Integer stock;

    public Product(Integer id, String nombre, Double precio, Integer stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y Setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }


}
