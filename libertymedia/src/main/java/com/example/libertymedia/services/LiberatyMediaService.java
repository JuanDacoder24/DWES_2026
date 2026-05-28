package com.example.libertymedia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.libertymedia.entity.CampeonatoPiloto;
import com.example.libertymedia.entity.Equipo;
import com.example.libertymedia.entity.Piloto;
import com.example.libertymedia.entity.Rol;
import com.example.libertymedia.repository.CampeonatoPilotoRepository;
import com.example.libertymedia.repository.EquipoRepository;
import com.example.libertymedia.repository.PilotoRepository;

@Service
public class LiberatyMediaService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private PilotoRepository pilotoRepository;

    @Autowired
    private CampeonatoPilotoRepository campeonatoPilotoRepository;

    //equipos

    //mostrar todos los equipos
    public List<Equipo> getAllEquipos(){
        return equipoRepository.findAll();
    }

    //mostrar equipo por id
    public Optional<Equipo> getEquipoById(int id) {
        return equipoRepository.findById(id);
    }

    //--------------------------------------------



    //pilotos

    //mostrar todos los pilotos
    public List<Piloto> getAllPilotos(){
        return pilotoRepository.findAll();
    }

    //mostrar piloto por id
    public Optional<Piloto> getPilotoById(String id) {
        return pilotoRepository.findById(id);
    }

    //agregar piloto
    public Piloto addPiloto(Piloto piloto) {
        return pilotoRepository.save(piloto);
    }

    //modificar el rol de un piloto
    public Optional<Piloto> modificarRolPiloto(String id, String nuevoRol) {
       return pilotoRepository.findById(id).map(piloto -> {
            piloto.setRol(Rol.valueOf(nuevoRol));
            return pilotoRepository.save(piloto);
        });
    }

    //eliminar piloto
    public boolean eliminarPiloto(String id) {
        if (pilotoRepository.existsById(id)) {
            pilotoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //--------------------------------------------


    //campeonatoPiloto

    //mostrar los registros
    public List<CampeonatoPiloto> getAllCampeonato(){
        return campeonatoPilotoRepository.findAll();
    }

    //mostrar regsitros por id
    public Optional<CampeonatoPiloto> getCampeonatoById(String id) {
        return campeonatoPilotoRepository.findById(id);
    }

    //añadir registro
    public CampeonatoPiloto addCampeonato(CampeonatoPiloto campeonatoPiloto) {
        return campeonatoPilotoRepository.save(campeonatoPiloto);
    }

    //borrar registro
    public boolean eliminarCampeonato(String id) {
        if (campeonatoPilotoRepository.existsById(id)) {
            campeonatoPilotoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //modificar el quipo asignado a un registro del campeonato segun el id del registro
    public Optional<CampeonatoPiloto> actualizarEquipo(String campeonatoId, int equipoId) {
        Optional<CampeonatoPiloto> cameponato = campeonatoPilotoRepository.findById(campeonatoId);
        Optional<Equipo> equipoOpt = equipoRepository.findById(equipoId);

        if (cameponato.isPresent() && equipoOpt.isPresent()) {
            CampeonatoPiloto campeonatoPiloto = cameponato.get();
            campeonatoPiloto.setEquipo(equipoOpt.get());
            return Optional.of(campeonatoPilotoRepository.save(campeonatoPiloto));
        }
        return Optional.empty();
    }


    //funcion conjunto
    public Optional<CampeonatoPiloto> obtenerCampeonatoConjunto(String campeonatoId, String pilotoId, int equipoId) {
    return campeonatoPilotoRepository.findById(campeonatoId).filter(campeonato -> {
        boolean mismoPiloto = campeonato.getPiloto() != null && 
                                campeonato.getPiloto().getId().equals(pilotoId);
        
        boolean mismoEquipo = campeonato.getEquipo() != null ;
        
        return mismoPiloto && mismoEquipo;
    });
}

}
