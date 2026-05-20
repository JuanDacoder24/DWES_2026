package com.example.apirestfederacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.apirestfederacion.entity.Partido;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, String>{

    @Query("SELECT p FROM Partido p WHERE p.equipo1.id = :equipoId OR p.equipo2.id = :equipoId")
    List<Partido> buscarPartidosPorEquipo(@Param("equipoId") String equipoId);
}
