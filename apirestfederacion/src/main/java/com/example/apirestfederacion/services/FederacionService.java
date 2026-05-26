package com.example.apirestfederacion.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.apirestfederacion.dto.ArbitroDto;
import com.example.apirestfederacion.dto.JugadorDto;
import com.example.apirestfederacion.dto.PartidoDetalleResponse;
import com.example.apirestfederacion.dto.PartidoRequest;
import com.example.apirestfederacion.entity.Arbitro;
import com.example.apirestfederacion.entity.Equipo;
import com.example.apirestfederacion.entity.Jugador;
import com.example.apirestfederacion.entity.Partido;
import com.example.apirestfederacion.entity.Rol;
import com.example.apirestfederacion.repository.ArbitroRepository;
import com.example.apirestfederacion.repository.EquipoRepository;
import com.example.apirestfederacion.repository.JugadorRepository;
import com.example.apirestfederacion.repository.PartidoRepository;

@Service
public class FederacionService {

    @Autowired 
    private JugadorRepository jugadorRepository;

    @Autowired 
    private ArbitroRepository arbitroRepository;

    @Autowired 
    private EquipoRepository equipoRepository;
    
    @Autowired 
    private PartidoRepository partidoRepository;

    // ================= EQUIPOS =================
    public List<Equipo> getAllEquipos() {
        return equipoRepository.findAll();
    }

    public Optional<Equipo> getEquipoById(String id) {
        return equipoRepository.findById(id);
    }

    public Equipo addEquipo(Equipo equipo) {
        return equipoRepository.save(equipo); // Devolvemos el guardado para tener su ID
    }

    public Optional<Equipo> actualizarNombreEquipo(String id, String nombreEquipo) {
        return equipoRepository.findById(id).map(equipo -> {
            equipo.setNombreEquipo(nombreEquipo);
            return equipoRepository.save(equipo);
        });
    }

    public boolean eliminarEquipo(String id) {
        if (equipoRepository.existsById(id)) {
            // Lógica combinada: Si eliminamos el equipo, los jugadores quedan libres
            List<Jugador> jugadores = jugadorRepository.findByEquipo(equipoRepository.findById(id).get());
            jugadores.forEach(j -> j.setEquipo(null));
            jugadorRepository.saveAll(jugadores);
            
            equipoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ================= JUGADORES =================
    public List<Jugador> getAllJugadores() {
        return jugadorRepository.findAll();
    }

    public List<Jugador> getJugadoresPorEquipo(String equipoId) {
        return equipoRepository.findById(equipoId)
                .map(jugadorRepository::findByEquipo)
                .orElse(List.of());
    }

    public List<Jugador> getJugadoresPorNombre(String nombre) {
        return jugadorRepository.findByNombreContainingIgnoreCase(nombre); 
    }

    public List<Jugador> getJugadoresSinEquipo() {
        return jugadorRepository.findByEquipoIsNull();
    }

    public Jugador addJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    public Optional<Jugador> actualizarEquipoJugador(String jugadorId, String equipoId) {
        Optional<Jugador> jugadorOpt = jugadorRepository.findById(jugadorId);
        Optional<Equipo> equipoOpt = equipoRepository.findById(equipoId);

        if (jugadorOpt.isPresent() && equipoOpt.isPresent()) {
            Jugador jugador = jugadorOpt.get();
            jugador.setEquipo(equipoOpt.get());
            return Optional.of(jugadorRepository.save(jugador));
        }
        return Optional.empty();
    }

    // NUEVA COMBINACIÓN: Desvincular a un jugador de su equipo
    public Optional<Jugador> liberarJugador(String jugadorId) {
        return jugadorRepository.findById(jugadorId).map(jugador -> {
            jugador.setEquipo(null);
            return jugadorRepository.save(jugador);
        });
    }

    public boolean eliminarJugador(String id) {
        if (jugadorRepository.existsById(id)) {
            jugadorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ================= ÁRBITROS =================
    public List<Arbitro> getAllArbitros() {
        return arbitroRepository.findAll();
    }

    public List<Arbitro> getArbitrosPorRol(Rol rol) {
        return arbitroRepository.findByRol(rol); // Usando JPA directo
    }

    public Arbitro addArbitro(Arbitro arbitro) {
        return arbitroRepository.save(arbitro);
    }

    public boolean eliminarArbitro(String id) {
        if (arbitroRepository.existsById(id)) {
            arbitroRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ================= PARTIDOS =================
    public List<Partido> getAllPartidos() {
        return partidoRepository.findAll();
    }

    public Optional<Partido> getPartidoById(String id) {
        return partidoRepository.findById(id);
    }

    public List<Partido> getPartidosPorEquipo(String equipoId) {
        return partidoRepository.buscarPartidosPorEquipo(equipoId);
    }

    public PartidoDetalleResponse crearPartidoDesdeIds(PartidoRequest request) {
        Equipo equipo1 = equipoRepository.findById(request.getEquipo1())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipo1 no encontrado"));
        Equipo equipo2 = equipoRepository.findById(request.getEquipo2())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipo2 no encontrado"));
        Arbitro arbitro1 = arbitroRepository.findById(request.getArbitro1())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Árbitro1 no encontrado"));
        Arbitro arbitro2 = arbitroRepository.findById(request.getArbitro2())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Árbitro2 no encontrado"));

        if (equipo1.getId().equals(equipo2.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los equipos deben ser distintos");
        }

        if (arbitro1.getId().equals(arbitro2.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los árbitros deben ser distintos");
        }

        Partido partido = new Partido(null, equipo1, equipo2, arbitro1, arbitro2);
        Partido guardado = partidoRepository.save(partido);

        List<ArbitroDto> arbitros = List.of(ArbitroDto.from(arbitro1), ArbitroDto.from(arbitro2));
        List<JugadorDto> jugadoresEquipo1 = jugadorRepository.findByEquipo(equipo1)
                .stream()
                .map(JugadorDto::from)
                .toList();
        List<JugadorDto> jugadoresEquipo2 = jugadorRepository.findByEquipo(equipo2)
                .stream()
                .map(JugadorDto::from)
                .toList();

        return PartidoDetalleResponse.from(guardado, arbitros, jugadoresEquipo1, jugadoresEquipo2);
    }

    public Partido addPartido(Partido partido) {
        return partidoRepository.save(partido);
    }

    public boolean eliminarPartido(String id) {
        if (partidoRepository.existsById(id)) {
            partidoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}