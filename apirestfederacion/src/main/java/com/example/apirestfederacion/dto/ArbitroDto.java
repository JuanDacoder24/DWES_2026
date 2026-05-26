package com.example.apirestfederacion.dto;

import com.example.apirestfederacion.entity.Arbitro;
import com.example.apirestfederacion.entity.Rol;

public class ArbitroDto {

    private String id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private Rol rol;

    public ArbitroDto() {
    }

    public static ArbitroDto from(Arbitro arbitro) {
        ArbitroDto dto = new ArbitroDto();
        dto.id = arbitro.getId();
        dto.nombre = arbitro.getNombre();
        dto.apellido1 = arbitro.getApellido1();
        dto.apellido2 = arbitro.getApellido2();
        dto.rol = arbitro.getRol();
        return dto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
