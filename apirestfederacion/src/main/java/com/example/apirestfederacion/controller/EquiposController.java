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
    public ResponseEntity<Equipo> getEquipoById(@PathVariable String id) {
        return service.getEquipoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/equipos")
    public ResponseEntity<Equipo> addEquipo(@RequestBody Equipo equipo){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addEquipo(equipo));
    }

    @PutMapping("/equipos/{id}")
    public ResponseEntity<Equipo> actualizarEquipo(@PathVariable String id, @RequestParam String nombre) {
        return service.actualizarNombreEquipo(id, nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //     Opción A: Modificar el JSON en Postman (La más rápida para probar)
// Si no quieres tocar tu código Java ahora mismo, tienes que enviarle el parámetro tal como lo pide tu @RequestParam.
// En Postman, cambia la pestaña Body a none.
// Ve a la pestaña Params (justo debajo de la URL).
// Añade una fila donde la clave (Key) sea nombre y el valor (Value) sea Sporting Cristal.
// Tu URL se verá automáticamente así:
// http://localhost:8080/api/equipos/13e1aece-e919-4e54-bd09-1b9b76d5dfde?nombre=Sporting+Cristal
// (Ten en cuenta que con esta opción la sede no se actualizará, ya que tu método Java solo recibe el nombre).


    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable String id){
        if (service.eliminarEquipo(id)) {
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.notFound().build();
    }

}
