package com.example.centroformacion.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.centroformacion.entity.Curso;
import com.example.centroformacion.services.CentroServices;

@RestController
@RequestMapping("/api")
public class CursoConjuntoController {

    @Autowired
    private CentroServices centroServices;

    // Endpoint: GET /api/cursos/comprobacion/{idCurso}?parametro1=valor1&parametro2=valor2
    //EJEMPLO: http://localhost:8080/api/cursos/comprobacion/gsdgs?profesorId=FEFEF&aulaId=qwqw
    
    @GetMapping("/cursos/comprobacion/{idCurso}")
    public ResponseEntity<Curso> consultarCursoCompleto(
            @PathVariable String idCurso,
            @RequestParam String profesorId,
            @RequestParam String aulaId) {

        return centroServices.obtenerCursoConjunto(idCurso, profesorId, aulaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); 
                // Devuelve 404 si el curso no existe o si el profesor/aula no coinciden
    }

}
