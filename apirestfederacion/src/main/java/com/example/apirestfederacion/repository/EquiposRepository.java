package com.example.apirestfederacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apirestfederacion.entity.Equipos;

@Repository
public interface EquiposRepository extends JpaRepository<Equipos, Long> {

}
