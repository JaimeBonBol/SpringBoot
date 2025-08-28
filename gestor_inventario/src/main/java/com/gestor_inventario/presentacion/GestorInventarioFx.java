package com.gestor_inventario.presentacion;

import com.gestor_inventario.GestorInventarioApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class GestorInventarioFx extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        // Inicia Spring Boot
        this.applicationContext = new SpringApplicationBuilder(GestorInventarioApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Carga la primera vidta (productosview.fxml)
        FXMLLoader loader = new FXMLLoader(GestorInventarioApplication.class.getResource("/templates/productosview.fxml"));

        // Pemite que los conntroladores usen @Autowired
        loader.setControllerFactory(applicationContext::getBean);   //Integrar las tecnologías Spring y JavaFX

        Scene escena = new Scene(loader.load());

        stage.setTitle("Inventario Productos");
        stage.setScene(escena);
        stage.show();
    }

    @Override
    public void stop(){
        // Cierra el contexto de Spring cuando la app se cierra
        applicationContext.close();
        Platform.exit();
    }
}
