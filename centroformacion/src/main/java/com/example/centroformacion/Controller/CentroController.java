package com.example.centroformacion.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.centroformacion.entity.Curso;
import com.example.centroformacion.entity.Especialidad;
import com.example.centroformacion.entity.Profesor;
import com.example.centroformacion.services.CentroServices;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api")
public class CentroController {

    @Autowired
    private CentroServices service;

    //profesores
    @GetMapping("/profesores")
    public List<Profesor>getListaProfesores() {
        return service.getAllProfesores();
    }

    //profesores por id
    @GetMapping("/profesores/{id}")
    public ResponseEntity<Profesor> getProfesorById(@PathVariable String id) {
        Profesor profesor = service.getProfesorById(id);
        if (profesor != null) {
            return ResponseEntity.ok(profesor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //agregar profesor
    @PostMapping("/profesores")
    public ResponseEntity<String> addProfesor(@RequestBody Profesor profesor){
        service.addProfesor(profesor);
        return ResponseEntity.ok("Profesor agregado correctamente");
    }

    //modificar profesor
    @PutMapping("/profesores/{id}")
    public ResponseEntity<String> actualizarProfesor(@PathVariable Especialidad especialidad,@PathVariable String id) {
        boolean actualizado = service.actualizarEspecialidad(especialidad, id);
        if (actualizado) {
            return ResponseEntity.ok("Profesor actualizado correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //eliminar profesor
    @DeleteMapping("/profesores/{id}")
    public ResponseEntity<String> eliminarProfesor(@PathVariable String id){
        boolean eliminado = service.eliminarProfesor(id);
        if (eliminado) {
            return ResponseEntity.ok("Profesor eliminado correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @GetMapping("/curso")
    public List<Curso>getListaEquipos() {
        return service.getAllCursos();
    }

    //curso por id 
    @GetMapping("/curso/{id}")
    public ResponseEntity<Curso> getCursoById(@PathVariable String id) {
        Curso curso = service.getCursoById(id);
        if (curso != null) {
            return ResponseEntity.ok(curso);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //eliminar curso por id
    @DeleteMapping("/curso/{id}")
    public ResponseEntity<String> eliminarCurso(@PathVariable String id){
        boolean eliminado = service.eliminarCurso(id);
        if (eliminado) {
            return ResponseEntity.ok("Curso eliminado correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //controlador conjunto
    //llamaria a todas las funciones de los demas controllers que demuestren todos los objetos
    // @GetMapping("/{id_curso}/{id_profesor}/{id_aula}")
    // public List<String> getAllConjunto() {
    //     return ;
    // }
    

    


    
    

}
