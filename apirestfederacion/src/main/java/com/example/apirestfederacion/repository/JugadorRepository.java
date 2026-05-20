package com.example.apirestfederacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apirestfederacion.entity.Equipo;
import com.example.apirestfederacion.entity.Jugador;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, String> {

        List<Jugador> findByEquipo(Equipo equipo);

        List<Jugador> findByNombreContainingIgnoreCase(String nombre);
        
        List<Jugador> findByEquipoIsNull();


}
