package com.example.apirestfederacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apirestfederacion.entity.Jugadores;

@Repository
public interface JugadoresRepository extends JpaRepository<Jugadores, Long> {

}
