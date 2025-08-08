package com.corporativoX.cursoSpringBoot.service;

import com.corporativoX.cursoSpringBoot.model.Product;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

// @Primary
@Service
@ConditionalOnProperty(name = "service.products", havingValue = "json")
public class ProductsServiceJSONImpl implements ProductService{

    @Override
    public List<Product> getProducts() {
        List<Product> products;

        try{
            products = new ObjectMapper().readValue(this.getClass().getResourceAsStream("/products.json"), new TypeReference<List<Product>>(){});

            return products;

        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
