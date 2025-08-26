package com.to_do.To_Do.controller;


import com.to_do.To_Do.model.Task;
import com.to_do.To_Do.service.TaskService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@Data
@ViewScoped
public class IndexController {

    @Autowired
    TaskService taskService;

    private List<Task> pendingTasks;
    private List<Task> completedTasks;
    private Task newTask;
    private Task taskSelected;
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


    @PostConstruct
    public void init(){
        this.newTask = new Task();
        logger.info("TaskSelected" + (String.valueOf(taskSelected)));
        loadTasks();
    }

    public void loadTasks(){
        this.pendingTasks = taskService.getPendingTasks();
        this.completedTasks = taskService.getCompletedTasks();
    }

    /**
     * Por defecto cuando se añade no va a estar completada, los demás atributos se capturan desde la vista
     */
    public void addTask(){
        this.newTask.setCompleted(false);

        this.taskService.saveTask(newTask);

        //Reset del objeto para seguiir trabajando con el.
        this.newTask = new Task();

        logger.info("TaskSelected" + (String.valueOf(taskSelected)));

        loadTasks();
    }

    /**
     * Método para completar una tarea
     *
     * action="#{indexController.completeTask(task)}"
     * Aquí se está pasando el objeto task directamente desde la iteración de la tabla (var="task").
     * JSF/PrimeFaces sabe cuál fila corresponde porque var="task" en <p:dataTable> crea un objeto task temporal por cada fila.
     * Cuando haces clic en el botón, ese objeto se envía al método completeTask(Task task) de tu controlador.
     *
     *
     * ¡¡IMPORTANTE. Si se le pasa por fila, Si tu bean no está correctamente ViewScoped, JSF crea un nuevo bean
     * en el request AJAX, y el objeto task de la iteración ya no es válido, por eso el método no hace nada. !!
     *
     *
     * POR TANTO HE CREADO UN ATRIBUTO DE TIPO TASK taskSelected PARA NO PASARLE EL TASK DIRECTAMENTE, SELECCIONARLO PRIMERO
     * Y TRABAJAR CON ÉL, SABE CUÁL ES SELECCIONADO A TRAVÉS DE <f:setPropertyActionListener>
     *
     */
    public void completeTask(){
        taskSelected.setCompleted(true);

        // Se guarda en la bd de nuevo con el atributo completada en true.
        taskService.saveTask(taskSelected);

        logger.info("TaskSelected" + (String.valueOf(taskSelected)));

        loadTasks();
    }

    /**
     * Método para eliminar tarea
     * Igual que el anterior, se utiliza el objeto creado de tipo Task "taskSelected"
     */
    public void deleteTask(){
        taskService.deleteTask(taskSelected.getId());

        logger.info("TaskSelected" + (String.valueOf(taskSelected)));

        loadTasks();
    }
}
