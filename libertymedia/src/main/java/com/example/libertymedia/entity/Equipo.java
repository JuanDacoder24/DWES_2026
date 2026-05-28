package com.example.libertymedia.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipos")
public class Equipo {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "nombre_equipo", nullable = false)
    private String nombreEquipo;

    @Column(name = "pais", nullable = false)
    private String pais;

    @Column(name = "motor", nullable = false)
    private String motor;

    public Equipo(){

    }

    public Equipo(int id, String nombreEquipo, String pais, String motor) {
        this.id = id;
        this.nombreEquipo = nombreEquipo;
        this.pais = pais;
        this.motor = motor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    

}
