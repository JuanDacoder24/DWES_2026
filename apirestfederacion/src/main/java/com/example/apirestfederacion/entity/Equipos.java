package com.example.apirestfederacion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipos")
public class Equipos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_equipo", nullable = false)
    private String nombreEquipo;

    @Column(name = "sede", nullable = false)
    private String sede;


    public Equipos(Long id, String nombreEquipo, String sede) {
        this.id = id;
        this.nombreEquipo = nombreEquipo;
        this.sede = sede;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
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
