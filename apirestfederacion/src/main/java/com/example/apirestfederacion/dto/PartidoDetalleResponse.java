package com.example.apirestfederacion.dto;

import java.util.List;

import com.example.apirestfederacion.entity.Partido;

public class PartidoDetalleResponse {

    private String id;
    private EquipoDto equipo1;
    private EquipoDto equipo2;
    private List<ArbitroDto> arbitros;
    private List<JugadorDto> jugadoresEquipo1;
    private List<JugadorDto> jugadoresEquipo2;

    public PartidoDetalleResponse() {
    }

    public static PartidoDetalleResponse from(Partido partido, List<ArbitroDto> arbitros,
            List<JugadorDto> jugadoresEquipo1, List<JugadorDto> jugadoresEquipo2) {
        PartidoDetalleResponse response = new PartidoDetalleResponse();
        response.id = partido.getId();
        response.equipo1 = EquipoDto.from(partido.getEquipo1());
        response.equipo2 = EquipoDto.from(partido.getEquipo2());
        response.arbitros = arbitros;
        response.jugadoresEquipo1 = jugadoresEquipo1;
        response.jugadoresEquipo2 = jugadoresEquipo2;
        return response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EquipoDto getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(EquipoDto equipo1) {
        this.equipo1 = equipo1;
    }

    public EquipoDto getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(EquipoDto equipo2) {
        this.equipo2 = equipo2;
    }

    public List<ArbitroDto> getArbitros() {
        return arbitros;
    }

    public void setArbitros(List<ArbitroDto> arbitros) {
        this.arbitros = arbitros;
    }

    public List<JugadorDto> getJugadoresEquipo1() {
        return jugadoresEquipo1;
    }

    public void setJugadoresEquipo1(List<JugadorDto> jugadoresEquipo1) {
        this.jugadoresEquipo1 = jugadoresEquipo1;
    }

    public List<JugadorDto> getJugadoresEquipo2() {
        return jugadoresEquipo2;
    }

    public void setJugadoresEquipo2(List<JugadorDto> jugadoresEquipo2) {
        this.jugadoresEquipo2 = jugadoresEquipo2;
    }
}
