package com.example.centroformacion.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36, columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "nombre_curso", nullable = false)
    private String nombreCurso;

    @Column(name = "horas", nullable = false)
    private int horas;

    //SI LO QUE SE QUIERE ES CREAR UN CURSO NUEVO CON PROFESORES NUEVOS Y AULAS NUEVAS, 
    //SE PONDRIA 
    //@ManyToOne(cascade = CascadeType.PERSIST

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id", nullable = false)
    private Profesor profesores;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aulas;

    public Curso() {
    }

    public Curso(String id, String nombreCurso, int horas, Profesor profesores, Aula aulas) {
        this.id = id;
        this.nombreCurso = nombreCurso;
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

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
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
