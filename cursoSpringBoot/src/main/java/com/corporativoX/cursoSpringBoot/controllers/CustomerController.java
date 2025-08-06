package com.corporativoX.cursoSpringBoot.controllers;

import com.corporativoX.cursoSpringBoot.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Customer>> getCustomers(){

        return ResponseEntity.ok(customers);
    }

    // @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    @GetMapping("/{username}")
    public ResponseEntity<?> getCustomerByUsername(@PathVariable String username){
        for (Customer customer : customers){
            if (customer.getUserName().equalsIgnoreCase(username)){
                return ResponseEntity.ok(customer);
            }
        }

        // Código de respuesta 404 en caso de que no encuentre el username
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado con username: " + username);
    }

    // @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<?> postCustomer(@RequestBody Customer newCustomer){
        customers.add(newCustomer);

        return ResponseEntity.status(HttpStatus.CREATED).body("El cliente " + newCustomer.getUserName() + " creado con éxito");
        // return newCustomer;
    }

    // @RequestMapping(method = RequestMethod.PUT)
    @PutMapping
    public ResponseEntity<?> putCustomer(@RequestBody Customer customer){
        for (Customer c : customers){
            if (c.getId() == customer.getId()){
                c.setName(customer.getName());
                c.setUserName(customer.getUserName());
                c.setPassword(customer.getPassword());

                return ResponseEntity.ok("Cliente modificado correctamente: " + customer.getUserName());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado: " + customer.getId());
    }

    // @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id){
        for (Customer c : customers){
            if (c.getId() == id){
                customers.remove(c);

                return ResponseEntity.ok("Cliente eliminado con éxito: " + c.getId());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado: " + id);
    }

    // @RequestMapping(method = RequestMethod.PATCH)
    @PatchMapping
    public ResponseEntity<?> patchCustomer(@RequestBody Customer customer){
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

                return ResponseEntity.ok("Cliente modificado con éxito: " + customer.getId());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado: " + customer.getId());
    }

}
