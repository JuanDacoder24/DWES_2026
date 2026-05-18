package com.example.apirestfederacion.entity;

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

}
