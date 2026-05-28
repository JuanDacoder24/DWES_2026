package com.example.libertymedia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "campeonato_pilotos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CampeonatoPiloto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36, columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "temporada", nullable = false)
    private int temporada;

    @Column(name = "posicion", nullable = false)
    private int posicion;

    @Column(name = "puntos", nullable = false)
    private int puntos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piloto_id", nullable = false)
    private Piloto piloto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    public CampeonatoPiloto(){

    }

    public CampeonatoPiloto(String id, int temporada, int posicion, int puntos, Piloto piloto, Equipo equipo) {
        this.id = id;
        this.temporada = temporada;
        this.posicion = posicion;
        this.puntos = puntos;
        this.piloto = piloto;
        this.equipo = equipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    



}
