package com.corporativoX.cursoSpringBoot.controllers;

import com.corporativoX.cursoSpringBoot.model.Customer;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/clientes/{username}")
    public Customer getCustomerByUsername(@PathVariable String username){
        for (Customer customer : customers){
            if (customer.getUserName().equalsIgnoreCase(username)){
                return customer;
            }
        }
        return null;
    }

    @PostMapping("/clientes")
    public Customer postCustomer(@RequestBody Customer newCustomer){
        customers.add(newCustomer);
        return newCustomer;
    }

    @PutMapping("/clientes")
    public Customer putCustomer(@RequestBody Customer customer){
        for (Customer c : customers){
            if (c.getId() == customer.getId()){
                c.setName(customer.getName());
                c.setUserName(customer.getUserName());
                c.setPassword(customer.getPassword());

                return c;
            }
        }
        return null;
    }

}
