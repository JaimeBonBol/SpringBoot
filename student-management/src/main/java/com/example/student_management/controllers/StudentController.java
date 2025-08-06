package com.example.student_management.controllers;

import com.example.student_management.model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    // Lista de estudiantes simulando una base de datos.
    private List<Student> students = new ArrayList<>(Arrays.asList(
            new Student(1, "Martin Jimenez", "martinj@prueba.p", 24, "1 DAM"),
            new Student(2, "Lucia Perez", "luciap@prueba.p", 26, "2 DAM"),
            new Student(3, "Luisa Martin", "luisam@prueba.p", 18, "1 BACH"),
            new Student(4, "Pepe Ramirez", "pepera@prueba.p", 30, " DAM")
    ));

    // Mostrar todos los estudiantes.
    @GetMapping
    public List<Student> getStudents() {
        return students;
    }

    // Mostrar un estudiante por su email.
    @GetMapping("/{email}")
    public Student getStudentByEmail(@PathVariable String email){
        for (Student student : students){
            if (student.getEmail().equalsIgnoreCase(email)){
                return student;
            }
        }
        return null;
    }

    // Añadir un nuevo estudiante.
    @PostMapping
    public Student postStudent(@RequestBody Student newStudent){
        students.add(newStudent);
        return newStudent;
    }

    // Modificar estudiante.
    @PutMapping
    public Student putStudent(@RequestBody Student student){
        for (Student s : students){
            if (s.getId() == student.getId()){
                s.setNombre(student.getNombre());
                s.setEmail(student.getEmail());
                s.setEdad(student.getEdad());
                s.setCurso(student.getCurso());

                return s;
            }
        }
        return null;
    }

    // Modificar atributos parcialmente de un estudiante.
    @PatchMapping
    public Student patchStudent(@RequestBody Student student){
        for (Student s : students){
            if (s.getId() == student.getId()){
                if (student.getNombre() != null){
                    s.setNombre(student.getNombre());
                }
                if (student.getEmail() != null){
                    s.setEmail(student.getEmail());
                }
                if (student.getEdad() != 0){
                    s.setEdad(student.getEdad());
                }
                if (student.getCurso() != null){
                    s.setCurso(student.getCurso());
                }

                return s;
            }
        }

        return null;
    }

    // Eliminar estudiante por su id.
    @DeleteMapping("/{id}")
    public Student deleteStudent(@PathVariable int id){
        for (Student s : students){
            if (s.getId() == id){
                students.remove(s);

                return s;
            }
        }

        return null;
    }
}
