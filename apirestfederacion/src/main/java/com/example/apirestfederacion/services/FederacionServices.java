package com.example.apirestfederacion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apirestfederacion.entity.Equipos;
import com.example.apirestfederacion.repository.ArbitrosRepository;
import com.example.apirestfederacion.repository.EquiposRepository;
import com.example.apirestfederacion.repository.JugadoresRepository;

@Service
public class FederacionServices {

    @Autowired
    private JugadoresRepository jugadoresRepository;

    @Autowired
    private ArbitrosRepository arbitrosRepository;

    @Autowired
    private EquiposRepository equiposRepository;

    public List<Equipos> getAllEquipos() {
        return equiposRepository.findAll();
    }

    public Equipos getEquipoById(Long id) {
        return equiposRepository.findById(id).orElse(null);
    }

    public void addEquipo(Equipos equipo) {
        if (equipo != null) {
            equiposRepository.save(equipo);
        }
    }

    public Equipos actualizarNombreEquipos(String nombre_equipo, Long id) {
        Equipos equipo = equiposRepository.findById(id).orElse(null);
        if (equipo != null) {
            equipo.setNombreEquipo(nombre_equipo);
            return equiposRepository.save(equipo);
        }
        return null;
    }

    public boolean eliminarEquipo(Long id) {
        if (equiposRepository.existsById(id)) {
            equiposRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
