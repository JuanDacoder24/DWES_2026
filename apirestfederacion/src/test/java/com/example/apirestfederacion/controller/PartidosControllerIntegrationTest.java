package com.example.apirestfederacion.controller;

import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.apirestfederacion.dto.PartidoRequest;
import com.example.apirestfederacion.entity.Arbitro;
import com.example.apirestfederacion.entity.Equipo;
import com.example.apirestfederacion.entity.Jugador;
import com.example.apirestfederacion.entity.Posicion;
import com.example.apirestfederacion.entity.Rol;
import com.example.apirestfederacion.repository.ArbitroRepository;
import com.example.apirestfederacion.repository.EquipoRepository;
import com.example.apirestfederacion.repository.JugadorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
class PartidosControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private ArbitroRepository arbitroRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        jugadorRepository.deleteAll();
        arbitroRepository.deleteAll();
        equipoRepository.deleteAll();

        Equipo equipo1 = equipoRepository.save(new Equipo(null, "Equipo A", "Sede A"));
        Equipo equipo2 = equipoRepository.save(new Equipo(null, "Equipo B", "Sede B"));

        Arbitro arbitro1 = arbitroRepository.save(new Arbitro(null, "Luis", "García", "Pérez", Rol.PRINCIPAL));
        Arbitro arbitro2 = arbitroRepository.save(new Arbitro(null, "Ana", "Martínez", "López", Rol.ASISTENTE));

        Jugador jugador1 = new Jugador(null, 1, "Carlos", "Ruiz", "Sánchez", Posicion.DEFENSA);
        jugador1.setEquipo(equipo1);
        Jugador jugador2 = new Jugador(null, 7, "Pablo", "Mora", "Núñez", Posicion.DELANTERO);
        jugador2.setEquipo(equipo1);
        Jugador jugador3 = new Jugador(null, 5, "Mateo", "Vega", "Torres", Posicion.MEDIO);
        jugador3.setEquipo(equipo2);
        Jugador jugador4 = new Jugador(null, 9, "Iván", "Linares", "Ramos", Posicion.DELANTERO);
        jugador4.setEquipo(equipo2);

        jugadorRepository.save(jugador1);
        jugadorRepository.save(jugador2);
        jugadorRepository.save(jugador3);
        jugadorRepository.save(jugador4);

        testRequest = new PartidoRequest();
        testRequest.setEquipo1(equipo1.getId());
        testRequest.setEquipo2(equipo2.getId());
        testRequest.setArbitro1(arbitro1.getId());
        testRequest.setArbitro2(arbitro2.getId());
    }

    private PartidoRequest testRequest;

    @Test
    void deberiaCrearPartidoYDevolverArbitrosYJugadoresDeLosEquipos() throws Exception {
        mockMvc.perform(post("/api/partidos/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.equipo1.id").value(testRequest.getEquipo1()))
            .andExpect(jsonPath("$.equipo2.id").value(testRequest.getEquipo2()))
            .andExpect(jsonPath("$.arbitros", hasSize(2)))
            .andExpect(jsonPath("$.jugadoresEquipo1", hasSize(2)))
            .andExpect(jsonPath("$.jugadoresEquipo2", hasSize(2)));
    }
}
