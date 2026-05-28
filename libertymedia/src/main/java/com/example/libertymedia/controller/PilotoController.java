package com.example.libertymedia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.libertymedia.entity.Piloto;
import com.example.libertymedia.services.LiberatyMediaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class PilotoController {

    @Autowired
    private LiberatyMediaService liberatyMediaService;

    @GetMapping("/pilotos")
    public ResponseEntity<List<Piloto>> getListaPilotos(){
        return ResponseEntity.ok(liberatyMediaService.getAllPilotos());
    }

    @GetMapping("/pilotos/{id}")
    public ResponseEntity<Piloto> getPilotoById(@PathVariable String id) {
        return liberatyMediaService.getPilotoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/pilotos")
    public ResponseEntity<Piloto> createPiloto(@RequestBody Piloto piloto) {
        Piloto nuevopiloto = liberatyMediaService.addPiloto(piloto);
        return ResponseEntity.ok(nuevopiloto);
    }

    @PutMapping("/pilotos/{id}")
    public ResponseEntity<Piloto> modificarRolPiloto(@PathVariable String id, @RequestBody String nuevoRol) {
        return liberatyMediaService.modificarRolPiloto(id, nuevoRol)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/pilotos/{id}")
    public ResponseEntity<Void> eliminarPiloto(@PathVariable String id) {
        if (liberatyMediaService.eliminarPiloto(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    
    

}
