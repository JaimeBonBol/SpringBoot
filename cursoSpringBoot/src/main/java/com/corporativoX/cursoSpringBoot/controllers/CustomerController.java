package com.corporativoX.cursoSpringBoot.controllers;

import com.corporativoX.cursoSpringBoot.model.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class CustomerController {

    private List<Customer> customers = new ArrayList<>(Arrays.asList(
            new Customer(1, "Pepe Pérez", "pepepe", "contrasena123"),
            new Customer(2, "Lucía Martínez", "luciama", "contrasena456"),
            new Customer(3, "Marta Nuñez", "martanu", "contrasena789"),
            new Customer(4, "Lucas Rodriguez", "lucasro", "contasena000")
    ));

    @GetMapping("/clientes")
    public List<Customer> getCustomers(){
        return customers;
    }

}
