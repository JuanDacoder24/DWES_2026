package com.example.apirestfederacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apirestfederacion.dto.PartidoDetalleResponse;
import com.example.apirestfederacion.dto.PartidoRequest;
import com.example.apirestfederacion.entity.Partido;
import com.example.apirestfederacion.services.FederacionService;

@RestController
@RequestMapping("/api")
public class PartidosController {

    @Autowired
    private FederacionService service;

    @GetMapping("/partidos")
    public ResponseEntity<List<Partido>> getListaPartidos() {
        return ResponseEntity.ok(service.getAllPartidos());
    }

    @GetMapping("/partidos/{id}")
    public ResponseEntity<Partido> getPartidoById(@PathVariable String id) {
        return service.getPartidoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/equipos/{equipoId}/partidos")
    public ResponseEntity<List<Partido>> getPartidosPorEquipo(@PathVariable String equipoId) {
        return ResponseEntity.ok(service.getPartidosPorEquipo(equipoId));
    }

    @PostMapping("/partidos")
    public ResponseEntity<Partido> addPartido(@RequestBody Partido partido){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addPartido(partido));
    }

    @PostMapping("/partidos/crear")
    public ResponseEntity<PartidoDetalleResponse> crearPartidoDesdeIds(@RequestBody PartidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearPartidoDesdeIds(request));
    }

    @DeleteMapping("/partidos/{id}")
    public ResponseEntity<Void> eliminarPartido(@PathVariable String id){
        if (service.eliminarPartido(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
