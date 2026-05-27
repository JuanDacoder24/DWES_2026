package com.example.centroformacion.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.centroformacion.entity.Profesor;
import com.example.centroformacion.services.CentroServices;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api")
public class ProfesorController {

    @Autowired
    private CentroServices centroServices;

    @GetMapping("/profesores")
    public ResponseEntity<List<Profesor>> getListaProfesores(){
        return ResponseEntity.ok(centroServices.getAllProfesores());
    }

    @GetMapping("/profesores/{id}")
    public ResponseEntity<Profesor> getProfesorById(@PathVariable String id) {
        return centroServices.getProfesorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/profesores")
    public ResponseEntity<Profesor> createProfesor(@RequestBody Profesor profesor) {
        Profesor nuevoProfesor = centroServices.addProfesor(profesor);
        return ResponseEntity.ok(nuevoProfesor);
    }
    

    //EN EL POSTMAN EN LA PARTE DEL BODY SOLO DEBO DE ESCRIBIR EL NOMBRE DEL ENUM, EJEMPLO INFORMATICA A SECAS
    @PutMapping("/profesores/{id}")
    public ResponseEntity<Profesor> modificarEspecialidadProfesor(@PathVariable String id, @RequestBody String nuevaEspecialidad) {
        return centroServices.modificarEspecialidadProfesor(id, nuevaEspecialidad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/profesores/{id}")
    public ResponseEntity<Void> eliminarProfesor(@PathVariable String id) {
        if (centroServices.eliminarProfesor(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
