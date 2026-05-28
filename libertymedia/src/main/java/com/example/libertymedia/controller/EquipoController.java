package com.example.libertymedia.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.libertymedia.entity.Equipo;
import com.example.libertymedia.services.LiberatyMediaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api")
public class EquipoController {

    @Autowired
    private LiberatyMediaService liberatyMediaService;

    @GetMapping("/equipos")
    public ResponseEntity<List<Equipo>> getAllEquipos() {
        return ResponseEntity.ok(liberatyMediaService.getAllEquipos());
    }

    //Poder mostrar los datos de un aula concreta mediante su ID.
    @GetMapping("/equipos/{id}")
    public ResponseEntity<Equipo> getEquipoById(@PathVariable int id) {
        return liberatyMediaService.getEquipoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}


