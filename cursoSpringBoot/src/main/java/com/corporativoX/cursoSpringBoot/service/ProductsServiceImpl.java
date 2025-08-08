package com.corporativoX.cursoSpringBoot.service;

import com.corporativoX.cursoSpringBoot.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("listResourceService")
public class ProductsServiceImpl implements ProductService {

    List<Product> products = new ArrayList<>(Arrays.asList(
            new Product(1, "Ordenador", 1050.50, 4),
            new Product(2, "Estuche", 4.50, 25),
            new Product(3, "Goma", 0.80, 40),
            new Product(4, "Móvil", 650.99, 5)
    ));

    @Override
    public List<Product> getProducts() {
        return products;
    }

}
