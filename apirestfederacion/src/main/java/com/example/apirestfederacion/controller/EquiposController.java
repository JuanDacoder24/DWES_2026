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

import com.example.apirestfederacion.entity.Equipo;
import com.example.apirestfederacion.services.FederacionService;

@RestController
@RequestMapping("/api")
public class EquiposController {

    @Autowired
    private FederacionService service;

    @GetMapping("/equipos")
    public ResponseEntity<List<Equipo>> getListaEquipos() {
        return ResponseEntity.ok(service.getAllEquipos());
    }

    @GetMapping("/equipos/{id}")
    public ResponseEntity<Equipo> getEquipoById(@PathVariable Long id) {
        return service.getEquipoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/equipos")
    public ResponseEntity<Equipo> addEquipo(@RequestBody Equipo equipo){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addEquipo(equipo));
    }

    @PutMapping("/equipos/{id}")
    public ResponseEntity<Equipo> actualizarEquipo(@PathVariable Long id, @RequestParam String nombre) {
        return service.actualizarNombreEquipo(id, nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable Long id){
        if (service.eliminarEquipo(id)) {
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.notFound().build();
    }

}
