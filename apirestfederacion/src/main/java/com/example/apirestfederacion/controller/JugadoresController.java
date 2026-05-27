package com.example.apirestfederacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.apirestfederacion.entity.Jugador;
import com.example.apirestfederacion.services.FederacionService;

@RestController
@RequestMapping("/api")
public class JugadoresController {

    @Autowired
    private FederacionService service;

   @GetMapping("/jugadores")
    public ResponseEntity<List<Jugador>> getListaJugadores() {
        return ResponseEntity.ok(service.getAllJugadores());
    }

    @GetMapping("/jugadores/buscar")
    public ResponseEntity<List<Jugador>> getJugadoresPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(service.getJugadoresPorNombre(nombre));
    }

    @GetMapping("/equipos/{equipoId}/jugadores")
    public ResponseEntity<List<Jugador>> getJugadoresPorEquipo(@PathVariable String equipoId) {
        List<Jugador> jugadores = service.getJugadoresPorEquipo(equipoId);
        if (jugadores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(jugadores);
    }

    @GetMapping("/jugadores/libres")
    public ResponseEntity<List<Jugador>> getJugadoresLibres() {
        return ResponseEntity.ok(service.getJugadoresSinEquipo());
    }

    @PostMapping("/jugadores")
    public ResponseEntity<Jugador> addJugador(@RequestBody Jugador jugador){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addJugador(jugador));
    }

    @PutMapping("/jugadores/{jugadorId}/equipo/{equipoId}")
    public ResponseEntity<Jugador> ficharJugador(@PathVariable String jugadorId, @PathVariable String equipoId) {
        return service.actualizarEquipoJugador(jugadorId, equipoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/jugadores/{jugadorId}/liberar")
    public ResponseEntity<Jugador> liberarJugador(@PathVariable String jugadorId) {
        return service.liberarJugador(jugadorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/jugadores/{id}")
    public ResponseEntity<Void> eliminarJugador(@PathVariable String id){
        if (service.eliminarJugador(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
