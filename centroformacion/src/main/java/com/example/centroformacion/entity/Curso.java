package com.example.centroformacion.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id = UUID.randomUUID().toString();

    private String nombre_curso;
    private int horas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesor_id", nullable = false)
    private Profesor profesores;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aulas;

    public Curso(String id, String nombre_curso, int horas, Profesor profesores, Aula aulas) {
        this.id = id;
        this.nombre_curso = nombre_curso;
        this.horas = horas;
        this.profesores = profesores;
        this.aulas = aulas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre_curso() {
        return nombre_curso;
    }

    public void setNombre_curso(String nombre_curso) {
        this.nombre_curso = nombre_curso;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public Profesor getProfesores() {
        return profesores;
    }

    public void setProfesores(Profesor profesores) {
        this.profesores = profesores;
    }

    public Aula getAulas() {
        return aulas;
    }

    public void setAulas(Aula aulas) {
        this.aulas = aulas;
    }

    


    


}
