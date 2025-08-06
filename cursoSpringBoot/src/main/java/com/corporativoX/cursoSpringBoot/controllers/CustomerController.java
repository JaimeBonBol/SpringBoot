package com.corporativoX.cursoSpringBoot.controllers;

import com.corporativoX.cursoSpringBoot.model.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/clientes") //Unificación de rutas a nivel de clase.
public class CustomerController {

    // Lista que simula una base de datos
    private List<Customer> customers = new ArrayList<>(Arrays.asList(
            new Customer(1, "Pepe Pérez", "pepepe", "contrasena123"),
            new Customer(2, "Lucía Martínez", "luciama", "contrasena456"),
            new Customer(3, "Marta Nuñez", "martanu", "contrasena789"),
            new Customer(4, "Lucas Rodriguez", "lucasro", "contasena000")
    ));

    // @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public List<Customer> getCustomers(){
        return customers;
    }

    // @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    @GetMapping("/{username}")
    public Customer getCustomerByUsername(@PathVariable String username){
        for (Customer customer : customers){
            if (customer.getUserName().equalsIgnoreCase(username)){
                return customer;
            }
        }
        return null;
    }

    // @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public Customer postCustomer(@RequestBody Customer newCustomer){
        customers.add(newCustomer);
        return newCustomer;
    }

    // @RequestMapping(method = RequestMethod.PUT)
    @PutMapping
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

    // @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public Customer deleteCustomer(@PathVariable int id){
        for (Customer c : customers){
            if (c.getId() == id){
                customers.remove(c);

                return c;
            }
        }
        return null;
    }

    // @RequestMapping(method = RequestMethod.PATCH)
    @PatchMapping
    public Customer patchCustomer(@RequestBody Customer customer){
        for (Customer c : customers){
            if (c.getId() == customer.getId()){
                if (customer.getName() != null){
                    c.setName(customer.getName());
                }
                if (customer.getUserName() != null){
                    c.setUserName(customer.getUserName());
                }
                if (customer.getPassword() != null){
                    c.setPassword(customer.getPassword());
                }

                return c;
            }
        }
        return null;
    }

}
