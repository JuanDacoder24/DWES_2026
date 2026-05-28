package com.example.libertymedia.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.libertymedia.entity.CampeonatoPiloto;
import com.example.libertymedia.services.LiberatyMediaService;

@RestController
@RequestMapping("/api")
public class CampeonatoPilotoController {

    @Autowired
    private LiberatyMediaService liberatyMediaService;


    @GetMapping("/campeonatos")
    public ResponseEntity<List<CampeonatoPiloto>> getAllCampeonato() {
        return ResponseEntity.ok(liberatyMediaService.getAllCampeonato());
    }

    @GetMapping("/campeonatos/{id}")
    public ResponseEntity<CampeonatoPiloto> getCampeonatoById(@PathVariable String id) {
        return liberatyMediaService.getCampeonatoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/campeonatos")
    public ResponseEntity<CampeonatoPiloto> createCampeonato(@RequestBody CampeonatoPiloto campeonatoPiloto) {
        CampeonatoPiloto nuevoCampeonatoPiloto = liberatyMediaService.addCampeonato(campeonatoPiloto);
        return ResponseEntity.ok(nuevoCampeonatoPiloto);
    }

    @DeleteMapping("/campeonatos/{id}")
    public ResponseEntity<Void> eliminarCampeonato(@PathVariable String id) {
        if (liberatyMediaService.eliminarCampeonato(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/cursos/{id}")
    public ResponseEntity<CampeonatoPiloto> modificarEquipo(
        @PathVariable String id, 
        @RequestBody Map<String, String> body) {
    
    String nuevoEquipo = body.get("equipoId");
    
    if (nuevoEquipo == null) {
        return ResponseEntity.badRequest().build(); 
    }
    return liberatyMediaService.actualizarEquipo(id, 0)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}
}
