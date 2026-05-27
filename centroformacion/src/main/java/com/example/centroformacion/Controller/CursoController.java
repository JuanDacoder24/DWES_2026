package com.example.centroformacion.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.centroformacion.entity.Curso;
import com.example.centroformacion.services.CentroServices;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api")
public class CursoController {

    @Autowired
    private CentroServices centroServices;

    @GetMapping("/cursos")
    public ResponseEntity<List<Curso>> getListaCursos() {
        return ResponseEntity.ok(centroServices.getAllCursos());
    }

    @GetMapping("/cursos/{id}")
    public ResponseEntity<Curso> getCursoById(@PathVariable String id) {
        return centroServices.getCursoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cursos")
    public ResponseEntity<Curso> createCurso(@RequestBody Curso curso) {
        Curso nuevoCurso = centroServices.addCurso(curso);
        return ResponseEntity.ok(nuevoCurso);
    }
    
    //Poder modificar el profesor asignado al curso según el ID del curso.
    @PutMapping("/cursos/{id}")
    public ResponseEntity<Curso> modificarProfesorAsignado(
        @PathVariable String id, // Este es el ID del curso
        @RequestBody Map<String, String> body) {
    
    String nuevoProfesorId = body.get("profesorId");
    
    if (nuevoProfesorId == null) {
        return ResponseEntity.badRequest().build(); // Protección por si no mandan el campo
    }
    return centroServices.modificarProfesor(id, nuevoProfesorId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable String id) {
        if (centroServices.eliminarCurso(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
