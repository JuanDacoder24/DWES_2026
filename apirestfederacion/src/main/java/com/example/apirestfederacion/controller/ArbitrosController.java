package com.example.apirestfederacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apirestfederacion.entity.Arbitro;
import com.example.apirestfederacion.entity.Rol;
import com.example.apirestfederacion.services.FederacionService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class ArbitrosController {

    @Autowired
    private FederacionService service;

    // Listado de árbitros
    @GetMapping("/arbitros")
    public List<Arbitro> getListaArbitros() {
        return service.getAllArbitros();
    }

    // Obtener árbitros por rol
    @GetMapping("/arbitros/rol/{rol}")
    public List<Arbitro> getArbitrosPorRol(@PathVariable Rol rol) {
        return service.getArbitrosPorRol(rol);
    }

    //agregar árbitro
    @PostMapping("/arbitros")
    public ResponseEntity<String> addArbitro(@RequestBody Arbitro arbitro){
        service.addArbitro(arbitro);
        return ResponseEntity.ok("Árbitro agregado correctamente");
    }

    //eliminar árbitro
    @DeleteMapping("/arbitros/{id}")
    public ResponseEntity<String> eliminarArbitro(@PathVariable String id){
        boolean eliminado = service.eliminarArbitro(id);
        if (eliminado) {
            return ResponseEntity.ok("Árbitro eliminado correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    

}
