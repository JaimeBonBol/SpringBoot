package com.to_do.To_Do;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.to_do.To_Do.gui.ToDoListForma;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class ToDoSwing {
    public static void main(String[] args) {

        // Modo oscuro
        FlatDarculaLaf.setup();

        // Instancia la fábrica de Spring
        ConfigurableApplicationContext contextSpring =
                new SpringApplicationBuilder(ToDoSwing.class).headless(false).web(WebApplicationType.NONE).run(args);

        // Crear un objeto de Swing, una vez que se carga la fárbcia de Spring, invocamos el bean de ToDoListForma ya que con
        // la anotación @Component ya forma parte de la fábrica de Spring.
        SwingUtilities.invokeLater(() -> {
            ToDoListForma toDoListForma = contextSpring.getBean(ToDoListForma.class);
            toDoListForma.setVisible(true);
        });
    }
}
