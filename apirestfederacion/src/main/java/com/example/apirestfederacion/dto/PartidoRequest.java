package com.example.apirestfederacion.dto;

public class PartidoRequest {

    private String equipo1;
    private String equipo2;
    private String arbitro1;
    private String arbitro2;

    public PartidoRequest() {
    }

    public String getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(String equipo1) {
        this.equipo1 = equipo1;
    }

    public String getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(String equipo2) {
        this.equipo2 = equipo2;
    }

    public String getArbitro1() {
        return arbitro1;
    }

    public void setArbitro1(String arbitro1) {
        this.arbitro1 = arbitro1;
    }

    public String getArbitro2() {
        return arbitro2;
    }

    public void setArbitro2(String arbitro2) {
        this.arbitro2 = arbitro2;
    }
}
