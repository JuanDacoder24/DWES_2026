package com.example.centroformacion.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.centroformacion.entity.Aula;
import com.example.centroformacion.services.CentroServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api")
public class AulaController {

    @Autowired
    private CentroServices centroServices;

    @GetMapping("/aulas")
    public ResponseEntity<List<Aula>> getListaAulas() {
        return ResponseEntity.ok(centroServices.getAllAulas());
    }

    //Poder mostrar los datos de un aula concreta mediante su ID.
    @GetMapping("/aulas/{id}")
    public ResponseEntity<Aula> getAulaById(@PathVariable String id) {
        return centroServices.getAulaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    

}
