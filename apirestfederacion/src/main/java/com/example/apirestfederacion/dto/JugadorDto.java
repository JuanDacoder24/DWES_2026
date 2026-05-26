package com.example.apirestfederacion.dto;

import com.example.apirestfederacion.entity.Jugador;
import com.example.apirestfederacion.entity.Posicion;

public class JugadorDto {

    private String id;
    private int dorsal;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private Posicion posicion;

    public JugadorDto() {
    }

    public static JugadorDto from(Jugador jugador) {
        JugadorDto dto = new JugadorDto();
        dto.id = jugador.getId();
        dto.dorsal = jugador.getDorsal();
        dto.nombre = jugador.getNombre();
        dto.apellido1 = jugador.getApellido1();
        dto.apellido2 = jugador.getApellido2();
        dto.posicion = jugador.getPosicion();
        return dto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
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

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }
}
