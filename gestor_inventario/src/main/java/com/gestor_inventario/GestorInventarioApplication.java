package com.gestor_inventario;

import com.gestor_inventario.presentacion.GestorInventarioFx;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestorInventarioApplication {

	public static void main(String[] args) {
		// SpringApplication.run(GestorInventarioApplication.class, args);
        Application.launch(GestorInventarioFx.class, args);   // Así al ejecutar la aplicación de Spring, llama la aplicación GestorInventarioFx.

	}

}
