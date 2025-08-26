package com.to_do.To_Do.gui;

import com.to_do.To_Do.model.Task;
import com.to_do.To_Do.service.TaskService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

// @Component
public class ToDoListForma extends JFrame {
    private JPanel panelPrincipal;
    private JTable taskTable;
    private JScrollPane scrollPane1;
    private JTextField titleText;
    private JTextField descriptionText;
    private JCheckBox completedCheckBox;
    private JTextField dueDateText;
    private JButton saveTaskButton;
    private JButton deleteTaskButton;
    private JButton cleanButton;

    private TaskService taskService;
    private DefaultTableModel taskTableModel;
    private Integer idTaskSelected;

    public ToDoListForma(TaskService taskService){
        this.taskService = taskService;
        inicializarForma();

        saveTaskButton.addActionListener(actionEvent -> {
            saveTask();
        });
        taskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadTaskSelected();
            }
        });
        deleteTaskButton.addActionListener(actionEvent -> {
            deleteTask();
        });
        cleanButton.addActionListener(actionEvent -> {
            clearForm();
        });
    }

    /**
     * Inicializar forma para que se muestre al iniciar la aplicación.
     */
    private void inicializarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 900);
        setLocationRelativeTo(null); // Para centrar la ventana.
    }

    /**
     * Crear un objeto de tipo DefaultTableModel para asignar el num de col y filas. Crear una lista con los cabeceros
     * de la table y asignarlos al modelo. (Este modelo es donde realmente están los datos)
     * Luego este modelo se pasa por paŕametros a la tabla de la forma. (Es lo que se ve)
     */
    private void createUIComponents() {
        // TODO: place custom component creation code here
        // this.taskTableModel = new DefaultTableModel(0,5);

        // Evitar la edición de los valores de las celdas de la tabla
        this.taskTableModel = new DefaultTableModel(0,5){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] headersTable = {"Id", "Title", "Descripton", "Completed", "Due Date"};
        this.taskTableModel.setColumnIdentifiers(headersTable);

        this.taskTable = new JTable(taskTableModel);

        // Resringir la selección de la tabla a un solo registro.
        this.taskTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Listar las tareas en la tabla.
        listTasks();
    }

    /**
     * Metodo para listar las tareas
     */
    private void listTasks(){
        // Settear en 0 las filas de la tabla. Se hace en la tabla modelo por que es donde realmente residen los datos.
        this.taskTableModel.setRowCount(0);

        // Guardar en una lista las tareas, y luego iterar sobre ella para añadirlas a un array de tipo Object[], que será
        // cada renglón de la tabla. Después este renglón se añade a la tabla.
        List<Task> tasks = this.taskService.getTasks();
        for (Task task : tasks){
            Object[] rowTask = {
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getCompleted(),
                    task.getDueDate()
            };

            this.taskTableModel.addRow(rowTask);
        }
    }

    /**
     * Método para guardar o actualizar tarea, depende si encuentra un id actualiza, y si no tiene id crea una nueva tarea.
     * Botón Guardar que:
     * Si no hay idTaskSelected, crea una nueva tarea.
     * Si hay idTaskSelected, actualiza la tarea.
     */
    private void saveTask(){
        // Comprobar que el textField del texto no esté vacío para guardar una nueva tarea, por tanto es un campño obligatorio.
        if (titleText.getText().equals("")){
            showMessage("Proporciona un título");
            titleText.requestFocusInWindow();
            return;
        }

        // Comprobar que el textField de la fecha de entrega no esté vacío para guardar una tarea, por tanto es un campo
        // obligatorio
        if (dueDateText.getText().equals("")){
            showMessage("Proporciona fecha de entrega");
            dueDateText.requestFocusInWindow();
            return;
        }

        //Recuperar datos de los cmapos de texto.
        String title = this.titleText.getText();
        String description = this.descriptionText.getText();
        Boolean completed = this.completedCheckBox.isSelected();
        LocalDate dueDate = LocalDate.parse(this.dueDateText.getText());

        Task newTask = new Task();
        newTask.setId(this.idTaskSelected); //Dependiendo de si encuentra o no, crea nueva taera o actualiza.
        newTask.setTitle(title);
        newTask.setDescription(description);
        newTask.setCompleted(completed);
        newTask.setDueDate(dueDate);

        // Guardar o actualizar tarea y mostrar mensaje
        taskService.saveTask(newTask);
        if (idTaskSelected ==  null){
            showMessage("Tarea agregada con éxito");
        }else {
            showMessage("Se actualizó la tarea con id " + idTaskSelected);
        }

        clearForm();
        listTasks();
    }

    /**
     * Método para cargar tarea seleccionada desde la tabla.
     */
    private void loadTaskSelected(){
        int row = taskTable.getSelectedRow();

        // Si la fila no es -1, es decir, hay alguna fila seleccionada, cargamos sus atributos en los cmapos de texto,
        // y la id en el atributo de nuestra clase.
        if (row != -1){
           this.idTaskSelected = (Integer) taskTableModel.getValueAt(row, 0);
           this.titleText.setText((String) taskTableModel.getValueAt(row, 1));
           this.descriptionText.setText((String) taskTableModel.getValueAt(row, 2));
           this.completedCheckBox.setSelected((Boolean) taskTableModel.getValueAt(row,3));
           this.dueDateText.setText(taskTableModel.getValueAt(row, 4).toString());
        }
    }

    /**
     * Método para eliminar tarea
     */
    private void deleteTask(){
        // Obtener el renglón de la tabla seleccionado.
        int row = taskTable.getSelectedRow();

        if (row != -1){
            // Recuperar el id
            this.idTaskSelected = (Integer) taskTableModel.getValueAt(row, 0);

            // Crear un objeto de tarea y asignarle el id recuperado.
            Task taskDel = new Task();
            taskDel.setId(idTaskSelected);

            // ELiminar tarea y mostrar mensaje
            taskService.deleteTask(taskDel.getId());
            showMessage("Tarea con id " + taskDel.getId() + " eliminada");

            clearForm();
            listTasks();
        }else {
            showMessage("Debe seleccionar una tarea");
        }
    }

    /**
     * Método para limpiar el fomrulario
     */
    private void clearForm(){
        titleText.setText("");
        descriptionText.setText("");
        completedCheckBox.setSelected(false);
        dueDateText.setText("");
        idTaskSelected = null;
        taskTable.clearSelection(); // Deseleccionar renglón.
    }

    private void showMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }
}
