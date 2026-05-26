package com.example.apirestfederacion.dto;

import com.example.apirestfederacion.entity.Equipo;

public class EquipoDto {

    private String id;
    private String nombreEquipo;
    private String sede;

    public EquipoDto() {
    }

    public static EquipoDto from(Equipo equipo) {
        EquipoDto dto = new EquipoDto();
        dto.id = equipo.getId();
        dto.nombreEquipo = equipo.getNombreEquipo();
        dto.sede = equipo.getSede();
        return dto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }
}
