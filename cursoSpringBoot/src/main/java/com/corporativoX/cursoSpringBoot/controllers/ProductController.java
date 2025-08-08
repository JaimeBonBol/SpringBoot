package com.corporativoX.cursoSpringBoot.controllers;

import com.corporativoX.cursoSpringBoot.model.Product;
import com.corporativoX.cursoSpringBoot.service.ProductService;
import com.corporativoX.cursoSpringBoot.service.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductController {

    // Inyección de Dependencia
    @Autowired
    /*@Qualifier("listResourceService")*/
    private ProductService productsService;

    @GetMapping
    public ResponseEntity<?> getproducts(){
        List<Product> products = productsService.getProducts();

        return ResponseEntity.ok(products);
    }

}
