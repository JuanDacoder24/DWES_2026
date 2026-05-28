package com.example.libertymedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.libertymedia.entity.CampeonatoPiloto;
import com.example.libertymedia.services.LiberatyMediaService;

@RestController
@RequestMapping("/api")
public class ConjuntoController {

    @Autowired
    private LiberatyMediaService liberatyMediaService;

    @GetMapping("/campeonato/comprobacion/{idCampeonato}")
    public ResponseEntity<CampeonatoPiloto> consultarCursoCompleto(
            @PathVariable String campeonatoId,
            @RequestParam String pilotoId,
            @RequestParam int equipoId) {

        return liberatyMediaService.obtenerCampeonatoConjunto(campeonatoId, pilotoId, equipoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); 
                // Devuelve 404 si el curso no existe o si el profesor/aula no coinciden
    }

}
